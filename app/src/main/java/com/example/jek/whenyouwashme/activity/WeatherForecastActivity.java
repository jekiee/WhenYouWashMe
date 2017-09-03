package com.example.jek.whenyouwashme.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.fragments.FragmentLowerPart;
import com.example.jek.whenyouwashme.fragments.FragmentRightPart;
import com.example.jek.whenyouwashme.fragments.FragmentWeather;
import com.example.jek.whenyouwashme.model.WeatherForecast.RemoteFetch;
import com.example.jek.whenyouwashme.services.LocationService;
import com.google.gson.Gson;

public class WeatherForecastActivity extends AppCompatActivity {
    private static final String TAG = WeatherForecastActivity.class.getSimpleName();
    LocationService locationService;
    RemoteFetch remoteFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentWeather fragmentWeather = new FragmentWeather();
        FragmentLowerPart fragmentLowerPart = new FragmentLowerPart();
        FragmentRightPart fragmentRightPart = new FragmentRightPart();
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_portrait);
            fragmentTransaction.add(R.id.weather_part, fragmentWeather);
            fragmentTransaction.add(R.id.lower_part, fragmentLowerPart);
            fragmentTransaction.commit();
        } else {
            //Log.d(TAG, "switch to else");
            setContentView(R.layout.activity_main_landscape);
            fragmentTransaction.add(R.id.weather_part, fragmentWeather);
            fragmentTransaction.add(R.id.right_part, fragmentRightPart);
            fragmentTransaction.commit();
        }

        remoteFetch = new RemoteFetch();

        Gson gson = new Gson();
        TestObj obj = new TestObj();
        obj.setId(12);
        obj.setText("text");

        String json = gson.toJson(obj);
        Log.d(TAG, "json : " + json);

        TestObj newObj = gson.fromJson(json, TestObj.class);
    }

    public static class TestObj {
        public long id;
        public String text;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.ACTION_LOCATION);
        registerReceiver(remoteFetch, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        unregisterReceiver(remoteFetch);
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            WeatherForecastActivity.this.locationService = ((LocationService.MapBinder) service).getService();
            Log.d(TAG, "service connected " + System.currentTimeMillis());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "service disconnected");
            WeatherForecastActivity.this.locationService = null;
        }
    };

    private void bindWeatherForecastService() {
        bindService(new Intent(this, LocationService.class), connection, BIND_AUTO_CREATE);
    }
}