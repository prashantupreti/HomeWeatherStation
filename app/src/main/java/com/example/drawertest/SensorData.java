package com.example.drawertest;

public class SensorData {
    String id, air_temperature, humidity, wind_speed, barometric_pressure, created_at;

    public SensorData(String sensor_id, String air_temp, String hum, String speed, String pressure, String created){
        this.id=sensor_id;
        this.air_temperature=air_temp;
        this.humidity=hum;
        this.wind_speed=speed;
        this.barometric_pressure=pressure;
        this.created_at=created;
    }

    public String getAirTemperature(){
        return air_temperature;
    }
    public String getHumidity(){
        return humidity;
    }
    public String getWindSpeed(){
        return wind_speed;
    }
    public String getBarometricPressure(){
        return barometric_pressure;
    }
    public String getCreatedAt(){
        return created_at;
    }
}
