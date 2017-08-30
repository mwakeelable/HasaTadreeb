package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jrummyapps.android.util.HtmlBuilder;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;

public class MessageDetailsActivity extends BaseActivity {
    TextView txtMessageBody, txtReplyBody, txtMessageDate, txtMessageTime, txtReplyDate, txtReplyTime;
    int position;
    LinearLayout replyContainer, noReplyPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtMessageBody = (TextView) findViewById(R.id.msg_content);
        txtReplyBody = (TextView) findViewById(R.id.reply_content);
        txtMessageDate = (TextView) findViewById(R.id.txtMessageDate);
        txtMessageTime = (TextView) findViewById(R.id.txtMessageTime);
        txtReplyDate = (TextView) findViewById(R.id.txtReplyDate);
        txtReplyTime = (TextView) findViewById(R.id.txtReplyTime);
        replyContainer = (LinearLayout) findViewById(R.id.replyContainer);
        noReplyPlaceholder = (LinearLayout) findViewById(R.id.noReplyPlaceholder);


        Bundle extra = getIntent().getExtras();
        if (extra != null)
            position = extra.getInt("pos");
        txtMessageBody.setMovementMethod(LinkMovementMethod.getInstance());
        txtReplyBody.setMovementMethod(LinkMovementMethod.getInstance());
        txtMessageBody.setText(getMessageBody());
        txtMessageDate.setText(CacheHelper.getInstance().messages.get(position).getMessageDate());
        txtMessageTime.setText(CacheHelper.getInstance().messages.get(position).getMessageTime());

        if (CacheHelper.getInstance().messages.get(position).getReplay().equals("")){
            noReplyPlaceholder.setVisibility(View.VISIBLE);
            replyContainer.setVisibility(View.GONE);
        }else{
            noReplyPlaceholder.setVisibility(View.GONE);
            replyContainer.setVisibility(View.VISIBLE);
            txtReplyBody.setText(getReplyBody());
            txtReplyDate.setText(CacheHelper.getInstance().messages.get(position).getReplayDate());
            txtReplyTime.setText(CacheHelper.getInstance().messages.get(position).getReplayTime());
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.message_details_activity;
    }

    private Spanned getMessageBody() {
        HtmlBuilder html = new HtmlBuilder();
        html.p(CacheHelper.getInstance().messages.get(position).getMessage());
        return html.build();
    }

    private Spanned getReplyBody() {
        HtmlBuilder html = new HtmlBuilder();
        html.p(CacheHelper.getInstance().messages.get(position).getReplay());
        return html.build();
    }
}