package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.MainActivity;
import com.linked_sys.hasatraining.activities.StudentCertificatesActivity;
import com.linked_sys.hasatraining.activities.StudentCoursesActivity;
import com.linked_sys.hasatraining.activities.RegisterProgramActivity;

public class HomeFragment extends Fragment {
    MainActivity activity;
    public TextView txtProgCount, txtCertificatesCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtProgCount = (TextView) view.findViewById(R.id.txtProgramsCount);
        txtCertificatesCount = (TextView) view.findViewById(R.id.txtCertificatesCount);
//        txtProgCount.setText(activity.session.getUserDetails().get(activity.session.KEY_PROGRAMS_COUNT));
//        txtCertificatesCount.setText(activity.session.getUserDetails().get(activity.session.KEY_CERTIFICATES_COUNT));
        view.findViewById(R.id.btnMyCertificates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCertificatesActivity.class);
            }
        });
        view.findViewById(R.id.btnMyCourses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCoursesActivity.class);
            }
        });
        view.findViewById(R.id.btnAllPrograms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(RegisterProgramActivity.class);
            }
        });
        view.findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.openActivity(SettingsActivity.class);
                Toast.makeText(activity, "Under Developing", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
