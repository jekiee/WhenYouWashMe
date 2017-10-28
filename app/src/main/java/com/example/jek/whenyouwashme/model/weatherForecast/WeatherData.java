package com.example.jek.whenyouwashme.model.weatherForecast;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jek on 29.08.2017.
 */

public class WeatherData {

    public String cod;

    public class Weather {
        public double pressure;
        public double temp;
    }

    @SerializedName("list")
    public WeatherInfo[] info;

    public class WeatherInfo {
        @SerializedName("dt")
        public long date;

        @SerializedName("weather")
        public WeatherStats[] stats;

        @SerializedName("main")
        public Weather weather;

        public Date getDate() {
            return new Date(date * 1000);
        }
    }

    public class WeatherStats{
        @SerializedName("main")
        public String weatherType;
    }
}