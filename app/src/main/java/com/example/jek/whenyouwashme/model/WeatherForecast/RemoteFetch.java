package com.example.jek.whenyouwashme.model.WeatherForecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.services.LocationService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by jek on 13.08.2017.
 */

public class RemoteFetch extends BroadcastReceiver {
    private static final String TAG = RemoteFetch.class.getSimpleName();
    private Location location;
    private LocationService locationService;
    private static String weatherForecastURL;

    //sharedpreferences save last weather update time

    public static JSONObject getJSON(Context context) {
        try {
            URL url = new URL(weatherForecastURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder json = new StringBuilder(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200) {
                return null;
            }
            Log.d(TAG, "JSONobject: " + data.toString());
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*long futureTime;*/


        /*futureTime = LocationService.alarmTime;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.getTimeInMillis() < futureTime) {
                //LocationService.alarmWeatherForecastService();
            }
        }*/

        location = LocationService.currentLocation;
        String latitudeFormatted;
        String longitudeFormatted;
        String s;

        double latitude = location.getLatitude();
        DecimalFormat df = new DecimalFormat("###.####");
        double longitude = location.getLongitude();
        longitudeFormatted = df.format(longitude);
        latitudeFormatted = df.format(latitude);
        longitudeFormatted = longitudeFormatted.replace(",", ".");
        latitudeFormatted = latitudeFormatted.replace(",", ".");
        //getting ohooeenneey URL
        //getUrl(Double.parseDouble(latitudeFormatted), Double.parseDouble(longitudeFormatted));
        getUrl(latitude, longitude);
        Log.d(TAG, "onRecieve formatted: " + "\n" + "Latitude: " + latitudeFormatted + "\n" + "Longitude: " + longitudeFormatted);
        Log.d(TAG, "onReceive: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
    }

    private String getUrl(double latitude, double longitude) {
        StringBuilder sbWeatherForecastURL = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast?units=metric&");
        sbWeatherForecastURL.append("lat=" + latitude + "&lon=" + longitude + "&appid=aebffaa07d0394bde5b33f3466a00516");
        Log.d(TAG, "url: " + sbWeatherForecastURL);
        return weatherForecastURL = sbWeatherForecastURL.toString();
    }
}
