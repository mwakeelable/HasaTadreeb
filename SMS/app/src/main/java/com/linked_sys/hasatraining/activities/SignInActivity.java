package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.AppController;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;
import com.linked_sys.hasatraining.utils.SpinnerDialog;

import org.json.JSONObject;

public class SignInActivity extends BaseActivity {
    EditText txt_email, txt_password;
    SpinnerDialog mProgress;
    AwesomeValidation awesomeValidation;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mProgress = new SpinnerDialog(this);
        txt_email = (EditText) findViewById(R.id.txt_identity);
        txt_password = (EditText) findViewById(R.id.txt_password);
        final Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txt_email.getText().toString().trim();
                password = txt_password.getText().toString().trim();
                if (password.equalsIgnoreCase("") || username.equalsIgnoreCase("")) {
                    awesomeValidation.validate();
                    return;
                }
                doLogin();
            }
        });
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txt_identity, " ", R.string.txt_validation);
        awesomeValidation.addValidation(this, R.id.txt_password, " ", R.string.txt_validation);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        finishAffinity();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.sign_in_activity;
    }

    private void doLogin() {
        String signInURL =
                ApiEndPoints.SIGNIN_URL
                        + "?APP_CODE=" + CacheHelper.getInstance().appCode
                        + "&username=" + username
                        + "&password=" + password;
        ApiHelper loginAPI = new ApiHelper(this, signInURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                CacheHelper.getInstance().ACode = res.optString("ACODE");
                session.createLoginSession(
                        username,
                        password,
                        CacheHelper.getInstance().ACode,
                        res.optString("REF"),
                        res.optString("FULLNAME"),
                        res.optString("USERTYPE"));
                openActivity(MainActivity.class);
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
