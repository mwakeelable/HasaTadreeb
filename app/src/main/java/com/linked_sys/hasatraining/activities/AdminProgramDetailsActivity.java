package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;

public class AdminProgramDetailsActivity extends BaseActivity {
    int position;
    TextView txtStudentName, txtStudentMobile, txtStudentID;
    TextView txtProgramName, txtProgramID, txtProgramTime, txtProgramDate,
            txtRegDate, txtProgramStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
        }
        initalizeUI();
        setData();
    }

    private void initalizeUI(){
        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtStudentMobile = (TextView) findViewById(R.id.txtStudentMobile);
        txtStudentID = (TextView) findViewById(R.id.txtStudentID);
        txtProgramName = (TextView) findViewById(R.id.txtProgramName);
        txtProgramID = (TextView) findViewById(R.id.txtProgramID);
        txtProgramTime = (TextView) findViewById(R.id.txtProgramTime);
        txtProgramDate = (TextView) findViewById(R.id.txtProgramDate);
        txtRegDate = (TextView) findViewById(R.id.txtProgramRegDate);
        txtProgramStatus = (TextView) findViewById(R.id.txtProgramStatus);
    }

    private void setData() {
        txtStudentName.setText(CacheHelper.getInstance().adminPrograms.get(position).getMotadarebFullName());
        txtStudentMobile.setText(CacheHelper.getInstance().adminPrograms.get(position).getMotadarebMobile());
        txtStudentID.setText(CacheHelper.getInstance().adminPrograms.get(position).getMotadarebID());
        txtProgramName.setText(CacheHelper.getInstance().adminPrograms.get(position).getProgramName());
        txtProgramID.setText(CacheHelper.getInstance().adminPrograms.get(position).getProgramID());
        txtProgramTime.setText(CacheHelper.getInstance().adminPrograms.get(position).getProgramTime());
        txtProgramDate.setText(CacheHelper.getInstance().adminPrograms.get(position).getProgramDate());
        txtRegDate.setText(CacheHelper.getInstance().adminPrograms.get(position).getRegistrationDate());
        txtProgramStatus.setText(CacheHelper.getInstance().adminPrograms.get(position).getProgramStatus());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.admin_program_details_activity;
    }

}
