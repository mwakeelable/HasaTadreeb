package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;

public class TeacherDetailsActivity extends BaseActivity {
    TextView txtTeacherName, txtTeacherMobile, txtTeacherID,
            txtSpecalize, txtCurrentWork, txtCase, txtRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        txtTeacherName = (TextView) findViewById(R.id.txtTeacherName);
        txtTeacherMobile = (TextView) findViewById(R.id.txtTeacherMobile);
        txtTeacherID = (TextView) findViewById(R.id.txtTeacherID);
        txtSpecalize = (TextView) findViewById(R.id.txtTeacherSpecialize);
        txtCurrentWork = (TextView) findViewById(R.id.txtTeacherCurrentWork);
        txtCase = (TextView) findViewById(R.id.txtTeacherCase);
        txtRef = (TextView) findViewById(R.id.txtTeacherRef);
        if (bundle != null) {
            txtTeacherName.setText(bundle.getString("name"));
            txtTeacherMobile.setText(bundle.getString("mobile"));
            txtTeacherID.setText(bundle.getString("ID"));
            txtSpecalize.setText(bundle.getString("specialize"));
            txtCurrentWork.setText(bundle.getString("work"));
            txtCase.setText(bundle.getString("case"));
            txtRef.setText(bundle.getString("idRef"));
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_details_activity;
    }
}
