package com.shera.android.meetin.ui;



import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.shera.android.meetin.DatePreference;
import com.shera.android.meetin.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FiltersFragment extends PreferenceFragment {


    private SwitchPreference switchPreference;
    private DatePreference datePreference;

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setRetainInstance(true);

//        final ArrayList<CheckBoxPreference> alViewMode = new ArrayList<>();
        switchPreference = (SwitchPreference) getPreferenceManager().findPreference("endDateFilterEnable");
        datePreference = (DatePreference) getPreferenceManager().findPreference("endDateFilter");
        datePreference.setEnabled(switchPreference.isChecked());
//
//        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                for (CheckBoxPreference cbp : alViewMode) {
//                    if (!cbp.getKey().equals(preference.getKey()) && cbp.isChecked()) {
//                        cbp.setChecked(false);
//                    }
//                    else if (cbp.getKey().equals(preference.getKey()) && !cbp.isChecked()) {
//                        cbp.setChecked(true);
//                    }
//                }
//                return false;
//            }
//        };

        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                datePreference.setEnabled((Boolean)newValue);
                return true;
            }
        });

//        CheckBoxPreference popularFilter = (CheckBoxPreference) getPreferenceManager().findPreference("isPopularFilterOn");
//        popularFilter.setOnPreferenceClickListener(listener);
//
//        CheckBoxPreference successFilter = (CheckBoxPreference) getPreferenceManager().findPreference("isSuccessFilterOn");
//        successFilter.setOnPreferenceClickListener(listener);
//
//        CheckBoxPreference newestFilter = (CheckBoxPreference) getPreferenceManager().findPreference("isNewestFilterOn");
//        newestFilter.setOnPreferenceClickListener(listener);
//
//        CheckBoxPreference fundedFilter = (CheckBoxPreference) getPreferenceManager().findPreference("isFundedFilterOn");
//        fundedFilter.setOnPreferenceClickListener(listener);
//
//        alViewMode.addAll(Arrays.asList(popularFilter,successFilter,newestFilter,fundedFilter));

    }


}
