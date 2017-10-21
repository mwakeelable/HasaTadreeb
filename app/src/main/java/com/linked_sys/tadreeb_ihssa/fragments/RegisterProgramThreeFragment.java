package com.linked_sys.tadreeb_ihssa.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.RegisterProgramActivity;
import com.linked_sys.tadreeb_ihssa.adapters.RegisterProgramAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.ProgramByPeriod;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterProgramThreeFragment extends Fragment implements RegisterProgramAdapter.RegisterProgramAdapterListener {
    RegisterProgramActivity activity;
    private RecyclerView recyclerView;
    public RegisterProgramAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    int limit = 10;
    int skip = 1;
    boolean loadMore = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_three_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new RegisterProgramAdapter(activity, CacheHelper.getInstance().programByPeriods, this);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        getProgramsByPeriod(activity.periodRef);
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
                            loadMorePrograms(activity.periodRef);
                        }
                    }
                }
            }
        });
    }

    private void getProgramsByPeriod(String periodRef) {
        String url = ApiEndPoints.GET_PROGRAM_BY_PERIODS +
                "?APPCode=" + CacheHelper.getInstance().appCode +
                "&PeriodREF=" + periodRef
                + "&PageSize=" + limit
                + "&PageNumber=" + skip;
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    CacheHelper.getInstance().programByPeriods.clear();
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArr = res.optJSONArray("con");
                    if (programsArr.length() > 0) {
                        for (int i = 0; i < programsArr.length(); i++) {
                            JSONObject programObj = programsArr.getJSONObject(i);
                            ProgramByPeriod program = new ProgramByPeriod();
                            program.setREF(programObj.optString("ProgramREF"));
                            program.setProgramID(programObj.optString("ProgramID"));
                            program.setProgramName(programObj.optString("ProgramName"));
                            program.setProgramDays(programObj.optString("ProgramDays"));
                            program.setProgramTimes(programObj.optString("ProgramTimes"));
                            program.setProgramDateStrat(programObj.optString("ProgramDateStrat"));
                            program.setProgramDateEnd(programObj.optString("ProgramDateEnd"));
                            program.setProgramTimeStrat(programObj.optString("ProgramTimeStrat"));
                            program.setProgramLocation(programObj.optString("ProgramLocation"));
                            program.setTeacherName(programObj.optString("TeacherName"));
                            program.setProgramClass(programObj.optString("ProgramClass"));
                            program.setProgramStatus(programObj.optString("ProgramStatus"));
                            CacheHelper.getInstance().programByPeriods.add(program);
                        }
                        recyclerView.setAdapter(mAdapter);
                        if (programsArr.length() < 10)
                            loadMore = false;
                        else
                            loadMore = true;
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void loadMorePrograms(String periodRef) {
        String url = ApiEndPoints.GET_PROGRAM_BY_PERIODS +
                "?APPCode=" + CacheHelper.getInstance().appCode +
                "&PeriodREF=" + periodRef +
                "&PageSize=" + limit +
                "&PageNumber=" + skip;
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArr = res.optJSONArray("con");
                    for (int i = 0; i < programsArr.length(); i++) {
                        JSONObject programObj = programsArr.getJSONObject(i);
                        ProgramByPeriod program = new ProgramByPeriod();
                        program.setREF(programObj.optString("ProgramREF"));
                        program.setProgramID(programObj.optString("ProgramID"));
                        program.setProgramName(programObj.optString("ProgramName"));
                        program.setProgramDays(programObj.optString("ProgramDays"));
                        program.setProgramTimes(programObj.optString("ProgramTimes"));
                        program.setProgramDateStrat(programObj.optString("ProgramDateStrat"));
                        program.setProgramDateEnd(programObj.optString("ProgramDateEnd"));
                        program.setProgramTimeStrat(programObj.optString("ProgramTimeStrat"));
                        program.setProgramLocation(programObj.optString("ProgramLocation"));
                        program.setTeacherName(programObj.optString("TeacherName"));
                        program.setProgramClass(programObj.optString("ProgramClass"));
                        program.setProgramStatus(programObj.optString("ProgramStatus"));
                        CacheHelper.getInstance().programByPeriods.add(program);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (programsArr.length() < 10)
                        loadMore = false;
                    else
                        loadMore = true;
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    public void onProgramRowClicked(int position) {
        activity.drawProgramDetails(position);
    }
}

