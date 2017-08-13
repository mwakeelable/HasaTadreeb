package com.linked_sys.hasatraining.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.adapters.MessagesAdapter;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.models.Message;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class TechnicalTicketsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessagesAdapterListener {
    private RecyclerView recyclerView;
    public MessagesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(SendTechnicalTicketActivity.class);
            }
        });
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MessagesAdapter(this, CacheHelper.getInstance().messages, this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getMessages();
                    }
                }
        );
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_technical_tickets;
    }

    private void getMessages() {
        String url = ApiEndPoints.GET_USER_MESSAGES
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&SchoolId=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                CacheHelper.getInstance().messages.clear();
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray messageArray = res.optJSONArray("con");
                    if (messageArray.length() > 0) {
                        for (int i = 0; i < messageArray.length(); i++) {
                            JSONObject messageObj = messageArray.optJSONObject(i);
                            Message message = new Message();
                            message.setMessageDate(messageObj.optString("MessageDate"));
                            message.setMessage(messageObj.optString("Message"));
                            message.setMessageREF(messageObj.optString("MessageREF"));
                            message.setSchoolId(messageObj.optString("SchoolId"));
                            message.setMessageTime(messageObj.optString("MessageTime"));
                            message.setReplay(messageObj.optString("Replay"));
                            message.setReplayDate(messageObj.optString("ReplayDate"));
                            message.setReplayTime(messageObj.optString("ReplayTime"));
                            CacheHelper.getInstance().messages.add(message);
                        }
                        recyclerView.setAdapter(mAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        placeholder.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    public void onRefresh() {
        getMessages();
    }

    @Override
    public void onMessageRowClick(int position) {
        Intent intent = new Intent(this, MessageDetailsActivity.class);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessages();
    }
}
