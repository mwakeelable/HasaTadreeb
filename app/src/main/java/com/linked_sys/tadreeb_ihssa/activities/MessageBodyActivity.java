package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
    int pos;


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
        titleTxt.setText("تفاصيل الرسـالة");
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
        msg_subject = (TextView) findViewById(R.id.messageTitleTxt);
        msg_content = (TextView) findViewById(R.id.messageBodyTxt);
        txt_date = (TextView) findViewById(R.id.messagesDateTxt);
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
        getMessageBody();
    }

}
