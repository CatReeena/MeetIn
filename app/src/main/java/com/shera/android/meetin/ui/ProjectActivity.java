package com.shera.android.meetin.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProjectActivity extends SingleFragmentActivity implements ProjectFragment.Callbacks {

    public static final String EXTRA_PROJECT_ID =
            "com.shera.android.meetin.project_id";

    public static Intent newIntent(Context context, Long projectId) {
        Intent intent = new Intent(context, ProjectActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        Long projectId = (Long) getIntent()
                .getSerializableExtra(EXTRA_PROJECT_ID);
        return ProjectFragment.newInstance(projectId);
    }

    @Override
    public void onUnexistingProjectBehavior() {
        finish();
    }
}

