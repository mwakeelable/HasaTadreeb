package com.linked_sys.hasatraining.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.fragments.RegisterProgramDetailsFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramFourFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramOneFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramThreeFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramTwoFragment;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterProgramActivity extends BaseActivity {
    CardView firstStep, secondStep, thirdStep, fourthStep, chooseProgram;
    LinearLayout btnAcceptLicence;
    Button btnValidData, btnInvalidData, btnSubmitRegister, btnNewRegister, btnClose, btnSubmit;
    RegisterProgramOneFragment FRAGMENT_STEP_ONE;
    RegisterProgramTwoFragment FRAGMENT_STEP_TWO;
    RegisterProgramThreeFragment FRAGMENT_STEP_THREE;
    RegisterProgramFourFragment FRAGMENT_STEP_FOUR;
    RegisterProgramDetailsFragment FRAGMENT_PROGRAM_DETAILS;
    public String periodRef, userID, userMobile, userName;
    boolean finish;

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
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_register));
        firstStep = (CardView) findViewById(R.id.firstStepContainer);
        secondStep = (CardView) findViewById(R.id.secondStepContainer);
        thirdStep = (CardView) findViewById(R.id.thirdStepContainer);
        fourthStep = (CardView) findViewById(R.id.fourthStepContainer);
        chooseProgram = (CardView) findViewById(R.id.chooseProgramStepContainer);

        btnAcceptLicence = (LinearLayout) findViewById(R.id.btnAcceptLicence);
        btnValidData = (Button) findViewById(R.id.btnValidData);
        btnInvalidData = (Button) findViewById(R.id.btnInvalidData);
        btnSubmitRegister = (Button) findViewById(R.id.btnSubmitRegister);
        btnNewRegister = (Button) findViewById(R.id.btnNewRegister);
        btnSubmit = (Button) findViewById(R.id.btnSubmitRegisterInProgram);
        btnClose = (Button) findViewById(R.id.btnClose);
        FRAGMENT_STEP_ONE = new RegisterProgramOneFragment();
        firstStep.setVisibility(View.VISIBLE);
        secondStep.setVisibility(View.GONE);
        thirdStep.setVisibility(View.GONE);
        fourthStep.setVisibility(View.GONE);
        chooseProgram.setVisibility(View.GONE);
        drawFirstStepFragment(FRAGMENT_STEP_ONE);
        btnAcceptLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FRAGMENT_STEP_ONE.chkAcceptLicence.isChecked()) {
                    FRAGMENT_STEP_TWO = new RegisterProgramTwoFragment();
                    firstStep.setVisibility(View.GONE);
                    secondStep.setVisibility(View.VISIBLE);
                    thirdStep.setVisibility(View.GONE);
                    fourthStep.setVisibility(View.GONE);
                    chooseProgram.setVisibility(View.GONE);
                    drawFragment(FRAGMENT_STEP_TWO);
                } else {
                    new MaterialDialog.Builder(RegisterProgramActivity.this)
                            .title("خطــأ")
                            .content("لم توافق على الشروط")
                            .positiveText("تم")
                            .show();
                }
            }
        });

        btnValidData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FRAGMENT_STEP_THREE = new RegisterProgramThreeFragment();
                firstStep.setVisibility(View.GONE);
                secondStep.setVisibility(View.GONE);
                thirdStep.setVisibility(View.VISIBLE);
                fourthStep.setVisibility(View.GONE);
                chooseProgram.setVisibility(View.GONE);
                drawFragment(FRAGMENT_STEP_THREE);
            }
        });

        btnInvalidData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(RegisterProgramActivity.this)
                        .title(getResources().getString(R.string.txtMessageLabel))
                        .input(null, "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                final StringBuilder sb = new StringBuilder(input.length());
                                sb.append(input);
                                String comment = sb.toString();
                                sendMessage(comment);
                            }
                        }).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkProgram();
            }
        });

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterProgramActivity.this, "الرجاء اختيار البرنامج اولا", Toast.LENGTH_SHORT).show();
            }
        });

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStep.setVisibility(View.GONE);
                secondStep.setVisibility(View.GONE);
                thirdStep.setVisibility(View.VISIBLE);
                fourthStep.setVisibility(View.GONE);
                chooseProgram.setVisibility(View.GONE);
                drawFragment(FRAGMENT_STEP_THREE);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterProgramActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (finish){
            finish();
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }else if (FRAGMENT_STEP_TWO != null && FRAGMENT_STEP_TWO.isVisible()) {
            firstStep.setVisibility(View.VISIBLE);
            secondStep.setVisibility(View.GONE);
            thirdStep.setVisibility(View.GONE);
            fourthStep.setVisibility(View.GONE);
            chooseProgram.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else if (FRAGMENT_STEP_THREE != null && FRAGMENT_STEP_THREE.isVisible()) {
            firstStep.setVisibility(View.GONE);
            secondStep.setVisibility(View.VISIBLE);
            thirdStep.setVisibility(View.GONE);
            fourthStep.setVisibility(View.GONE);
            chooseProgram.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else if (FRAGMENT_PROGRAM_DETAILS != null && FRAGMENT_PROGRAM_DETAILS.isVisible()) {
            firstStep.setVisibility(View.GONE);
            secondStep.setVisibility(View.VISIBLE);
            thirdStep.setVisibility(View.VISIBLE);
            fourthStep.setVisibility(View.GONE);
            chooseProgram.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else if (FRAGMENT_STEP_FOUR != null && FRAGMENT_STEP_FOUR.isVisible()) {
            finish();
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.register_program_activity;
    }

    private void drawFirstStepFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerContainerView, fragment);
        transaction.commit();
    }

    private void drawFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void drawProgramDetails(int pos) {
        firstStep.setVisibility(View.GONE);
        secondStep.setVisibility(View.GONE);
        thirdStep.setVisibility(View.GONE);
        fourthStep.setVisibility(View.GONE);
        chooseProgram.setVisibility(View.VISIBLE);
        FRAGMENT_PROGRAM_DETAILS = new RegisterProgramDetailsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerContainerView, FRAGMENT_PROGRAM_DETAILS);
        transaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("REF", CacheHelper.getInstance().programByPeriods.get(pos).getREF());
        FRAGMENT_PROGRAM_DETAILS.setArguments(bundle);
        transaction.commit();
    }

    private void sendMessage(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("UserId", userID);
        map.put("UserFullName", userName);
        map.put("userMobile", userMobile);
        map.put("MailMessage", message);
        ApiHelper apiHelper = new ApiHelper(this, ApiEndPoints.SEND_MSG, Request.Method.POST, map, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                new MaterialDialog.Builder(RegisterProgramActivity.this)
                        .title("مراسلة الادارة")
                        .content("تم ارسال الرسالة للادارة بشكل صحيح")
                        .positiveText("تم")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                RegisterProgramActivity.this.finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        apiHelper.executePostRequest(true);
    }

    private void submitRegister() {
        String url = ApiEndPoints.SUBMIT_REGISTER
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID)
                + "&ProgRef=" + FRAGMENT_PROGRAM_DETAILS.regRef;
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                if (res.optString("retmessage").equals("Success")) {
                    new MaterialDialog.Builder(RegisterProgramActivity.this)
                            .title("التسجيل")
                            .content("تم التسجيل بنجاح")
                            .positiveText("إنهـاء")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    RegisterProgramActivity.this.finish();
                                }
                            })
                            .negativeText("تسجيل جديد")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    FRAGMENT_STEP_THREE = new RegisterProgramThreeFragment();
                                    firstStep.setVisibility(View.GONE);
                                    secondStep.setVisibility(View.GONE);
                                    thirdStep.setVisibility(View.VISIBLE);
                                    fourthStep.setVisibility(View.GONE);
                                    chooseProgram.setVisibility(View.GONE);
                                    drawFragment(FRAGMENT_STEP_THREE);
                                    finish = true;
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void checkProgram() {
        String url = ApiEndPoints.CHECK_PROGRAM_EXIST
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID)
                + "&ProgRef=" + FRAGMENT_PROGRAM_DETAILS.regRef;
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                if (res.optString("message").equals("false")) {
                    submitRegister();
                } else {
                    new MaterialDialog.Builder(RegisterProgramActivity.this)
                            .title("خطــأ")
                            .content("لا يمكن التسجيل بهذا البرنامج")
                            .positiveText("تم")
                            .show();
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

}
