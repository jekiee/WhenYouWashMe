package com.example.jek.whenyouwashme.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jek.whenyouwashme.R;

/**
 * Created by jek on 10.08.2017.
 */

public class FragmentLowerPart extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_portrait_lower_fragment, null);
    }
}
