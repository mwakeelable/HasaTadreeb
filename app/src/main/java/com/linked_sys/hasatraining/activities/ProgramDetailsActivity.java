package com.linked_sys.hasatraining.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONObject;

public class ProgramDetailsActivity extends BaseActivity {
    TextView txtProgramID, txtProgramName, txtProgramDate, txtProgramTime, txtProgramLocation;
    String regRef;
    boolean print, rate, comeFromRate;
    CardView btnPrint, btnRate;
    static final int REQUEST_RATE_CODE = 0;

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
        txtProgramID = (TextView) findViewById(R.id.txt_program_id);
        txtProgramName = (TextView) findViewById(R.id.txt_program_name);
        txtProgramDate = (TextView) findViewById(R.id.txt_program_date);
        txtProgramTime = (TextView) findViewById(R.id.txt_program_times);
        txtProgramLocation = (TextView) findViewById(R.id.txt_program_location);
        btnPrint = (CardView) findViewById(R.id.btnPrint);
        btnRate = (CardView) findViewById(R.id.btnRate);
        btnPrint.setVisibility(View.GONE);
        btnRate.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            regRef = bundle.getString("REGREF");
            comeFromRate = bundle.getBoolean("comeFromRate");
            getProgramData();
        }

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  ApiEndPoints.BASE_URL + ApiEndPoints.STUDENT_CERTIFICATE_URL + "?RegREF=" + regRef;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
//                printCertificate();
//                Toast.makeText(ProgramDetailsActivity.this, "تحت التطوير", Toast.LENGTH_SHORT).show();
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rateIntent = new Intent(ProgramDetailsActivity.this, ProgramRateActivity.class);
                rateIntent.putExtra("regRef", regRef);
                startActivityForResult(rateIntent, REQUEST_RATE_CODE);
            }
        });
    }


    private void getProgramData() {
        final String getProgramDataURL = ApiEndPoints.GET_PROGRAM_DATA
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&RegREF=" + regRef;
        ApiHelper api = new ApiHelper(this, getProgramDataURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                JSONObject dataObj = res.optJSONObject("con");
                Log.d(AppController.TAG, res.toString());
                regRef = dataObj.optString("RegREF");
                txtProgramID.setText(dataObj.optString("ProgramID"));
                txtProgramName.setText(dataObj.optString("ProgramName"));
                txtProgramDate.setText(dataObj.optString("ProgramDate"));
                txtProgramTime.setText(dataObj.optString("ProgramTime"));
                txtProgramLocation.setText(dataObj.optString("ProgramLocation"));
                print = dataObj.optBoolean("CanPrintCertificate");
                rate = dataObj.optBoolean("MustRate");
                if (print && !rate) {
                    btnPrint.setVisibility(View.VISIBLE);
                    btnRate.setVisibility(View.GONE);
                } else if (rate && !print) {
                    btnPrint.setVisibility(View.GONE);
                    btnRate.setVisibility(View.VISIBLE);
                } else {
                    btnPrint.setVisibility(View.GONE);
                    btnRate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void printCertificate() {
        final String printCertificateURL = ApiEndPoints.PRINT_CERTIFICATE
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&RegREF=" + regRef;
        ApiHelper api = new ApiHelper(this, printCertificateURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                String url = res.optString("con");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.program_details_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        if (comeFromRate) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RATE_CODE) {
            if (resultCode == RESULT_OK) {
                getProgramData();
            }
        }
    }
}
