package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.os.Handler;

import com.linked_sys.hasatraining.R;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SPLASH_TIME_OUT = 1000;
        if (session.isLoggedIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openActivity(MainActivity.class);
                }
            }, SPLASH_TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    session.checkLogin();
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
