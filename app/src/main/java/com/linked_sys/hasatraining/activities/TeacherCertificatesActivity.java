package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.linked_sys.hasatraining.R;

public class TeacherCertificatesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_certificates_activity;
    }

}
