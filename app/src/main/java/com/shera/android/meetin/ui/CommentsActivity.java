package com.shera.android.meetin.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.shera.android.meetin.entities.Project;

public class CommentsActivity extends SingleFragmentActivity {

    public static final String EXTRA_PROJECT =
            "com.shera.android.meetin.project";

    public static Intent newIntent(Context context, Project project) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Project project = (Project) getIntent()
                .getSerializableExtra(EXTRA_PROJECT);
        return CommentsFragment.newInstance(project);
    }
}