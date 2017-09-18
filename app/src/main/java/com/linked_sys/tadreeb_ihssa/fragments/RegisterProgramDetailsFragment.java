package com.linked_sys.tadreeb_ihssa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.RegisterProgramActivity;
import com.linked_sys.tadreeb_ihssa.core.AppController;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONObject;

public class RegisterProgramDetailsFragment extends Fragment {
    RegisterProgramActivity activity;
    TextView txtProgramID, txtProgramName, txtProgramDays, txtProgramTime, txtProgramDate, txtProgramDateFrom, txtProgramDateTo, txtProgramTimeStart, txtProgramLocation;
    public String regRef;
    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtProgramID = (TextView) view.findViewById(R.id.txt_program_id);
        txtProgramName = (TextView) view.findViewById(R.id.txt_program_name);
        txtProgramDays = (TextView) view.findViewById(R.id.txt_program_days);
        txtProgramTime = (TextView) view.findViewById(R.id.txt_program_time);
        txtProgramDate = (TextView) view.findViewById(R.id.txt_program_date);
        txtProgramDateFrom = (TextView) view.findViewById(R.id.txt_program_days_from);
        txtProgramDateTo = (TextView) view.findViewById(R.id.txt_program_days_to);
        txtProgramTimeStart = (TextView) view.findViewById(R.id.txt_program_time_start);
        txtProgramLocation = (TextView) view.findViewById(R.id.txt_program_location);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            regRef = bundle.getString("REF");
            getProgramDetails();
        }

    }

    private void getProgramDetails() {
        String url = ApiEndPoints.GET_PROGRAM_DATA_BY_REF_ID
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&ProgramRef=" + regRef;
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                Log.d(AppController.TAG, response.toString());
                JSONObject res = (JSONObject) response;
                JSONObject progObj = res.optJSONObject("con");
                txtProgramID.setText(progObj.optString("ProgramID"));
                txtProgramName.setText(progObj.optString("ProgramName"));
                txtProgramDays.setText(progObj.optString("ProgranDays"));
                txtProgramTime.setText(progObj.optString("ProgramTime"));
                txtProgramDate.setText(progObj.optString("ProgramDate"));
                txtProgramDateFrom.setText(progObj.optString("ProgramDate"));
                txtProgramDateTo.setText(progObj.optString("ProgramDateEnd"));
                txtProgramTimeStart.setText(progObj.optString("ProgramTimeStart"));
                txtProgramLocation.setText(progObj.optString("ProgramLocation"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }
}
