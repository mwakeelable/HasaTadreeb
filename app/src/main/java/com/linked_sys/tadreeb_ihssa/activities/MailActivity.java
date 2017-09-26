package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.adapters.MailAdapter;
import com.linked_sys.tadreeb_ihssa.adapters.MessagesAdapter;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Mail;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MailAdapter.MailAdapterListener {
    public static ArrayList<Mail> mailList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static MailAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int limit = 10;
    int skip = 0;
    boolean loadMore = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MailAdapter(this, mailList, this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loadMore) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            skip = skip + limit;
//                            loadMoreInbox();
                        }
                    }
                }
            }
        });
    }

    private void getInbox(){
        String url = ApiEndPoints.GET_INBOX
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserID=" + session.getUserDetails().get(session.KEY_ID_REF);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                mailList.clear();
                JSONObject obj = (JSONObject) response;
                try {
                    JSONArray mailArray = obj.optJSONArray("con");
                    if (mailArray.length() > 0) {
                        placeholder.setVisibility(View.GONE);
                        for (int i = 0; i < mailArray.length(); i++) {
                            JSONObject mailData = mailArray.optJSONObject(i);
                            Mail message = new Mail();
                            message.setBody(mailData.optString( "Id"));
                            message.setTitle(mailData.optString("Title"));
                            message.setBody(mailData.optString("Body"));
                            message.setIsRead(mailData.optString("Read"));
                            message.setUserType(mailData.optString("UserType"));
                            mailList.add(message);
                        }
                        recyclerView.setAdapter(mAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                        if (mailArray.length() < 10)
                            loadMore = false;
                        else
                            loadMore = true;
                    } else {
                        placeholder.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } catch (Exception e) {
                    Log.d(AppController.TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(MailActivity.this, "Unable to fetch json: " + error.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        api.executeRequest(true, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_messages;
    }

    @Override
    public void onRefresh() {
        limit = 10;
        skip = 0;
        getInbox();
    }

    @Override
    public void onIconClicked(int position) {
        limit = 10;
        skip = 0;
        openMsgBody(position, mAdapter.filteredList.get(position).getId());
    }

    @Override
    public void onMessageRowClicked(int position) {
        limit = 10;
        skip = 0;
        openMsgBody(position, mAdapter.filteredList.get(position).getId());
    }

    @Override
    public void onRowLongClicked(int position) {
        limit = 10;
        skip = 0;
        openMsgBody(position, mAdapter.filteredList.get(position).getId());
    }

    private void openMsgBody(int pos, String mailID) {
        for (int i = 0; i < mailList.size(); i++) {
            if (mailList.get(i).getId().equals(mailID)) {
                CacheHelper.getInstance().body = new Mail(
                        mailList.get(i).getId(),
                        mailList.get(i).getTitle(),
                        mailList.get(i).getBody(),
                        mailList.get(i).getIsRead(),
                        mailList.get(i).getUserType()
                );
            }
        }
        Intent intent = new Intent(this, MessageBodyActivity.class);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
