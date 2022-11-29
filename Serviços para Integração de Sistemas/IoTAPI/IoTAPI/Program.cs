using Amazon;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DocumentModel;
using Amazon.DynamoDBv2.Model;
using Microsoft.Extensions.Options;
using System.ComponentModel.DataAnnotations;
using System.Net;
using System.Text.Json;
using System.Text.Json.Serialization;

var builder = WebApplication.CreateBuilder(args);

var config = builder.Configuration;

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.Configure<DatabaseSettings>(config.GetSection(DatabaseSettings.KeyName));
builder.Services.AddSingleton<IAmazonDynamoDB>(_ => new AmazonDynamoDBClient(RegionEndpoint.USWest2));

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.MapGet("/temperaturas", async(int? limit, IAmazonDynamoDB dynamoDb, IOptions<DatabaseSettings> databaseSettings) =>
{
    ScanRequest scanRequest = new()
    {
        TableName = databaseSettings.Value.TableName,
    };

    if (limit != null)
        scanRequest.Limit = (int)limit;

    var response = await dynamoDb.ScanAsync(scanRequest);

    if (response.Items.Count == 0)
    {
        return null;
    }
   
    List<MeasurementDTO> measurements = new ();

    foreach (var item in response.Items)
    {
        var itemAsDocument = Document.FromAttributeMap(item);
        var measurement = JsonSerializer.Deserialize<Measurement>(itemAsDocument.ToJson());

        if (measurement != null)
        {
            measurements.Add(new MeasurementDTO()
            {
                Id = Guid.Parse(measurement.Id),
                Temperature = measurement.Temperature,
                DateTimeOffset = measurement.DateTimeOffset
            });
        }
    }

    return measurements;
})
.WithName("GetTemperatures");

app.MapPost("/temperatura", async (MeasurementDTO measurementDTO, IAmazonDynamoDB dynamoDb, IOptions<DatabaseSettings> databaseSettings) =>
{
    if (measurementDTO.Temperature != null && measurementDTO.DateTimeOffset != null)
    {
        Measurement measurement = new()
        {
            Id = Guid.NewGuid().ToString(),
            Temperature = (double)measurementDTO.Temperature,
            DateTimeOffset = (DateTimeOffset)measurementDTO.DateTimeOffset,
        };

        var measurementAsJson = JsonSerializer.Serialize(measurement);
        var itemAsDocument = Document.FromJson(measurementAsJson);
        var itemAsAttributes = itemAsDocument.ToAttributeMap();

        var createItemRequest = new PutItemRequest
        {
            TableName = databaseSettings.Value.TableName,
            Item = itemAsAttributes
        };

        var response = await dynamoDb.PutItemAsync(createItemRequest);

        if (response.HttpStatusCode == HttpStatusCode.OK)
        {
            return Results.Created("/measurements", null);
        }
        else
        {
            return Results.BadRequest("Erro ao cadastrar medição");
        }
    }
    else
    {
        return Results.BadRequest("Valores inválidos");
    }
})
.WithName("CreateMeasurement");

app.MapDelete("/temperaturas/{id}", async (Guid id, IAmazonDynamoDB dynamoDb, IOptions<DatabaseSettings> databaseSettings) =>
{
    var getItemRequest = new GetItemRequest
    {
        TableName = databaseSettings.Value.TableName,
        Key = new Dictionary<string, AttributeValue>
        {
            { "pk", new AttributeValue { S = id.ToString() } },
            { "sk", new AttributeValue { S = id.ToString() } }
        }
    };

    var response = await dynamoDb.GetItemAsync(getItemRequest);

    if (response.Item.Count == 0)
    {
        return Results.BadRequest("Medição não encontrada");
    }
    else
    {
        var deleteItemRequest = new DeleteItemRequest
        {
            TableName = databaseSettings.Value.TableName,
            Key = new Dictionary<string, AttributeValue>
            {
                { "pk", new AttributeValue { S = id.ToString() } },
                { "sk", new AttributeValue { S = id.ToString() } }
            }
        };

        var deleteResponse = await dynamoDb.DeleteItemAsync(deleteItemRequest);

        if (deleteResponse.HttpStatusCode == HttpStatusCode.OK)
        {
            return Results.Ok();
        }
        else
        {
            return Results.BadRequest("Erro ao remover medição");
        }
    }
}).WithName("Delete");

app.Run();

public class MeasurementDTO
{
    public Guid Id { get; set; }

    [Required]
    public double? Temperature { get; set; }

    [Required]
    public DateTimeOffset? DateTimeOffset { get; set; }
}

public class Measurement
{
    [JsonPropertyName("pk")]
    public string Pk => Id;

    [JsonPropertyName("sk")]
    public string Sk => Pk;

    [JsonPropertyName("id")]
    public string Id { get; init; } = default!;

    [JsonPropertyName("temperature")]
    public double Temperature { get; init; } = default!;

    [JsonPropertyName("dateTimeOffset")]
    public DateTimeOffset DateTimeOffset { get; init; }
}

public class DatabaseSettings
{
    public const string KeyName = "Database";

    public string TableName { get; set; } = default!;
}