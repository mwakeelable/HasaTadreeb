package com.linked_sys.tadreeb_ihssa.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
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
import com.linked_sys.tadreeb_ihssa.adapters.AllProgramsAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Program;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentCertificatesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AllProgramsAdapter.AllProgramsAdapterListener, SearchView.OnQueryTextListener {
    public ArrayList<Program> programs = new ArrayList<>();
    private RecyclerView recyclerView;
    public AllProgramsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int limit = 10;
    int skip = 1;
    boolean loadMore = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View shadow = findViewById(R.id.toolbar_shadow);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shadow.setVisibility(View.VISIBLE);
        else
            shadow.setVisibility(View.GONE);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_my_certificates));
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AllProgramsAdapter(this, programs, this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
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
                        getPrograms();
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
                            loadMorePrograms();
                        }
                    }
                }
            }
        });
    }

    private void getPrograms() {
        final String programsURL = ApiEndPoints.STUDENT_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_ID)
                + "&ProgStatus=2"
                + "&PageSize=" + limit
                + "&PageNumber=" + skip;
        ApiHelper programsAPI = new ApiHelper(this, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                programs.clear();
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    if (programsArray.length() > 0) {
                        for (int i = 0; i < programsArray.length(); i++) {
                            JSONObject programObj = programsArray.optJSONObject(i);
                            Program program = new Program();
                            program.setRegREF(programObj.optString("RegREF"));
                            program.setProgramREF(programObj.optString("ProgramREF"));
                            program.setProgramID(programObj.optString("ProgramID"));
                            program.setProgramName(programObj.optString("ProgramName"));
                            program.setProgramDate(programObj.optString("ProgramDate"));
                            program.setProgramTime(programObj.optString("ProgramTime"));
                            program.setProgramLocation(programObj.optString("ProgramLocation"));
                            program.setProgramStatus(programObj.optString("ProgramStatus"));
                            program.setMustRate(programObj.optBoolean("MustRate"));
                            program.setCanPrintCertificate(programObj.optBoolean("CanPrintCertificate"));
                            programs.add(program);
                        }
                        recyclerView.setAdapter(mAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                        if (programsArray.length() < 10)
                            loadMore = false;
                        else
                            loadMore = true;
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
        programsAPI.executeRequest(true, false);
    }

    private void loadMorePrograms() {
        final String programsURL = ApiEndPoints.STUDENT_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_ID)
                + "&ProgStatus=2"
                + "&PageSize=" + limit
                + "&PageNumber=" + skip;
        ApiHelper programsAPI = new ApiHelper(this, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    for (int i = 0; i < programsArray.length(); i++) {
                        JSONObject programObj = programsArray.optJSONObject(i);
                        Program program = new Program();
                        program.setRegREF(programObj.optString("RegREF"));
                        program.setProgramREF(programObj.optString("ProgramREF"));
                        program.setProgramID(programObj.optString("ProgramID"));
                        program.setProgramName(programObj.optString("ProgramName"));
                        program.setProgramDate(programObj.optString("ProgramDate"));
                        program.setProgramTime(programObj.optString("ProgramTime"));
                        program.setProgramLocation(programObj.optString("ProgramLocation"));
                        program.setProgramStatus(programObj.optString("ProgramStatus"));
                        program.setMustRate(programObj.optBoolean("MustRate"));
                        program.setCanPrintCertificate(programObj.optBoolean("CanPrintCertificate"));
                        programs.add(program);
                    }
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    if (programsArray.length() < 10)
                        loadMore = false;
                    else
                        loadMore = true;
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d("Error", "Failed");
            }
        });
        programsAPI.executeRequest(true, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_certificates_activity;
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
                StudentCertificatesActivity.this.finish();
                hideSoftKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    public void onRefresh() {
        limit = 10;
        skip = 0;
        getPrograms();
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
    public void onProgramRowClicked(int position) {
        openProgram(mAdapter.filteredList.get(position).getRegREF());
    }

    private void openProgram(String regRef) {
        Intent intent = new Intent(StudentCertificatesActivity.this, ProgramDetailsActivity.class);
        intent.putExtra("REGREF", regRef);
        intent.putExtra("PRINT", true);
        intent.putExtra("RATE", false);
        startActivity(intent);
    }
}
