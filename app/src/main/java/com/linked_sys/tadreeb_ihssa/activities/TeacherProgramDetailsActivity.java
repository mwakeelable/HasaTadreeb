package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;
import com.linked_sys.tadreeb_ihssa.network.DownloadTask;

import org.json.JSONObject;

public class TeacherProgramDetailsActivity extends BaseActivity {
    TextView txtProgramRef, txtProgramDays, txtProgramID, txtProgramName, txtProgramDateFrom, txtProgramDateTo, txtProgramTime, txtProgramLocation;
    int pos;
    boolean canPrint, mustAttend;
    LinearLayout btnAbsence, btnPrint;
    static final int REQUEST_ABSENCE_CODE = 0;
    String comeFrom;
    String backAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView backBtn = (ImageView) findViewById(R.id.backImgView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
        titleTxt.setText("تفاصيل البرنامج");
        txtProgramRef = (TextView) findViewById(R.id.txt_program_ref);
        txtProgramDays = (TextView) findViewById(R.id.txt_program_days);
        txtProgramID = (TextView) findViewById(R.id.txt_program_id);
        txtProgramName = (TextView) findViewById(R.id.txt_program_name);
        txtProgramDateFrom = (TextView) findViewById(R.id.txt_program_date_from);
        txtProgramDateTo = (TextView) findViewById(R.id.txt_program_date_to);
        txtProgramTime = (TextView) findViewById(R.id.txt_program_time);
        txtProgramLocation = (TextView) findViewById(R.id.txt_program_location);
        btnAbsence = (LinearLayout) findViewById(R.id.btnAbsence);
        btnPrint = (LinearLayout) findViewById(R.id.btnPrint);
        btnAbsence.setVisibility(View.GONE);
        btnPrint.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
            comeFrom = bundle.getString("comeFrom");
            getProgramDetails();
        }

        btnAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent absenceIntent = new Intent(TeacherProgramDetailsActivity.this, AbsenceActivity.class);
                absenceIntent.putExtra("ref", txtProgramRef.getText().toString());
                startActivityForResult(absenceIntent, REQUEST_ABSENCE_CODE);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printCertificate();
            }
        });
    }

    private void printCertificate() {
        final String printCertificateURL = ApiEndPoints.DOWNLOAD_TEACHER_CERTIFICATE + "?ProgREF=" + txtProgramRef.getText().toString();
        ApiHelper api = new ApiHelper(this, printCertificateURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                String url = res.optString("con");
                new DownloadTask(TeacherProgramDetailsActivity.this, url);
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d(AppController.TAG, "Something went wrong!");
            }
        });
        api.executeRequest(false, false);
    }

    private void getProgramDetails() {
        if (comeFrom.equals("done")) {
            txtProgramRef.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getREF());
            txtProgramDays.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDays());
            txtProgramID.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramName());
            txtProgramDateFrom.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramDateEnd());
            txtProgramTime.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramTimes());
            txtProgramLocation.setText(CacheHelper.getInstance().doneFilteredList.get(pos).getProgramLocation());
            canPrint = CacheHelper.getInstance().doneFilteredList.get(pos).isCanPrintCertificate();
            mustAttend = CacheHelper.getInstance().doneFilteredList.get(pos).isMustAttend();
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
        } else if (comeFrom.equals("attend")) {
            txtProgramRef.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getREF());
            txtProgramDays.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDays());
            txtProgramID.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramName());
            txtProgramDateFrom.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramDateEnd());
            txtProgramTime.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramTimes());
            txtProgramLocation.setText(CacheHelper.getInstance().attendFilteredList.get(pos).getProgramLocation());
            canPrint = CacheHelper.getInstance().attendFilteredList.get(pos).isCanPrintCertificate();
            mustAttend = CacheHelper.getInstance().attendFilteredList.get(pos).isMustAttend();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ABSENCE_CODE && resultCode == RESULT_OK && data != null) {
            comeFrom = data.getStringExtra("comeFrom");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pos = bundle.getInt("pos");
            comeFrom = bundle.getString("comeFrom");
            getProgramDetails();
        }
    }
}
