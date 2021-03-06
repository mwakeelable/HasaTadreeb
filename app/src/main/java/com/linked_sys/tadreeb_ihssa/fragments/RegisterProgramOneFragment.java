package com.linked_sys.tadreeb_ihssa.fragments;


import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.RegisterProgramActivity;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.models.Periods;
import com.linked_sys.tadreeb_ihssa.models.ProgramByPeriod;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterProgramOneFragment extends Fragment {
    RegisterProgramActivity activity;
    public CheckBox chkAcceptLicence;
    Spinner periodsSpinner;
    ArrayAdapter<Periods> periodAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_one_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        TextView txtUserID = (TextView) view.findViewById(R.id.txtUserID);
        chkAcceptLicence = (CheckBox) view.findViewById(R.id.chkAcceptLicence);
        periodsSpinner = (Spinner) view.findViewById(R.id.periodsSpinner);
        txtUserID.setText(activity.session.getUserDetails().get(activity.session.KEY_NATIONAL_ID));
        getProgramPeriods();
        periodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Periods periods = (Periods) parent.getSelectedItem();
                activity.periodRef = periods.getPeriodREF();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ImageView imgSpinner = (ImageView) view.findViewById(R.id.imgSpinner);
        imgSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodsSpinner.performClick();
            }
        });

        view.findViewById(R.id.parent_view).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                view.findViewById(R.id.scrollView).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        view.findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
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

    private void getProgramPeriods() {
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
        api.executeRequest(false, false);
    }
}
