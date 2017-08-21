package com.example.jek.whenyouwashme.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.fragments.FragmentLowerPart;
import com.example.jek.whenyouwashme.fragments.FragmentRightPart;
import com.example.jek.whenyouwashme.fragments.FragmentWeather;
import com.example.jek.whenyouwashme.model.WeatherForecast.RemoteFetch;
import com.example.jek.whenyouwashme.services.WeatherForecastService;

public class WeatherForecastActivity extends AppCompatActivity {
    private static final String TAG = WeatherForecastActivity.class.getSimpleName();
    WeatherForecastService service;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, WeatherForecastService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WeatherForecastService.WEATHER_FORECAST_CLIENT_LOCATION);
        registerReceiver(remoteFetch, intentFilter);
    }


    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "service connected " + System.currentTimeMillis());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "service disconnected");
        }
    };

   private void bindWeatherForecastService(){
       bindService(new Intent(this, WeatherForecastService.class), connection, BIND_AUTO_CREATE);
   }

    private class WeatherForecastAlarm extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }




}