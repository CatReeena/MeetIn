package com.shera.android.meetin.ui;

import android.app.Fragment;
import android.os.Bundle;

import android.preference.PreferenceActivity;


public class FiltersActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);
        if (fragment == null || !fragment.getClass().equals(FiltersFragment.class)) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new FiltersFragment()).commit();
        }
    }
}