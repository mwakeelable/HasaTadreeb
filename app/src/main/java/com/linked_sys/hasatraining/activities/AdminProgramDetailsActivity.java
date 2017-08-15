package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;

public class AdminProgramDetailsActivity extends BaseActivity {
    int position;
    TextView txtStudentName, txtStudentMobile, txtStudentID;
    TextView txtProgramName, txtProgramID, txtProgramTime, txtProgramDate,
            txtRegDate, txtProgramStatus;
    String status;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
            status = bundle.getString("status");
        }
        initalizeUI();
        setData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initalizeUI() {
        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtStudentMobile = (TextView) findViewById(R.id.txtStudentMobile);
        txtStudentID = (TextView) findViewById(R.id.txtStudentID);
        txtProgramName = (TextView) findViewById(R.id.txtProgramName);
        txtProgramID = (TextView) findViewById(R.id.txtProgramID);
        txtProgramTime = (TextView) findViewById(R.id.txtProgramTime);
        txtProgramDate = (TextView) findViewById(R.id.txtProgramDate);
        txtRegDate = (TextView) findViewById(R.id.txtProgramRegDate);
        txtProgramStatus = (TextView) findViewById(R.id.txtProgramStatus);
        btnBack = (ImageView) findViewById(R.id.btn_back);
    }

    private void setData() {
        txtStudentName.setText(CacheHelper.getInstance().filteredList.get(position).getMotadarebFullName());
        txtStudentMobile.setText(CacheHelper.getInstance().filteredList.get(position).getMotadarebMobile());
        txtStudentID.setText(CacheHelper.getInstance().filteredList.get(position).getMotadarebID());
        txtProgramName.setText(CacheHelper.getInstance().filteredList.get(position).getProgramName());
        txtProgramID.setText(CacheHelper.getInstance().filteredList.get(position).getProgramID());
        txtProgramTime.setText(CacheHelper.getInstance().filteredList.get(position).getProgramTime());
        txtProgramDate.setText(CacheHelper.getInstance().filteredList.get(position).getProgramDate());
        txtRegDate.setText(CacheHelper.getInstance().filteredList.get(position).getRegistrationDate());
        txtProgramStatus.setText(CacheHelper.getInstance().filteredList.get(position).getProgramStatus());

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.admin_program_details_activity;
    }
}