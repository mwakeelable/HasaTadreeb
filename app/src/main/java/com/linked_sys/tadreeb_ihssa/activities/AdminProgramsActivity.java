package com.linked_sys.tadreeb_ihssa.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.adapters.AcceptedProgramsAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Program;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdminProgramsActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        AcceptedProgramsAdapter.AcceptedProgramsAdapterListener,
        SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    public AcceptedProgramsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;
    String status;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AcceptedProgramsAdapter(this, CacheHelper.getInstance().adminPrograms, this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = getIntent().getExtras();
                        if (bundle != null) {
                            status = bundle.getString("status");
                            if (status.equals("1"))
                                toolbar.setTitle("الطلبات المعتمدة");
                            else if (status.equals("2"))
                                toolbar.setTitle("الطلبات الغير معتمدة");
                            getPrograms();
                        }
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

                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.admin_programs_activity;
    }

    private void getPrograms() {
        final String programsURL = ApiEndPoints.ADMIN_PROGRAMS_WITH_STATUS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_ID)
                + "&PeriodId=" + CacheHelper.getInstance().adminPeriodSelectedID
                + "&progstatus=" + status;
        ApiHelper api = new ApiHelper(this, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                CacheHelper.getInstance().adminPrograms.clear();
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    if (programsArray.length() > 0) {
                        for (int i = 0; i < programsArray.length(); i++) {
                            JSONObject programObj = programsArray.optJSONObject(i);
                            Program program = new Program();
                            program.setMotadarebFullName(programObj.optString("MotadarebFullName"));
                            program.setMotadarebMobile(programObj.optString("MotadarebMobile"));
                            program.setMotadarebID(programObj.optString("MotadarebID"));
                            program.setProgramName(programObj.optString("ProgramName"));
                            program.setProgramID(programObj.optString("ProgramREF"));
                            program.setProgramTime(programObj.optString("ProgramTime"));
                            program.setProgramDate(programObj.optString("ProgramDate"));
                            program.setRegistrationDate(programObj.optString("RegistrationDate"));
                            program.setProgramStatus(programObj.optString("ProgramStatus"));
                            program.setRegREF(programObj.optString("RegREF"));
                            CacheHelper.getInstance().adminPrograms.add(program);
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
                Log.d("Error", "Failed");
            }
        });
        api.executeRequest(true, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AdminProgramsActivity.this.finish();
                hideSoftKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onRefresh() {
        getPrograms();
    }

    @Override
    public void onProgramRowClicked(int position) {
        openProgram(position);
    }

    private void openProgram(int pos) {
        Intent intent = new Intent(this, AdminProgramDetailsActivity.class);
        intent.putExtra("pos", pos);
        intent.putExtra("status",status);
        startActivity(intent);
    }
}
