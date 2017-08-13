package com.example.jek.whenyouwashme.model.WeatherForecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jek.whenyouwashme.services.LocationService;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

/**
 * Created by jek on 13.08.2017.
 */

public class RemoteFetch extends BroadcastReceiver{
    private GoogleMap mMap;
    private LocationService service;

    public static JSONObject getJSON(Context context, String location){

    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder weatherForecastURL
        return (weatherForecastURL.toString());
    }
}
