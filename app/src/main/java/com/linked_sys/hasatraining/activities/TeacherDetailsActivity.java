package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;

public class TeacherDetailsActivity extends BaseActivity {
    int position;
    TextView txtTeacherName, txtTeacherMobile, txtTeacherID,
            txtSpecalize, txtCurrentWork, txtCase, txtRef;

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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_details_activity;
    }

    private void initalizeUI() {
        txtTeacherName = (TextView) findViewById(R.id.txtTeacherName);
        txtTeacherMobile = (TextView) findViewById(R.id.txtTeacherMobile);
        txtTeacherID = (TextView) findViewById(R.id.txtTeacherID);
        txtSpecalize = (TextView) findViewById(R.id.txtTeacherSpecialize);
        txtCurrentWork = (TextView) findViewById(R.id.txtTeacherCurrentWork);
        txtCase = (TextView) findViewById(R.id.txtTeacherCase);
        txtRef = (TextView) findViewById(R.id.txtTeacherRef);
    }

    private void setData() {
        txtTeacherName.setText(CacheHelper.getInstance().teachersList.get(position).getName());
        txtTeacherMobile.setText(CacheHelper.getInstance().teachersList.get(position).getMobile());
        txtTeacherID.setText(CacheHelper.getInstance().teachersList.get(position).getId());
        txtSpecalize.setText(CacheHelper.getInstance().teachersList.get(position).getSpecialize());
        txtCurrentWork.setText(CacheHelper.getInstance().teachersList.get(position).getCurrentWork());
        txtCase.setText(CacheHelper.getInstance().teachersList.get(position).getCase());
        txtRef.setText(CacheHelper.getInstance().teachersList.get(position).getIDREF());
    }

}
