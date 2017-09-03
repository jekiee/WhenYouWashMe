package com.example.jek.whenyouwashme.model.WeatherForecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jek on 29.08.2017.
 */

public class WeatherData {

    public String message;

    public String intString;

    @SerializedName("temp")
    public int temp;

    @SerializedName("temp_main")
    public int tempMain;


    public int temp_max;
    public int pressure;
    public String clouds; //weather/main:
    public String windSpeed;
    public String windDirection;

    public List<Tag> tags;

    public WeatherData() {
    }
}

