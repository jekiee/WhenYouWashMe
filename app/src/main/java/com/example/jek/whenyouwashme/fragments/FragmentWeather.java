package com.example.jek.whenyouwashme.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.activity.WeatherForecastActivity;
import com.example.jek.whenyouwashme.model.weatherForecast.Forecast;
import com.example.jek.whenyouwashme.model.weatherForecast.WeatherData;
import com.example.jek.whenyouwashme.services.LocationService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentWeather extends Fragment {
    private final static String TAG = FragmentWeather.class.getSimpleName();

    TextView dateToday;
    TextView windPressure;
    ImageView weatherTodayBigPicture;
    TextView temperatureToday;

    ImageView weatherFirstDayPicture;
    TextView temperatureFirstDay;
    TextView dateFirstDay;

    TextView dateSecondDay;
    ImageView weatherSecondDayPicture;
    TextView temperatureSecondDay;

    TextView dateThirdDay;
    ImageView weatherThirdDayPicture;
    TextView temperatureThirdDay;

    TextView dateFourthDay;
    ImageView weatherFourthDayPicture;
    TextView temperatureFourthDay;

    Handler handler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dateToday.setText(new SimpleDateFormat("EEEE, d MMMM, HH:mm:ss", Locale.getDefault()).format(new Date()));
            handler.postDelayed(runnable, 1000);
        }
    };

    public FragmentWeather() {
        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_weather_fragment, container, false);

        dateToday = (TextView) rootView.findViewById(R.id.dateToday);
        windPressure = (TextView) rootView.findViewById(R.id.wind_pressure_today);
        weatherTodayBigPicture = (ImageView) rootView.findViewById(R.id.bigPicture_weather_today);
        temperatureToday = (TextView) rootView.findViewById(R.id.temperature_today);

        View firstDay = rootView.findViewById(R.id.widget1);
        dateFirstDay = (TextView) firstDay.findViewById(R.id.date);
        weatherFirstDayPicture = (ImageView) firstDay.findViewById(R.id.weatherImg);
        temperatureFirstDay = (TextView) firstDay.findViewById(R.id.temperature);

        View secondDay = rootView.findViewById(R.id.widget2);
        dateSecondDay = (TextView) secondDay.findViewById(R.id.date);
        weatherSecondDayPicture = (ImageView) secondDay.findViewById(R.id.weatherImg);
        temperatureSecondDay = (TextView) secondDay.findViewById(R.id.temperature);

        View thirdDay = rootView.findViewById(R.id.widget3);
        dateThirdDay = (TextView) thirdDay.findViewById(R.id.date);
        weatherThirdDayPicture = (ImageView) thirdDay.findViewById(R.id.weatherImg);
        temperatureThirdDay = (TextView) thirdDay.findViewById(R.id.temperature);

        View fourthDay = rootView.findViewById(R.id.widget4);
        dateFourthDay = (TextView) fourthDay.findViewById(R.id.date);
        weatherFourthDayPicture = (ImageView) fourthDay.findViewById(R.id.weatherImg);
        temperatureFourthDay = (TextView) fourthDay.findViewById(R.id.temperature);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* Thread timerThread;


        Runnable runnable = new SetCurrentTime();
        timerThread = new Thread(runnable);
        timerThread.start();*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + handler.getClass().toString());
        handler.post(runnable);
        Activity activity = getActivity();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.ACTION_LOCATION);
        activity.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + handler.getClass().toString());
        handler.removeCallbacks(runnable);
        getActivity().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentWeather.this.updateWeatherData();
        }
    };

    private void renderWeather(WeatherData data) {
        try {
            Forecast[] days = new Forecast[5];
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            for (int i = 0; i < days.length; i++) {
                days[i] = new Forecast(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
                if (i == 0) {
                    days[i].setWeatherInfo(data.info[0]);
                    continue;
                }
                for (WeatherData.WeatherInfo info : data.info) {
                    if (info.getDate().compareTo(days[i].getDate()) == 0) {
                        days[i].setWeatherInfo(info);
                    }
                }
            }

            ImageView[] weatherPicture =
                    new ImageView[]{weatherTodayBigPicture,
                            weatherFirstDayPicture,
                            weatherSecondDayPicture,
                            weatherThirdDayPicture,
                            weatherFourthDayPicture};

            TextView[] temperatureOnDay =
                    new TextView[]{temperatureToday,
                            temperatureFirstDay,
                            temperatureSecondDay,
                            temperatureThirdDay,
                            temperatureFourthDay};

            TextView[] dateOfDay =
                    new TextView[]{dateFirstDay,
                            dateSecondDay,
                            dateThirdDay,
                            dateFourthDay};

            for (int i = 0; i < days.length; i++) {
                switch (days[i].getWeatherType()) {
                    case "Thunderstorm" : weatherPicture[i].setImageResource(R.drawable.ic_storm);
                    break;
                    case "Drizzle" : weatherPicture[i].setImageResource(R.drawable.ic_rain);
                    break;
                    case "Rain" : weatherPicture[i].setImageResource(R.drawable.ic_rain_2);
                    break;
                    case "Snow" : weatherPicture[i].setImageResource(R.drawable.ic_snowing_3);
                    break;
                    case "Clear" : weatherPicture[i].setImageResource(R.drawable.ic_sun);
                    break;
                    case "Clouds" : weatherPicture[i].setImageResource(R.drawable.ic_cloud);
                    break;
                }
            }

            for (int i = 0; i < dateOfDay.length; i++) {
                dateOfDay[i].setText(new SimpleDateFormat("EE,\ndd.MM", Locale.getDefault()).format(days[i].getDate()));
            }

            for (int i = 0; i < days.length; i++) {
                long temperature = days[i].getTemperature();
                if (temperature > 0) {
                    temperatureOnDay[i].setText("+" + temperature + fromHtml("&#176")/* + R.string.temperature_in_gradus*/ + "C");
                } else {
                    temperatureOnDay[i].setText(String.valueOf(temperature) + fromHtml("&#176") + "C");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "One of the fields not found in the JSON data");
        }
    }


    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private void updateWeatherData() {
        new Thread() {
            public void run() {
                WeatherForecastActivity weatherForecastActivity = (WeatherForecastActivity) getActivity();
                final WeatherData data = weatherForecastActivity.fetchWeather();
                if (data == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(data);
                        }
                    });
                }
            }
        }.start();
    }

    public static FragmentWeather newInstance() {
        return new FragmentWeather();
    }


}
