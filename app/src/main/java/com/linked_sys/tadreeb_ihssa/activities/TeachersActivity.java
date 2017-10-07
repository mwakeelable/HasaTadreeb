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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.adapters.TeachersAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Teachers;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class TeachersActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        TeachersAdapter.TeachersAdapterListener,
        SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    public TeachersAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        mAdapter = new TeachersAdapter(this, CacheHelper.getInstance().teachersList, this);
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
                        getTeachersData();
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
        return R.layout.teachers_activity;
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
                TeachersActivity.this.finish();
                hideSoftKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getTeachersData() {
        String url = ApiEndPoints.TEACHERS_DATA
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);

        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                CacheHelper.getInstance().teachersList.clear();
                JSONObject res = (JSONObject) response;
                JSONArray teachersArray = res.optJSONArray("con");
                if (teachersArray.length() > 0) {
                    for (int i = 0; i < teachersArray.length(); i++) {
                        JSONObject teacherObj = teachersArray.optJSONObject(i);
                        Teachers teacher = new Teachers();
                        teacher.setName(teacherObj.optString("Name"));
                        teacher.setMobile(teacherObj.optString("Mobile"));
                        teacher.setId(teacherObj.optString("Id"));
                        teacher.setSpecialize(teacherObj.optString("Specialize"));
                        teacher.setCurrentWork(teacherObj.optString("CurrentWork"));
                        teacher.setCase(teacherObj.optString("Case"));
                        teacher.setIDREF(teacherObj.optString("IDREF"));
                        CacheHelper.getInstance().teachersList.add(teacher);
                    }
                    recyclerView.setAdapter(mAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    placeholder.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
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
        getTeachersData();
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
    public void onTeacherRowClicked(int position) {
        Intent intent = new Intent(this, TeacherDetailsActivity.class);
        intent.putExtra("name", mAdapter.filteredList.get(position).getName());
        intent.putExtra("mobile", mAdapter.filteredList.get(position).getMobile());
        intent.putExtra("ID", mAdapter.filteredList.get(position).getId());
        intent.putExtra("specialize", mAdapter.filteredList.get(position).getSpecialize());
        intent.putExtra("work", mAdapter.filteredList.get(position).getCurrentWork());
        intent.putExtra("case", mAdapter.filteredList.get(position).getCase());
        intent.putExtra("idRef", mAdapter.filteredList.get(position).getIDREF());
        startActivity(intent);
    }
}
