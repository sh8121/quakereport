package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference magPref = findPreference(getString(R.string.settings_min_magnitude_key));
            String magPrefDefault = getString(R.string.settings_min_magnitude_default);
            bindPreferenceSummaryToValue(magPref, magPrefDefault);

            Preference orderByPref = findPreference(getString(R.string.settings_order_by_key));
            String orderByDefault = getString(R.string.settings_order_by_default);
            bindPreferenceSummaryToValue(orderByPref, orderByDefault);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String value = o.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference)preference;
                int prefIndex = listPreference.findIndexOfValue(value);
                if(prefIndex >= 0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else{
                preference.setSummary(value);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference, String defaultVal){
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String prefString = prefs.getString(preference.getKey(), defaultVal);
            onPreferenceChange(preference, prefString);
        }
    }
}


