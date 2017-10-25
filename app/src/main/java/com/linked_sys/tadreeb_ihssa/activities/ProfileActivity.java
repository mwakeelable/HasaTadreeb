package com.linked_sys.tadreeb_ihssa.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONObject;

public class ProfileActivity extends BaseActivity {
    ImageView backBtn, userImg;
    TextView titleTxt, userNameTxt, messagesCountTxt;
    RelativeLayout messagesBtn, signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backBtn = (ImageView) findViewById(R.id.backImgView);
        userImg = (ImageView) findViewById(R.id.userImg);
        userNameTxt = (TextView) findViewById(R.id.userNameTxt);
        titleTxt = (TextView) findViewById(R.id.titleTxt);
        messagesCountTxt = (TextView) findViewById(R.id.messagesCountTxt);
        messagesBtn = (RelativeLayout) findViewById(R.id.messagesBtn);
        signOutBtn = (RelativeLayout) findViewById(R.id.signOutBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titleTxt.setText("الملف الشخصي");
        userNameTxt.setText(session.getUserDetails().get(session.KEY_FULL_NAME));
        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MailActivity.class);
            }
        });
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(ProfileActivity.this)
                        .title(getResources().getString(R.string.action_sign_out))
                        .content(getResources().getString(R.string.txt_confirmation))
                        .positiveText(getResources().getString(R.string.txt_positive_btn))
                        .negativeText(getResources().getString(R.string.txt_negative_btn))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                removeFBToken();
                            }
                        })
                        .show();
            }
        });
        getMessagesCount();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

    private void getMessagesCount() {
        String url = ApiEndPoints.GET_MESSAGE_COUNT
                + "?APPCode=zunIhQwuD38JfFkSQBCk8gzvK5aJQaoahacqSJLhRcg="
                + "&UserID=" + session.getUserDetails().get(session.KEY_ID_REF);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                messagesCountTxt.setText(res.optString("ret"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessagesCount();
        getUnreadMessagesCount();
    }
}
