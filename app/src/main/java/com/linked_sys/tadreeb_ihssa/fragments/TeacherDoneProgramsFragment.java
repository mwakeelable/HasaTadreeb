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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.MainActivity;
import com.linked_sys.tadreeb_ihssa.activities.TeacherProgramDetailsActivity;
import com.linked_sys.tadreeb_ihssa.adapters.TeachersDoneProgramsAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.TeacherProgram;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class TeacherDoneProgramsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TeachersDoneProgramsAdapter.TeachersDoneProgramsAdapterListener {
    MainActivity activity;
    private RecyclerView recyclerView;
    public TeachersDoneProgramsAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.teacher_done_programs_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        placeholder = (LinearLayout) view.findViewById(R.id.no_data_placeholder);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new TeachersDoneProgramsAdapter(activity, CacheHelper.getInstance().teacherDonePrograms, this);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
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
                if (dy > 0) //check for scroll down
                {

                }
            }
        });
    }

    private void getTeacherPrograms() {
        final String programsURL = ApiEndPoints.TEACHER_PROGRAMS_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + activity.session.getUserDetails().get(activity.session.KEY_ID)
                +"&progstatus=1" ;
        ApiHelper api = new ApiHelper(activity, programsURL, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                CacheHelper.getInstance().teacherDonePrograms.clear();
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
                            CacheHelper.getInstance().teacherDonePrograms.add(program);
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
    public void onRefresh() {
        getTeacherPrograms();
    }

    @Override
    public void onProgramRowClicked(int position) {
        openProgram(position);
    }

    private void openProgram(int pos) {
        Intent intent = new Intent(activity, TeacherProgramDetailsActivity.class);
        intent.putExtra("pos",pos);
        intent.putExtra("comeFrom", "done");
        activity.startActivityForResult(intent, activity.REQUEST_TEACHER_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getTeacherPrograms();
    }
}
