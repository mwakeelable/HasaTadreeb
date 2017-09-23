package com.linked_sys.tadreeb_ihssa.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.linked_sys.tadreeb_ihssa.R;

public class SplashActivity extends BaseActivity {
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
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
    }
}
