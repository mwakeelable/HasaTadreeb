package com.linked_sys.hasatraining.activities;

import android.content.Intent;
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
    String comeFrom;

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
            comeFrom = bundle.getString("comeFrom");
            if (comeFrom.equals("done")){
                txtProgramRef.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getREF());
                txtProgramDays.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramDays());
                txtProgramID.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramID());
                txtProgramName.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramName());
                txtProgramDateFrom.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramDateStrat());
                txtProgramDateTo.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramDateEnd());
                txtProgramTime.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramTimes());
                txtProgramLocation.setText(CacheHelper.getInstance().teacherDonePrograms.get(pos).getProgramLocation());
                canPrint = CacheHelper.getInstance().teacherDonePrograms.get(pos).isCanPrintCertificate();
                mustAttend = CacheHelper.getInstance().teacherDonePrograms.get(pos).isMustAttend();
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
            }else if (comeFrom.equals("attend")){
                txtProgramRef.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getREF());
                txtProgramDays.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramDays());
                txtProgramID.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramID());
                txtProgramName.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramName());
                txtProgramDateFrom.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramDateStrat());
                txtProgramDateTo.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramDateEnd());
                txtProgramTime.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramTimes());
                txtProgramLocation.setText(CacheHelper.getInstance().teacherAttendPrograms.get(pos).getProgramLocation());
                canPrint = CacheHelper.getInstance().teacherAttendPrograms.get(pos).isCanPrintCertificate();
                mustAttend = CacheHelper.getInstance().teacherAttendPrograms.get(pos).isMustAttend();
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

        btnAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent absenceIntent = new Intent(TeacherProgramDetailsActivity.this, AbsenceActivity.class);
                absenceIntent.putExtra("ref", txtProgramRef.getText().toString());
                startActivityForResult(absenceIntent, REQUEST_ABSENCE_CODE);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.teacher_program_details_activity;
    }
}
