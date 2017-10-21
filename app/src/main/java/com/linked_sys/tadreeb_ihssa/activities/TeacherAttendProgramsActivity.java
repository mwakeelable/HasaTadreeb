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
import com.linked_sys.tadreeb_ihssa.adapters.TeacherAttendProgramsAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.TeacherProgram;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class TeacherAttendProgramsActivity extends BaseActivity implements SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener, TeacherAttendProgramsAdapter.TeacherProgramsAdapterListener {
    private RecyclerView recyclerView;
    public TeacherAttendProgramsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;
    public static final int REQUEST_TEACHER_CODE = 0;
    int limit = 10;
    int skip = 1;
    boolean loadMore = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

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
        mAdapter = new TeacherAttendProgramsAdapter(this, CacheHelper.getInstance().teacherAttendPrograms, this);
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
                        getTeacherPrograms();
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
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (loadMore) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            skip = skip + 1;
                            loadMoreTeachersPrograms();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_teacher_attend_programs;
    }


    private void getTeacherPrograms() {
        final String programsURL = ApiEndPoints.TEACHER_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_ID)
                + "&progstatus=0" +
                "&PageSize=" + limit +
                "&PageNumber=" + skip;
        ApiHelper api = new ApiHelper(this, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                CacheHelper.getInstance().teacherAttendPrograms.clear();
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    if (programsArray.length() > 0) {
                        for (int i = 0; i < programsArray.length(); i++) {
                            JSONObject programObj = programsArray.optJSONObject(i);
                            TeacherProgram program = new TeacherProgram();
                            program.setREF(programObj.optString("REF"));
                            program.setProgramID(programObj.optString("ProgramID"));
                            program.setProgramName(programObj.optString("ProgramName"));
                            program.setProgramDays(programObj.optString("ProgramDays"));
                            program.setProgramTimes(programObj.optString("ProgramTimes"));
                            program.setProgramDateStrat(programObj.optString("ProgramDateStrat"));
                            program.setProgramDateEnd(programObj.optString("ProgramDateEnd"));
                            program.setProgramTimeStrat(programObj.optString("ProgramTimeStrat"));
                            program.setProgramLocation(programObj.optString("ProgramLocation"));
                            program.setRemainDays(programObj.optString("RemainDays"));
                            program.setAttendPeriodID(programObj.optString("AttendPeriodID"));
                            program.setAttendPeriodName(programObj.optString("AttendPeriodName"));
                            program.setCaseDays(programObj.optBoolean("CaseDays"));
                            program.setMustAttend(programObj.optBoolean("MustAttend"));
                            program.setCanPrintCertificate(programObj.optBoolean("CanPrintCertificate"));
                            CacheHelper.getInstance().teacherAttendPrograms.add(program);
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
        api.executeRequest(true, false);
    }


    private void loadMoreTeachersPrograms() {
        final String programsURL = ApiEndPoints.TEACHER_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_ID)
                + "&progstatus=0" +
                "&PageSize=" + limit +
                "&PageNumber=" + skip;
        ApiHelper api = new ApiHelper(this, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    for (int i = 0; i < programsArray.length(); i++) {
                        JSONObject programObj = programsArray.optJSONObject(i);
                        TeacherProgram program = new TeacherProgram();
                        program.setREF(programObj.optString("REF"));
                        program.setProgramID(programObj.optString("ProgramID"));
                        program.setProgramName(programObj.optString("ProgramName"));
                        program.setProgramDays(programObj.optString("ProgramDays"));
                        program.setProgramTimes(programObj.optString("ProgramTimes"));
                        program.setProgramDateStrat(programObj.optString("ProgramDateStrat"));
                        program.setProgramDateEnd(programObj.optString("ProgramDateEnd"));
                        program.setProgramTimeStrat(programObj.optString("ProgramTimeStrat"));
                        program.setProgramLocation(programObj.optString("ProgramLocation"));
                        program.setRemainDays(programObj.optString("RemainDays"));
                        program.setAttendPeriodID(programObj.optString("AttendPeriodID"));
                        program.setAttendPeriodName(programObj.optString("AttendPeriodName"));
                        program.setCaseDays(programObj.optBoolean("CaseDays"));
                        program.setMustAttend(programObj.optBoolean("MustAttend"));
                        program.setCanPrintCertificate(programObj.optBoolean("CanPrintCertificate"));
                        CacheHelper.getInstance().teacherAttendPrograms.add(program);
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
        api.executeRequest(true, false);
    }

    @Override
    public void onRefresh() {
        limit = 10;
        skip = 1;
        getTeacherPrograms();
    }

    @Override
    public void onProgramRowClicked(int position) {
        Intent intent = new Intent(TeacherAttendProgramsActivity.this, TeacherProgramDetailsActivity.class);
        intent.putExtra("pos", position);
        intent.putExtra("comeFrom", "attend");
        startActivityForResult(intent, REQUEST_TEACHER_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTeacherPrograms();
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
                TeacherAttendProgramsActivity.this.finish();
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
}
