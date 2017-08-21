package com.example.jek.whenyouwashme.model.WeatherForecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.services.LocationService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jek on 13.08.2017.
 */

public class RemoteFetch extends BroadcastReceiver {
    private Location location;
    private LocationService service;
    private static String weatherForecastURL;

    //sharedpraferences save last weather update time

    public static JSONObject getJSON(Context context, String location) {
        try {
            URL url = new URL(weatherForecastURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200) {
                return null;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        location = RemoteFetch.this.service.currentLocation;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        getUrl(latitude, longitude);
    }

    private String getUrl(double latitude, double longitude) {
        StringBuilder sbWeatherForecastURL = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?");
        sbWeatherForecastURL.append(latitude + "&" + longitude + "&appid=aebffaa07d0394bde5b33f3466a00516");
        return weatherForecastURL = sbWeatherForecastURL.toString();
    }
}
