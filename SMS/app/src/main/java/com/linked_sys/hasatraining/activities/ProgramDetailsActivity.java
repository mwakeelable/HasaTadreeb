package com.linked_sys.hasatraining.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

public class ProgramDetailsActivity extends BaseActivity {
    LinearLayout placeholder;
    String id, ref, name, days, times, timeStart, dateStart;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View shadow = findViewById(R.id.toolbar_shadow);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shadow.setVisibility(View.VISIBLE);
        else
            shadow.setVisibility(View.GONE);
        TextView txtID = (TextView) findViewById(R.id.txt_program_id);
        TextView txtRef = (TextView) findViewById(R.id.txt_program_ref);
        TextView txtName = (TextView) findViewById(R.id.txt_program_name);
        TextView txtDays = (TextView) findViewById(R.id.txt_program_days);
        TextView txtTimes = (TextView) findViewById(R.id.txt_program_times);
        TextView txtTimeStart = (TextView) findViewById(R.id.txt_program_time_start);
        TextView txtDateStart = (TextView) findViewById(R.id.txt_program_dates_start);
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        btnRegister = (Button) findViewById(R.id.btn_register_program);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            ref = bundle.getString("ref");
            name = bundle.getString("name");
            days = bundle.getString("days");
            times = bundle.getString("times");
            timeStart = bundle.getString("timeStart");
            dateStart = bundle.getString("DateStart");
            txtID.setText(id);
            txtRef.setText(ref);
            txtName.setText(name);
            txtDays.setText(days);
            txtTimes.setText(times);
            txtTimeStart.setText(timeStart);
            txtDateStart.setText(dateStart);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doRegisterProgram();
                }
            });
        } else {
            placeholder.setVisibility(View.VISIBLE);
        }
    }

    private void doRegisterProgram() {
        final String registerURL = ApiEndPoints.REGISTER_PROGRAM_URL
                + "?ACODE=" + session.getUserToken()
                + "&ID_Number=" + session.getUserDetails().get(session.KEY_REF)
                + "&APP_Number=" + id;
        ApiHelper registerAPI = new ApiHelper(this, registerURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG,response.toString());
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d(AppController.TAG,"Failed");
            }
        });
        registerAPI.executeRequest(true, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.program_details_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}
