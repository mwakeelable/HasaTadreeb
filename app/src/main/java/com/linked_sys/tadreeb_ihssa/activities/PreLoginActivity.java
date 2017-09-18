package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.linked_sys.tadreeb_ihssa.R;

public class PreLoginActivity extends BaseActivity {
    LinearLayout btnAdminLogin, btnStudentLogin, btnTeacherLogin;
    Intent loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnAdminLogin = (LinearLayout) findViewById(R.id.btnAdminLogin);
        btnStudentLogin = (LinearLayout) findViewById(R.id.btnStudentLogin);
        btnTeacherLogin = (LinearLayout) findViewById(R.id.btnTeacherLogin);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent = new Intent(PreLoginActivity.this, SignInActivity.class);
                loginIntent.putExtra("userType", 2);
                startActivity(loginIntent);
            }
        });

        btnStudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent = new Intent(PreLoginActivity.this, SignInActivity.class);
                loginIntent.putExtra("userType", 1);
                startActivity(loginIntent);
            }
        });

        btnTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent = new Intent(PreLoginActivity.this, SignInActivity.class);
                loginIntent.putExtra("userType", 0);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_pre_login;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}