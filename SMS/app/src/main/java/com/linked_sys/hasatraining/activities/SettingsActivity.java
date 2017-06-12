package com.linked_sys.hasatraining.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.fragments.SettingsFragment;

import static android.R.attr.key;

public class SettingsActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences prefs;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.settingsContainer, new SettingsFragment())
                    .commit();
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.settings_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Intent thisActivity = getIntent();
        if (key.equals("switch_user")){
            user = prefs.getString("switch_user", "1");
            if (user.equals("1")){
                session.setUserViewID(1);
                restartActivity(thisActivity);
            }else {
                session.setUserViewID(2);
                restartActivity(thisActivity);
            }
        }
    }
}
