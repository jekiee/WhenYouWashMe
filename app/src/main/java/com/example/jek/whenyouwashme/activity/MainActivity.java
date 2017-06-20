package com.example.jek.whenyouwashme.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jek.whenyouwashme.R;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        } else {
            //Log.d(TAG, "switch to else");
            setContentView(R.layout.activity_main);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(this, FirebaseActivity.class));
    }
}