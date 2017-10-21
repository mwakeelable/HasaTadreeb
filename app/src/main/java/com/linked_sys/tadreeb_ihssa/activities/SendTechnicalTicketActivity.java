package com.linked_sys.tadreeb_ihssa.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import java.util.HashMap;
import java.util.Map;

public class SendTechnicalTicketActivity extends BaseActivity {
    TextView txtSchoolName;
    EditText txtMessageBody;
    LinearLayout btnSendMessage, container;

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
        titleTxt.setText("تذكرة الدعم الفني");
        txtSchoolName = (TextView) findViewById(R.id.txtSchoolName);
        txtMessageBody = (EditText) findViewById(R.id.txtMessageBody);
        txtSchoolName.setText(session.getUserDetails().get(session.KEY_FULL_NAME));
        btnSendMessage = (LinearLayout) findViewById(R.id.btnSendMessage);
        container = (LinearLayout) findViewById(R.id.ticketContainer);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        setupUI(container);
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
                new MaterialDialog.Builder(SendTechnicalTicketActivity.this)
                        .title("تذكرة الدعم الفني")
                        .content("تم ارسال الرسالة بشكل صحيح")
                        .positiveText("تم")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SendTechnicalTicketActivity.this.finish();
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
