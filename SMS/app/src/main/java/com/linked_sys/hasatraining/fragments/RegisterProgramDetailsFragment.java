package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.RegisterProgramActivity;
import com.linked_sys.hasatraining.core.CacheHelper;

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
            position = bundle.getInt("pos");
            regRef = CacheHelper.getInstance().programByPeriods.get(position).getREF();
            txtProgramID.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramID());
            txtProgramName.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramName());
            txtProgramDays.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramDays());
            txtProgramDate.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramDateStrat());
            txtProgramTime.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramTimes());
            txtProgramDateFrom.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramDateStrat());
            txtProgramDateTo.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramDateEnd());
            txtProgramLocation.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramLocation());
            txtProgramTimeStart.setText(CacheHelper.getInstance().programByPeriods.get(position).getProgramTimeStrat());
        }

    }
}
