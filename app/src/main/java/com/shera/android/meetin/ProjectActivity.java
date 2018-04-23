package com.shera.android.meetin;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import java.util.UUID;

public class ProjectActivity extends SingleFragmentActivity {

    public static final String EXTRA_PROJECT_ID =
            "com.shera.android.meetin.project_id";

    public static Intent newIntent(Context context, UUID projectId) {
        Intent intent = new Intent(context, ProjectActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID projectId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PROJECT_ID);
        return ProjectFragment.newInstance(projectId);
    }
}

