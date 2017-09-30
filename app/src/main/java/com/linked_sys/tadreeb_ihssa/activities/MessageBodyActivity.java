package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONObject;

public class MessageBodyActivity extends BaseActivity {
    public TextView msg_subject, msg_content, txt_date;
    Toolbar mToolbar;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_body);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controlsDef();
        applyMessageData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_message_body;
    }

    private void controlsDef() {
        Intent in = getIntent();
        Bundle extra = in.getExtras();
        if (extra != null) {
            pos = extra.getInt("pos");
        }
        msg_subject = (TextView) findViewById(R.id.txt_mail_subject);
        msg_content = (TextView) findViewById(R.id.msg_content);
        txt_date = (TextView) findViewById(R.id.txt_date);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void getMessageBody() {
        String url = ApiEndPoints.GET_MESSAGE
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&MessageId=" + CacheHelper.getInstance().body.getId()
                + "&UserID=" + session.getUserDetails().get(session.KEY_ID_REF);

        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject obj = (JSONObject) response;
                JSONObject message = obj.optJSONObject("con");
                msg_subject.setText(message.optString("Title"));
                msg_content.setText(message.optString("Body"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void applyMessageData() {
//        msg_subject.setText(CacheHelper.getInstance().body.getTitle());
//        msg_content.setText(CacheHelper.getInstance().body.getBody());
        getMessageBody();
    }

}
