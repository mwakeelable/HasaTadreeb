package com.linked_sys.tadreeb_ihssa.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.linked_sys.tadreeb_ihssa.R;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
