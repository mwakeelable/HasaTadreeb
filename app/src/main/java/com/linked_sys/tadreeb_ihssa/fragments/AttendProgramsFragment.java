package com.linked_sys.tadreeb_ihssa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.StudentCoursesActivity;
import com.linked_sys.tadreeb_ihssa.activities.ProgramDetailsActivity;
import com.linked_sys.tadreeb_ihssa.adapters.AllProgramsAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Program;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AttendProgramsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AllProgramsAdapter.AllProgramsAdapterListener, SearchView.OnQueryTextListener {
    StudentCoursesActivity activity;
    public ArrayList<Program> programs = new ArrayList<>();
    private RecyclerView recyclerView;
    public AllProgramsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int limit = 10;
    int skip = 0;
    boolean loadMore = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (StudentCoursesActivity) getActivity();
        return inflater.inflate(R.layout.attend_programs_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        placeholder = (LinearLayout) view.findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AllProgramsAdapter(activity, programs, this);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
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
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//                    if (loadMore) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            Log.v("...", "Last Item Wow !");
//                            //Do pagination.. i.e. fetch new data
//                            skip = skip + limit;
//                            loadMorePrograms();
//                        }
//                    }
                }
            }
        });
    }

    private void getPrograms() {
        final String programsURL = ApiEndPoints.STUDENT_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId="+activity.session.getUserDetails().get(activity.session.KEY_ID)
                + "&ProgStatus=3";
        ApiHelper programsAPI = new ApiHelper(activity, programsURL, Request.Method.GET, new ApiCallback() {
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
        placeholder.setVisibility(View.VISIBLE);
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
        return false;
    }

    @Override
    public void onProgramRowClicked(int position) {
        openProgram(mAdapter.filteredList.get(position).getRegREF());
    }

    private void openProgram(String regRef) {
        Intent intent = new Intent(activity, ProgramDetailsActivity.class);
        intent.putExtra("REGREF",regRef);
        intent.putExtra("comeFromRate",true);
//        startActivity(intent);
        activity.startActivityForResult(intent, activity.REQUEST_RATE_CODE);
    }
}
