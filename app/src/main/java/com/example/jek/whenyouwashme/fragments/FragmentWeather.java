package com.example.jek.whenyouwashme.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jek.whenyouwashme.R;

import java.text.DateFormat;

/**
 * Created by jek on 10.08.2017.
 */

public class FragmentWeather extends Fragment {
    TextView date;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_weather_fragment, container, false);
        date = (TextView) rootView.findViewById(R.id.date);

        return inflater.inflate(R.layout.activity_main_weather_fragment, null);
    }
}
