package com.example.jek.whenyouwashme.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.model.WeatherForecast.RemoteFetch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
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

    Handler handler;

    Calendar calendar;
    Date date;

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
        Thread timerThread;

        Runnable runnable = new SetCurrentTime();
        timerThread = new Thread(runnable);
        timerThread.start();
    }

    private void doWork() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance();
                date = calendar.getTime();
                dateToday.setText(new SimpleDateFormat("EEEE, d MMMM, HH:mm:ss", Locale.ENGLISH).format(date.getTime()));
            }
        });
    }

    private class SetCurrentTime implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateWeatherData();
    }

    private void renderWeather(JSONObject json) {
        try {

            Log.d(TAG, String.valueOf(json));
        } catch (Exception e) {
            Log.e(TAG, "One of the fields not found in the JSON data");
        }
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
}
