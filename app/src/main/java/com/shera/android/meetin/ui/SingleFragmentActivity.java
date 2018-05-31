package com.shera.android.meetin.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.shera.android.meetin.R;

import static com.shera.android.meetin.Constants.LOCATION_PERMISSIONS;
import static com.shera.android.meetin.Constants.REQUEST_LOCATION_PERMISSIONS;

/**
 * Created by Shera on 31.01.2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    private DrawerLayout mDrawerLayout;


    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        Intent intent;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_filters:
                            intent = new Intent(SingleFragmentActivity.this, FiltersActivity.class);
                            startActivity(intent);
                            break;
                        case  R.id.nav_saved_projects:
                            intent = new Intent(SingleFragmentActivity.this, MapsActivity.class);
                            startActivity(intent);
                            break;
                    }
                        return true;
                    }
                });
    }
}
