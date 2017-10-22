package com.example.jek.whenyouwashme.model.weatherForecast;

import java.util.Date;

public class Forecast {
    private Date date;
    private double temperature;
    private int count;

    public Forecast(Date date) {
        this.date = date;
    }

    public void addTemperature(double temperature) {
        this.temperature += temperature;
        ++count;
    }

    public void normalize() {
        this.temperature /= count;
    }

    public Date getDate() {
        return this.date;
    }

    public double getTemperature() {
        return this.temperature;
    }
}
