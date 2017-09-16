package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.components.SpinnerDialog;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONObject;

public class SignInActivity extends BaseActivity {
    EditText txt_email, txt_password, txt_userType;
    SpinnerDialog mProgress;
    AwesomeValidation awesomeValidation;
    String nationalID, mobileNumber;
    int userType;
    ImageView userTypeImg;
    TextView userTypeTxt;
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgress = new SpinnerDialog(this);
        txt_email = (EditText) findViewById(R.id.txt_identity);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_userType = (EditText) findViewById(R.id.txt_user_type);
        userTypeImg = (ImageView) findViewById(R.id.user_type_image);
        userTypeTxt = (TextView) findViewById(R.id.user_type_txt);
        txt_password.clearFocus();
        txt_email.clearFocus();
        Bundle extra = getIntent().getExtras();
        container = (RelativeLayout) findViewById(R.id.login_container);
        if (extra != null) {
            userType = extra.getInt("userType");
            if (userType == 0) {
                userTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.trainer_icon));
                userTypeTxt.setText("المدرب");
            } else if (userType == 1) {
                userTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.trainee_icon));
                userTypeTxt.setText("المتدرب");
            } else if (userType == 2) {
                userTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.admin_icon));
                userTypeTxt.setText("منسق التدريب");
            }
        }
        final Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nationalID = txt_email.getText().toString().trim();
                mobileNumber = txt_password.getText().toString().trim();
                if (mobileNumber.equalsIgnoreCase("") || nationalID.equalsIgnoreCase("")) {
                    awesomeValidation.validate();
                    return;
                }
                doLogin();
            }
        });
        txt_userType.setClickable(true);
        txt_userType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SignInActivity.this)
                        .title(R.string.txt_userType)
                        .items(R.array.user_types)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                txt_userType.setText(text.toString());
                                if (which == 0)
                                    userType = 0;
                                else if (which == 1)
                                    userType = 1;
                                else if (which == 2)
                                    userType = 2;
                            }
                        })
                        .show();
            }
        });
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txt_identity, " ", R.string.txt_validation);
        awesomeValidation.addValidation(this, R.id.txt_password, " ", R.string.txt_validation);
        awesomeValidation.addValidation(this, R.id.txt_user_type, " ", R.string.txt_validation);
        txt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Button btn_login = (Button) findViewById(R.id.btn_login);
                    btn_login.performClick();
                    handled = true;
                }
                return handled;
            }
        });
        setupUI(container);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.sign_in_activity;
    }

    private void doLogin() {
        if (userType == 0)
            loginTeacher();
        else if (userType == 1)
            loginStudent();
        else if (userType == 2)
            loginAdmin();
        else {
            new MaterialDialog.Builder(SignInActivity.this)
                    .title(getResources().getString(R.string.txt_error))
                    .content(getResources().getString(R.string.sign_in_failed_msg))
                    .positiveText(getResources().getString(R.string.txt_positive_btn))
                    .show();
        }

    }

    private void loginTeacher() {
        String signInURL =
                ApiEndPoints.TEACHER_SIGNIN_URL
                        + "?APPCode=" + CacheHelper.getInstance().appCode
                        + "&UserName=" + nationalID
                        + "&Password=" + mobileNumber
                        + "&UserTyp=" + userType;
        ApiHelper loginAPI = new ApiHelper(this, signInURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                JSONObject userObj = res.optJSONObject("con");
                if (userObj.optString("ID").equals("null")) {
                    new MaterialDialog.Builder(SignInActivity.this)
                            .title(getResources().getString(R.string.txt_error))
                            .content(getResources().getString(R.string.sign_in_failed_msg))
                            .positiveText(getResources().getString(R.string.txt_positive_btn))
                            .show();
                } else {
                    session.createLoginSession(
                            nationalID,
                            userObj.optString("ID"),
                            userObj.optString("IDREF"),
                            userObj.optString("FullName"),
                            userObj.optString("UserType_string"),
                            userObj.optInt("UserType"),
                            "",
                            "",
                            "",
                            userObj.optString("AllProgCount"),
                            userObj.optString("CertificateProgCount"));
                    openActivity(MainActivity.class);
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                new MaterialDialog.Builder(SignInActivity.this)
                        .title(getResources().getString(R.string.txt_error))
                        .content(getResources().getString(R.string.sign_in_failed_msg))
                        .positiveText(getResources().getString(R.string.txt_positive_btn))
                        .show();
            }
        });
        loginAPI.executeRequest(true, false);
    }

    private void loginStudent() {
        String signInURL =
                ApiEndPoints.STUDENT_SIGNIN_URL
                        + "?APPCode=" + CacheHelper.getInstance().appCode
                        + "&UserName=" + nationalID
                        + "&Password=" + mobileNumber
                        + "&UserTyp=" + userType;
        ApiHelper loginAPI = new ApiHelper(this, signInURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                JSONObject userObj = res.optJSONObject("con");
                if (userObj.optString("ID").equals("null")) {
                    new MaterialDialog.Builder(SignInActivity.this)
                            .title(getResources().getString(R.string.txt_error))
                            .content(getResources().getString(R.string.sign_in_failed_msg))
                            .positiveText(getResources().getString(R.string.txt_positive_btn))
                            .show();
                } else {
                    session.createLoginSession(
                            nationalID,
                            userObj.optString("ID"),
                            userObj.optString("IDREF"),
                            userObj.optString("FullName"),
                            userObj.optString("UserType_string"),
                            userObj.optInt("UserType"),
                            userObj.optString("Image"),
                            userObj.optString("school"),
                            userObj.optString("school_ID"),
                            userObj.optString("AllProgCount"),
                            userObj.optString("CertificateProgCount"));
                    openActivity(MainActivity.class);
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                new MaterialDialog.Builder(SignInActivity.this)
                        .title(getResources().getString(R.string.txt_error))
                        .content(getResources().getString(R.string.sign_in_failed_msg))
                        .positiveText(getResources().getString(R.string.txt_positive_btn))
                        .show();
            }
        });
        loginAPI.executeRequest(true, false);
    }

    private void loginAdmin() {
        String signInURL =
                ApiEndPoints.ADMIN_SIGNIN_URL
                        + "?APPCode=" + CacheHelper.getInstance().appCode
                        + "&UserName=" + nationalID
                        + "&Password=" + mobileNumber
                        + "&UserTyp=" + userType;
        ApiHelper loginAPI = new ApiHelper(this, signInURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                JSONObject userObj = res.optJSONObject("con");
                if (userObj.optString("ID").equals("null")) {
                    new MaterialDialog.Builder(SignInActivity.this)
                            .title(getResources().getString(R.string.txt_error))
                            .content(getResources().getString(R.string.sign_in_failed_msg))
                            .positiveText(getResources().getString(R.string.txt_positive_btn))
                            .show();
                } else {
                    session.createLoginSession(
                            nationalID,
                            userObj.optString("ID"),
                            userObj.optString("IDREF"),
                            userObj.optString("FullName"),
                            userObj.optString("UserType_string"),
                            userObj.optInt("UserType"),
                            userObj.optString("Image"),
                            userObj.optString("school"),
                            userObj.optString("school_ID"),
                            userObj.optString("AllProgCount"),
                            userObj.optString("CertificateProgCount"));
                    openActivity(MainActivity.class);
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                new MaterialDialog.Builder(SignInActivity.this)
                        .title(getResources().getString(R.string.txt_error))
                        .content(getResources().getString(R.string.sign_in_failed_msg))
                        .positiveText(getResources().getString(R.string.txt_positive_btn))
                        .show();
            }
        });
        loginAPI.executeRequest(true, false);
    }
}
