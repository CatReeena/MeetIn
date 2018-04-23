package com.shera.android.meetin;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CrimeFragment extends Fragment {

    public static ProjectListFragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
