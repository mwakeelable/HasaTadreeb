package com.linked_sys.tadreeb_ihssa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.AdminPendingProgramsActivity;
import com.linked_sys.tadreeb_ihssa.activities.AdminProgramsActivity;
import com.linked_sys.tadreeb_ihssa.activities.MainActivity;
import com.linked_sys.tadreeb_ihssa.activities.SearchActivity;
import com.linked_sys.tadreeb_ihssa.activities.TeachersActivity;
import com.linked_sys.tadreeb_ihssa.activities.TechnicalTicketsActivity;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Periods;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainAdminFragment extends Fragment {
    MainActivity activity;
    Spinner periodsSpinner;
    ArrayAdapter<Periods> periodAdapter;
    String period;
    TextView pendingProgramsCountTxt;
    LinearLayout teachersDataBtn, acceptedProgramsBtn, notAcceptedProgramsBtn, techTicketBtn, btnSearch;
    RelativeLayout pendingProgramsBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main_admin_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        periodsSpinner = (Spinner) view.findViewById(R.id.periodsSpinner);
        pendingProgramsCountTxt = (TextView) view.findViewById(R.id.pendingProgramsCountTxt);
        teachersDataBtn = (LinearLayout) view.findViewById(R.id.btnTeachersData);
        techTicketBtn = (LinearLayout) view.findViewById(R.id.btnTicketSupport);
        acceptedProgramsBtn = (LinearLayout) view.findViewById(R.id.btnAcceptedPrograms);
        notAcceptedProgramsBtn = (LinearLayout) view.findViewById(R.id.btnNotAcceptedPrograms);
        btnSearch = (LinearLayout) view.findViewById(R.id.btnSearch);
        pendingProgramsBtn = (RelativeLayout) view.findViewById(R.id.pendingProgramsBtn);
        pendingProgramsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(AdminPendingProgramsActivity.class);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent acceptedIntent = new Intent(activity, SearchActivity.class);
                acceptedIntent.putExtra("period", period);
                startActivity(acceptedIntent);
            }
        });
        periodsSpinner = (Spinner) view.findViewById(R.id.periodsSpinner);
        ImageView imgSpinner = (ImageView) view.findViewById(R.id.imgSpinner);
        imgSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodsSpinner.performClick();
            }
        });

        acceptedProgramsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acceptedIntent = new Intent(activity, AdminProgramsActivity.class);
                acceptedIntent.putExtra("status", "1");
                startActivity(acceptedIntent);
            }
        });
        notAcceptedProgramsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acceptedIntent = new Intent(activity, AdminProgramsActivity.class);
                acceptedIntent.putExtra("status", "2");
                startActivity(acceptedIntent);
            }
        });
        teachersDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TeachersActivity.class);
            }
        });
        techTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TechnicalTicketsActivity.class);
            }
        });
        imgSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodsSpinner.performClick();
            }
        });
        getPeriods();
        periodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Periods periods = (Periods) parent.getSelectedItem();
                getPendingProgramsCount(periods.getPeriodREF());
                CacheHelper.getInstance().adminPeriodSelectedID = periods.getPeriodREF();
                period = periods.getPeriodName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setPeriodsData() {
        periodAdapter = new ArrayAdapter<>(
                activity,
                android.R.layout.simple_spinner_dropdown_item,
                CacheHelper.getInstance().periodsList);
        periodsSpinner.setAdapter(periodAdapter);
    }

    private void getPeriods() {
        CacheHelper.getInstance().periodsList.clear();
        String url = ApiEndPoints.GET_PROGRAM_PERIODS
                + "?APPCode=" + CacheHelper.getInstance().appCode;
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    CacheHelper.getInstance().periodsList.clear();
                    JSONObject res = (JSONObject) response;
                    JSONArray periodArr = res.optJSONArray("con");
                    for (int i = 0; i < periodArr.length(); i++) {
                        JSONObject periodObj = periodArr.getJSONObject(i);
                        Periods periods = new Periods(
                                periodObj.optString("REF"),
                                periodObj.optString("PeriodName")
                        );
                        CacheHelper.getInstance().periodsList.add(periods);
                    }
                    setPeriodsData();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    private void getPendingProgramsCount(String periodID) {
        String url = ApiEndPoints.ADMIN_PENDING_PROGRAMS_COUNT_URL
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&UserId=" + activity.session.getUserDetails().get(activity.session.KEY_NATIONAL_ID)
                + "&PeriodId=" + periodID;
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                pendingProgramsCountTxt.setText(res.optString("con"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPeriods();
    }
}
