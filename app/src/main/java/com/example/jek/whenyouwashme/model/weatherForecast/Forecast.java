package com.example.jek.whenyouwashme.model.weatherForecast;

import java.util.Date;

public class Forecast {
    private Date date;
    private WeatherData.WeatherInfo weatherInfo;

    public Forecast(Date date) {
        this.date = date;
    }

    public void setWeatherInfo(WeatherData.WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public Date getDate() {
        return this.date;
    }

    public long getTemperature() {
        return Math.round(this.weatherInfo.weather.temp);
    }

    public String getWeatherType(){
        return this.weatherInfo.stats[0].weatherType;
    }
}
