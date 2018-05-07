package com.shera.android.meetin.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProjectListActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, ProjectListActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return ProjectListFragment.newInstance();
    }
}

