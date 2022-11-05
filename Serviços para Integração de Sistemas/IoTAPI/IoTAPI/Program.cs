var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.MapGet("/temperaturas", () =>
{
    Sensor sensor = new(1, 30, 60, DateTimeOffset.Now);

    List<Sensor> temperatures = new()
    {
        sensor
    };

    return temperatures;
})
.WithName("GetTemperatures");

app.Run();

internal record Sensor(int Id, double Temperature, double Humidity, DateTimeOffset DateTimeOffset)
{
}
