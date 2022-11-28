using Amazon;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DocumentModel;
using Amazon.DynamoDBv2.Model;
using Microsoft.Extensions.Options;
using System.Net;
using System.Text.Json;
using System.Text.Json.Serialization;

var builder = WebApplication.CreateBuilder(args);

var config = builder.Configuration;

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.Configure<DatabaseSettings>(config.GetSection(DatabaseSettings.KeyName));
builder.Services.AddSingleton<IAmazonDynamoDB>(_ => new AmazonDynamoDBClient(RegionEndpoint.USWest2));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.MapGet("/measurements", async (IAmazonDynamoDB dynamoDb, IOptions<DatabaseSettings> databaseSettings) =>
{
    ScanRequest scanRequest = new ()
    {
        TableName = databaseSettings.Value.TableName
    };

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
            measurements.Add(new MeasurementDTO(measurement.Id, measurement.Temperature, measurement.Humidity, measurement.DateTimeOffset));
        }
    }

    return measurements;
})
.WithName("GetTemperatures");

app.MapPost("/measurement", async (MeasurementDTO measurementDTO, IAmazonDynamoDB dynamoDb, IOptions<DatabaseSettings> databaseSettings) =>
{
    Measurement measurement = new()
    {
        Id = Guid.NewGuid().ToString(),
        Temperature = measurementDTO.Temperature,
        Humidity = measurementDTO.Humidity,
        DateTimeOffset = measurementDTO.DateTimeOffset,
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
    return response.HttpStatusCode == HttpStatusCode.OK;
})
.WithName("CreateMeasurement");

app.Run();

internal record MeasurementDTO(string Id, double Temperature, double Humidity, DateTimeOffset DateTimeOffset)
{
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

    [JsonPropertyName("humidity")]
    public double Humidity { get; init; } = default!;

    [JsonPropertyName("dateTimeOffset")]
    public DateTimeOffset DateTimeOffset { get; init; }
}

public class DatabaseSettings
{
    public const string KeyName = "Database";

    public string TableName { get; set; } = default!;
}
