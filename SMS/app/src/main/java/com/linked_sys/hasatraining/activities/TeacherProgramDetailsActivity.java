package com.linked_sys.hasatraining.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;

public class TeacherProgramDetailsActivity extends BaseActivity {
    TextView txtProgramRef, txtProgramDays, txtProgramID, txtProgramName, txtProgramDateFrom, txtProgramDateTo, txtProgramTime, txtProgramLocation;
    int pos;
    boolean canPrint, mustAttend;
    CardView btnAbsence, btnPrint;
    static final int REQUEST_ABSENCE_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View shadow = findViewById(R.id.toolbar_shadow);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shadow.setVisibility(View.VISIBLE);
        else
            shadow.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtProgramRef = (TextView) findViewById(R.id.txt_program_ref);
        txtProgramDays = (TextView) findViewById(R.id.txt_program_days);
        txtProgramID = (TextView) findViewById(R.id.txt_program_id);
        txtProgramName = (TextView) findViewById(R.id.txt_program_name);
        txtProgramDateFrom = (TextView) findViewById(R.id.txt_program_date_from);
        txtProgramDateTo = (TextView) findViewById(R.id.txt_program_date_to);
        txtProgramTime = (TextView) findViewById(R.id.txt_program_time);
        txtProgramLocation = (TextView) findViewById(R.id.txt_program_location);
        btnAbsence = (CardView) findViewById(R.id.btnAbsence);
        btnPrint = (CardView) findViewById(R.id.btnPrint);
        btnAbsence.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
            txtProgramRef.setText(CacheHelper.getInstance().programs.get(pos).getREF());
            txtProgramDays.setText(CacheHelper.getInstance().programs.get(pos).getProgramDays());
            txtProgramID.setText(CacheHelper.getInstance().programs.get(pos).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().programs.get(pos).getProgramName());
            txtProgramDateFrom.setText(CacheHelper.getInstance().programs.get(pos).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().programs.get(pos).getProgramDateEnd());
            txtProgramTime.setText(CacheHelper.getInstance().programs.get(pos).getProgramTimes());
            txtProgramLocation.setText(CacheHelper.getInstance().programs.get(pos).getProgramLocation());
            canPrint = CacheHelper.getInstance().programs.get(pos).isCanPrintCertificate();
            mustAttend = CacheHelper.getInstance().programs.get(pos).isMustAttend();
            if (canPrint && !mustAttend) {
                btnPrint.setVisibility(View.VISIBLE);
                btnAbsence.setVisibility(View.GONE);
            } else if (mustAttend && !canPrint) {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.VISIBLE);
            } else {
                btnPrint.setVisibility(View.GONE);
                btnAbsence.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_program_details_activity;
    }
}
