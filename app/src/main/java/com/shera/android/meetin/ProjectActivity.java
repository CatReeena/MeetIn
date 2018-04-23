package com.shera.android.meetin;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context, UUID projectId) {
        return new Intent(context, CrimeActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return CrimeFragment.newInstance();
    }
}

