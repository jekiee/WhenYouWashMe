package com.example.jek.whenyouwashme.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

/**
 * Created by jek on 15.08.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class WeatherForecastService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int WEATHER_FORECAST_SERVICE_ID = 375;
    private static final String TAG = WeatherForecastService.class.getSimpleName();
    private static final long INTERVAL = 1000 * 60; // <--- 1 minute ("1000 * 60 * 60 * 24" <--- 1 day)
    public static final String WEATHER_FORECAST_CLIENT_LOCATION = "client location";
    private IBinder binder = new WeatherForecastBinder();


    //если времени прошло больше 1 минуты (для теста), то обновить AlarmManager
    //create AlarmManager



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        alarmWeatherForecastService();
        return START_STICKY;
    }

    private void alarmWeatherForecastService(){
        Context ctx = getApplicationContext();
/** this gives us the time for the first trigger.  */
        Calendar cal = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();
        }

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(ctx, WeatherForecastService.class);
// make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        PendingIntent servicePendingIntent =
                PendingIntent.getService(ctx,
                        WeatherForecastService.WEATHER_FORECAST_SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
// there are other options like setInexactRepeating, check the docs

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            am.setRepeating(
                    AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                    cal.getTimeInMillis(),
                    INTERVAL,
                    servicePendingIntent
            );
        }
    }

    public class WeatherForecastBinder extends Binder {

        public WeatherForecastService getService(){
            return WeatherForecastService.this;
        }
    }
    //return new coordinates every 6 hours/on changed dist more than 30 km/


}
