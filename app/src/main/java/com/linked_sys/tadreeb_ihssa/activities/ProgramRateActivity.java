package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

public class ProgramRateActivity extends BaseActivity {
    String userID, regREF;
    String num1, num2, num3, num4, num5, num6, num7, num8, num9, num10, num11, num12, num13, num14;
    RadioButton num1Exc, num1VGood, num1Good, num1Pass;
    RadioButton num2Exc, num2VGood, num2Good, num2Pass;
    RadioButton num3Exc, num3VGood, num3Good, num3Pass;
    RadioButton num4Exc, num4VGood, num4Good, num4Pass;
    RadioButton num5Exc, num5VGood, num5Good, num5Pass;
    RadioButton num6Exc, num6VGood, num6Good, num6Pass;
    RadioButton num7Exc, num7VGood, num7Good, num7Pass;
    RadioButton num8Exc, num8VGood, num8Good, num8Pass;
    RadioButton num9Exc, num9VGood, num9Good, num9Pass;
    RadioButton num10Exc, num10VGood, num10Good, num10Pass;
    RadioButton num11Exc, num11VGood, num11Good, num11Pass;
    RadioButton num12Exc, num12VGood, num12Good, num12Pass;
    RadioButton num13Exc, num13VGood, num13Good, num13Pass;
    RadioButton num14Exc, num14VGood, num14Good, num14Pass;
    CardView btnSubmitRate;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmitRate = (CardView) findViewById(R.id.btnSubmitRate);
        defineRadioButtons();
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            regREF = bundle.getString("regRef");
        }
        userID = session.getUserDetails().get(session.KEY_ID);

        btnSubmitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingValues();
                if (!num1.equals("") && !num2.equals("") && !num3.equals("") && !num4.equals("")
                        && !num5.equals("") && !num6.equals("") && !num7.equals("") && !num8.equals("")
                        && !num9.equals("") && !num10.equals("") && !num11.equals("") && !num12.equals("")
                        && !num13.equals("") && !num14.equals(""))
                    submitRating();
                else
                    new MaterialDialog.Builder(ProgramRateActivity.this)
                            .title("تقييم البرنامج")
                            .content("يلزم تقييم كل النقاط لاعتماد التقييم!")
                            .positiveText("تم")
                            .show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void defineRadioButtons() {
        num1Exc = (RadioButton) findViewById(R.id.radioExcNum1);
        num1VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum1);
        num1Good = (RadioButton) findViewById(R.id.radioGoodNum1);
        num1Pass = (RadioButton) findViewById(R.id.radioPassNum1);

        num2Exc = (RadioButton) findViewById(R.id.radioExcNum2);
        num2VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum2);
        num2Good = (RadioButton) findViewById(R.id.radioGoodNum2);
        num2Pass = (RadioButton) findViewById(R.id.radioPassNum2);

        num3Exc = (RadioButton) findViewById(R.id.radioExcNum3);
        num3VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum3);
        num3Good = (RadioButton) findViewById(R.id.radioGoodNum3);
        num3Pass = (RadioButton) findViewById(R.id.radioPassNum3);

        num4Exc = (RadioButton) findViewById(R.id.radioExcNum4);
        num4VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum4);
        num4Good = (RadioButton) findViewById(R.id.radioGoodNum4);
        num4Pass = (RadioButton) findViewById(R.id.radioPassNum4);

        num5Exc = (RadioButton) findViewById(R.id.radioExcNum5);
        num5VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum5);
        num5Good = (RadioButton) findViewById(R.id.radioGoodNum5);
        num5Pass = (RadioButton) findViewById(R.id.radioPassNum5);

        num6Exc = (RadioButton) findViewById(R.id.radioExcNum6);
        num6VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum6);
        num6Good = (RadioButton) findViewById(R.id.radioGoodNum6);
        num6Pass = (RadioButton) findViewById(R.id.radioPassNum6);

        num7Exc = (RadioButton) findViewById(R.id.radioExcNum7);
        num7VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum7);
        num7Good = (RadioButton) findViewById(R.id.radioGoodNum7);
        num7Pass = (RadioButton) findViewById(R.id.radioPassNum7);

        num8Exc = (RadioButton) findViewById(R.id.radioExcNum8);
        num8VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum8);
        num8Good = (RadioButton) findViewById(R.id.radioGoodNum8);
        num8Pass = (RadioButton) findViewById(R.id.radioPassNum8);

        num9Exc = (RadioButton) findViewById(R.id.radioExcNum9);
        num9VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum9);
        num9Good = (RadioButton) findViewById(R.id.radioGoodNum9);
        num9Pass = (RadioButton) findViewById(R.id.radioPassNum9);

        num10Exc = (RadioButton) findViewById(R.id.radioExcNum10);
        num10VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum10);
        num10Good = (RadioButton) findViewById(R.id.radioGoodNum10);
        num10Pass = (RadioButton) findViewById(R.id.radioPassNum10);

        num11Exc = (RadioButton) findViewById(R.id.radioExcNum11);
        num11VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum11);
        num11Good = (RadioButton) findViewById(R.id.radioGoodNum11);
        num11Pass = (RadioButton) findViewById(R.id.radioPassNum11);

        num12Exc = (RadioButton) findViewById(R.id.radioExcNum12);
        num12VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum12);
        num12Good = (RadioButton) findViewById(R.id.radioGoodNum12);
        num12Pass = (RadioButton) findViewById(R.id.radioPassNum12);

        num13Exc = (RadioButton) findViewById(R.id.radioExcNum13);
        num13VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum13);
        num13Good = (RadioButton) findViewById(R.id.radioGoodNum13);
        num13Pass = (RadioButton) findViewById(R.id.radioPassNum13);

        num14Exc = (RadioButton) findViewById(R.id.radioExcNum14);
        num14VGood = (RadioButton) findViewById(R.id.radioVeryGoodNum14);
        num14Good = (RadioButton) findViewById(R.id.radioGoodNum14);
        num14Pass = (RadioButton) findViewById(R.id.radioPassNum14);
    }

    private void setRatingValues() {
        //NUM 1
        if (num1Exc.isChecked())
            num1 = "100";
        else if (num1VGood.isChecked())
            num1 = "75";
        else if (num1Good.isChecked())
            num1 = "50";
        else if (num1Pass.isChecked())
            num1 = "25";
        else if (!num1Exc.isChecked() && !num1VGood.isChecked() && !num1Good.isChecked() && !num1Pass.isChecked())
            num1 = "";

        //NUM 2
        if (num2Exc.isChecked())
            num2 = "100";
        else if (num2VGood.isChecked())
            num2 = "75";
        else if (num2Good.isChecked())
            num2 = "50";
        else if (num2Pass.isChecked())
            num2 = "25";
        else if (!num2Exc.isChecked() && !num2VGood.isChecked() && !num2Good.isChecked() && !num2Pass.isChecked())
            num2 = "";

        //NUM 3
        if (num3Exc.isChecked())
            num3 = "100";
        else if (num3VGood.isChecked())
            num3 = "75";
        else if (num3Good.isChecked())
            num3 = "50";
        else if (num3Pass.isChecked())
            num3 = "25";
        else if (!num3Exc.isChecked() && !num3VGood.isChecked() && !num3Good.isChecked() && !num3Pass.isChecked())
            num3 = "";

        //NUM 4
        if (num4Exc.isChecked())
            num4 = "100";
        else if (num4VGood.isChecked())
            num4 = "75";
        else if (num4Good.isChecked())
            num4 = "50";
        else if (num4Pass.isChecked())
            num4 = "25";
        else if (!num4Exc.isChecked() && !num4VGood.isChecked() && !num4Good.isChecked() && !num4Pass.isChecked())
            num4 = "";

        //NUM 5
        if (num5Exc.isChecked())
            num5 = "100";
        else if (num5VGood.isChecked())
            num5 = "75";
        else if (num5Good.isChecked())
            num5 = "50";
        else if (num5Pass.isChecked())
            num5 = "25";
        else if (!num5Exc.isChecked() && !num5VGood.isChecked() && !num5Good.isChecked() && !num5Pass.isChecked())
            num5 = "";

        //NUM 6
        if (num6Exc.isChecked())
            num6 = "100";
        else if (num6VGood.isChecked())
            num6 = "75";
        else if (num6Good.isChecked())
            num6 = "50";
        else if (num6Pass.isChecked())
            num6 = "25";
        else if (!num6Exc.isChecked() && !num6VGood.isChecked() && !num6Good.isChecked() && !num6Pass.isChecked())
            num6 = "";

        //NUM 7
        if (num7Exc.isChecked())
            num7 = "100";
        else if (num7VGood.isChecked())
            num7 = "75";
        else if (num7Good.isChecked())
            num7 = "50";
        else if (num7Pass.isChecked())
            num7 = "25";
        else if (!num7Exc.isChecked() && !num7VGood.isChecked() && !num7Good.isChecked() && !num7Pass.isChecked())
            num7 = "";

        //NUM 8
        if (num8Exc.isChecked())
            num8 = "100";
        else if (num8VGood.isChecked())
            num8 = "75";
        else if (num8Good.isChecked())
            num8 = "50";
        else if (num8Pass.isChecked())
            num8 = "25";
        else if (!num8Exc.isChecked() && !num8VGood.isChecked() && !num8Good.isChecked() && !num8Pass.isChecked())
            num8 = "";

        //NUM 9
        if (num9Exc.isChecked())
            num9 = "100";
        else if (num9VGood.isChecked())
            num9 = "75";
        else if (num9Good.isChecked())
            num9 = "50";
        else if (num9Pass.isChecked())
            num9 = "25";
        else if (!num9Exc.isChecked() && !num9VGood.isChecked() && !num9Good.isChecked() && !num9Pass.isChecked())
            num9 = "";

        //NUM 10
        if (num10Exc.isChecked())
            num10 = "100";
        else if (num10VGood.isChecked())
            num10 = "75";
        else if (num10Good.isChecked())
            num10 = "50";
        else if (num10Pass.isChecked())
            num10 = "25";
        else if (!num10Exc.isChecked() && !num10VGood.isChecked() && !num10Good.isChecked() && !num10Pass.isChecked())
            num10 = "";

        //NUM 11
        if (num11Exc.isChecked())
            num11 = "100";
        else if (num11VGood.isChecked())
            num11 = "75";
        else if (num11Good.isChecked())
            num11 = "50";
        else if (num11Pass.isChecked())
            num11 = "25";
        else if (!num11Exc.isChecked() && !num11VGood.isChecked() && !num11Good.isChecked() && !num11Pass.isChecked())
            num11 = "";

        //NUM 12
        if (num12Exc.isChecked())
            num12 = "100";
        else if (num12VGood.isChecked())
            num12 = "75";
        else if (num12Good.isChecked())
            num12 = "50";
        else if (num12Pass.isChecked())
            num12 = "25";
        else if (!num12Exc.isChecked() && !num12VGood.isChecked() && !num12Good.isChecked() && !num12Pass.isChecked())
            num12 = "";

        //NUM 13
        if (num13Exc.isChecked())
            num13 = "100";
        else if (num13VGood.isChecked())
            num13 = "75";
        else if (num13Good.isChecked())
            num13 = "50";
        else if (num13Pass.isChecked())
            num13 = "25";
        else if (!num13Exc.isChecked() && !num13VGood.isChecked() && !num13Good.isChecked() && !num13Pass.isChecked())
            num13 = "";

        //NUM 14
        if (num14Exc.isChecked())
            num14 = "100";
        else if (num14VGood.isChecked())
            num14 = "75";
        else if (num14Good.isChecked())
            num14 = "50";
        else if (num14Pass.isChecked())
            num14 = "25";
        else if (!num14Exc.isChecked() && !num14VGood.isChecked() && !num14Good.isChecked() && !num14Pass.isChecked())
            num14 = "";
    }

    private void submitRating() {
        String rateURL = ApiEndPoints.RATE_PROGRAM
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&userID=" + userID + "&RegREF=" + regREF
                + "&num1=" + num1 + "&num2=" + num2 + "&num3=" + num3 + "&num4=" + num4 + "&num5=" + num5
                + "&num6=" + num6 + "&num7=" + num7 + "&num8=" + num8 + "&num9=" + num9 + "&num10=" + num10
                + "&num11=" + num11 + "&num12=" + num12 + "&num13=" + num13 + "&num14=" + num14;
        ApiHelper api = new ApiHelper(this, rateURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                new MaterialDialog.Builder(ProgramRateActivity.this)
                        .title("تقييم البرنامج")
                        .content("تم التقييم بنجاح")
                        .positiveText("تم")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d(AppController.TAG, "Failed");
            }
        });
        api.executeRequest(true, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.program_rate_activity;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("REGREF",regREF);
        intent.putExtra("comeFromRate", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}
