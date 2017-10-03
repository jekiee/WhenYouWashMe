package com.example.jek.whenyouwashme.fragments;

import android.app.Fragment;
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
import com.example.jek.whenyouwashme.model.weatherForecast.RemoteFetch;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jek on 10.08.2017.
 */

public class FragmentWeather extends Fragment {
    private final static String TAG = FragmentWeather.class.getSimpleName();

    TextView dateToday;
    TextView windPressure;
    ImageView weatherTodayBigPicture;
    TextView tempertureToday;

    ImageView weatherFirstDayPicture;
    TextView tempertureFirstDay;
    TextView dateFirstDay;

    TextView dateSecondDay;
    ImageView weatherSecondDayPicture;
    TextView tempertureSecondDay;

    TextView dateThirdDay;
    ImageView weatherThisrdDayPicture;
    TextView tempertureThirdDay;

    TextView dateFourthDay;
    ImageView weatherFourthDayPicture;
    TextView tempertureFourthDay;

    Calendar calendar;
    Date date;

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
        tempertureToday = (TextView) rootView.findViewById(R.id.temperature_today);

        View firstDay = rootView.findViewById(R.id.widget1);
        dateFirstDay = (TextView) firstDay.findViewById(R.id.date);
        weatherFirstDayPicture = (ImageView) firstDay.findViewById(R.id.weatherImg);
        tempertureFirstDay = (TextView) firstDay.findViewById(R.id.temperature);

        View secondDay = rootView.findViewById(R.id.widget2);
        dateSecondDay = (TextView) secondDay.findViewById(R.id.date);
        weatherSecondDayPicture = (ImageView) secondDay.findViewById(R.id.weatherImg);
        tempertureSecondDay = (TextView) secondDay.findViewById(R.id.temperature);

        View thirdDay = rootView.findViewById(R.id.widget3);
        dateThirdDay = (TextView) thirdDay.findViewById(R.id.date);
        weatherThisrdDayPicture = (ImageView) thirdDay.findViewById(R.id.weatherImg);
        tempertureThirdDay = (TextView) thirdDay.findViewById(R.id.temperature);

        View fourthDay = rootView.findViewById(R.id.widget4);
        dateFourthDay = (TextView) fourthDay.findViewById(R.id.date);
        weatherFourthDayPicture = (ImageView) fourthDay.findViewById(R.id.weatherImg);
        tempertureFourthDay = (TextView) fourthDay.findViewById(R.id.temperature);

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
        updateWeatherData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + handler.getClass().toString());
        handler.post(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + handler.getClass().toString());
        handler.removeCallbacks(runnable);
    }

    private void renderWeather(JSONObject json) {
        try {
            JSONObject wind = json.getJSONArray("list").getJSONObject(0).getJSONObject("wind");
            JSONObject weatherToday = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0);
            JSONObject weatherActual = json.getJSONArray("list").getJSONObject(0).getJSONObject("main");
            String temperatureActualString = weatherActual.getString("temp");
            String pressureActual = weatherActual.getString("pressure");
            //long temperature = Math.round(Double.parseDouble(temperatureActualString));
            long temperature = Math.round(Double.parseDouble(temperatureActualString));
            if (temperature > 0) {
                tempertureToday.setText("+" + temperature + fromHtml("&#176")/* + R.string.temperature_in_gradus*/ + "C");
            } else {
                tempertureToday.setText(String.valueOf(temperature) + fromHtml("&#176") + "C");
            }
            //int temperature = Integer.valueOf(temperatureActualString);
            Log.d(TAG, "\u00B0");
            Log.d(TAG, weatherToday.getString("main"));
            Log.d(TAG, String.valueOf(wind));
            Log.d(TAG, String.valueOf(weatherToday));
            Log.d(TAG, String.valueOf(json));
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
                final JSONObject json = RemoteFetch.getJSON(getActivity());
                if (json == null) {
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
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    public static FragmentWeather newInstance() {
        FragmentWeather fragmentWeather = new FragmentWeather();
        return fragmentWeather;
    }
}
