package com.linked_sys.hasatraining.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.fragments.RegisterProgramFourFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramOneFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramThreeFragment;
import com.linked_sys.hasatraining.fragments.RegisterProgramTwoFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterProgramActivity extends BaseActivity {
    CardView firstStep, secondStep, thirdStep, fourthStep;
    LinearLayout btnAcceptLicence;
    Button btnValidData, btnInvalidData, btnSubmitRegister, btnNewRegister, btnClose;
    RegisterProgramOneFragment FRAGMENT_STEP_ONE;
    RegisterProgramTwoFragment FRAGMENT_STEP_TWO;
    RegisterProgramThreeFragment FRAGMENT_STEP_THREE;
    RegisterProgramFourFragment FRAGMENT_STEP_FOUR;
    public String periodRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        btnAcceptLicence = (LinearLayout) findViewById(R.id.btnAcceptLicence);
        btnValidData = (Button) findViewById(R.id.btnValidData);
        btnInvalidData = (Button) findViewById(R.id.btnInvalidData);
        btnSubmitRegister = (Button) findViewById(R.id.btnSubmitRegister);
        btnNewRegister = (Button) findViewById(R.id.btnNewRegister);
        btnClose = (Button) findViewById(R.id.btnClose);
        FRAGMENT_STEP_ONE = new RegisterProgramOneFragment();
        firstStep.setVisibility(View.VISIBLE);
        secondStep.setVisibility(View.GONE);
        thirdStep.setVisibility(View.GONE);
        fourthStep.setVisibility(View.GONE);
        drawFragment(FRAGMENT_STEP_ONE);
        btnAcceptLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FRAGMENT_STEP_ONE.chkAcceptLicence.isChecked()){
                    FRAGMENT_STEP_TWO = new RegisterProgramTwoFragment();
                    firstStep.setVisibility(View.GONE);
                    secondStep.setVisibility(View.VISIBLE);
                    thirdStep.setVisibility(View.GONE);
                    fourthStep.setVisibility(View.GONE);
                    drawFragment(FRAGMENT_STEP_TWO);
                }else {
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
                                try {
                                    String comment = URLEncoder.encode(String.valueOf(input), "utf-8");
                                    //Send Message
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
            }
        });

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FRAGMENT_STEP_FOUR = new RegisterProgramFourFragment();
                firstStep.setVisibility(View.GONE);
                secondStep.setVisibility(View.GONE);
                thirdStep.setVisibility(View.GONE);
                fourthStep.setVisibility(View.VISIBLE);
                drawFragment(FRAGMENT_STEP_FOUR);
            }
        });

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstStep.setVisibility(View.VISIBLE);
                secondStep.setVisibility(View.GONE);
                thirdStep.setVisibility(View.GONE);
                fourthStep.setVisibility(View.GONE);
                drawFragment(FRAGMENT_STEP_ONE);
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
    protected int getLayoutResourceId() {
        return R.layout.register_program_activity;
    }

    private void drawFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.registerContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
