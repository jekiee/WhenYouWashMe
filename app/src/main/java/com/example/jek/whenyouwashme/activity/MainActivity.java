package com.example.jek.whenyouwashme.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jek.whenyouwashme.R;
import com.example.jek.whenyouwashme.fragments.FragmentLowerPart;
import com.example.jek.whenyouwashme.fragments.FragmentRightPart;
import com.example.jek.whenyouwashme.fragments.FragmentWeather;

import static android.R.attr.fragment;
import static android.R.attr.listPopupWindowStyle;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

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

   /* @Override
    protected void onStart() {
        super.onStart();
        //startActivity(new Intent(this, FirebaseActivity.class));
        startActivity(new Intent(this, MapsActivity.class));
    }*/
}