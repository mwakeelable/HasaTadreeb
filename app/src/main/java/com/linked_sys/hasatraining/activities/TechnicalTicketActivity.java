package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import java.util.HashMap;
import java.util.Map;

public class TechnicalTicketActivity extends BaseActivity {
    TextView txtSchoolName;
    EditText txtMessageBody;
    CardView btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtSchoolName = (TextView) findViewById(R.id.txtSchoolName);
        txtMessageBody = (EditText) findViewById(R.id.txtMessageBody);
        txtSchoolName.setText(session.getUserDetails().get(session.KEY_FULL_NAME));
        btnSendMessage = (CardView) findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_technical_ticket;
    }

    private void sendMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("SchoolId", session.getUserDetails().get(session.KEY_ID));
        map.put("Message", txtMessageBody.getText().toString());
        ApiHelper apiHelper = new ApiHelper(this, ApiEndPoints.SEND_TECH_TICKET, Request.Method.POST, map, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                new MaterialDialog.Builder(TechnicalTicketActivity.this)
                        .title("تذكرة الدعم الفني")
                        .content("تم ارسال الرسالة بشكل صحيح")
                        .positiveText("تم")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                TechnicalTicketActivity.this.finish();
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
}
