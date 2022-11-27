using Amazon;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DocumentModel;
using Amazon.DynamoDBv2.Model;
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

app.MapGet("/measurements", () =>
{
    Measurement2 sensor = new(1, 30, 60, DateTimeOffset.Now);

    List<Measurement2> measurements = new()
    {
        sensor
    };

    return measurements;
})
.WithName("GetTemperatures");

app.MapPost("/measurement", async (MeasurementDTO measurement, IAmazonDynamoDB dynamoDb) =>
{
    var measurementAsJson = JsonSerializer.Serialize(measurement);
    var itemAsDocument = Document.FromJson(measurementAsJson);
    var itemAsAttributes = itemAsDocument.ToAttributeMap();

    var createItemRequest = new PutItemRequest
    {
        TableName = "measurements",
        Item = itemAsAttributes
    };

    var response = await dynamoDb.PutItemAsync(createItemRequest);
    return response.HttpStatusCode == HttpStatusCode.OK;
})
.WithName("CreateMeasurement");

app.Run();

internal record Measurement2(int Id, double Temperature, double Humidity, DateTimeOffset DateTimeOffset)
{
}

public class MeasurementDTO
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
