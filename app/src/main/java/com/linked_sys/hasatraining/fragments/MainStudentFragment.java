package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.MainActivity;
import com.linked_sys.hasatraining.activities.RegisterProgramActivity;
import com.linked_sys.hasatraining.activities.StudentCertificatesActivity;
import com.linked_sys.hasatraining.activities.StudentCoursesActivity;

public class MainStudentFragment extends Fragment {
    MainActivity activity;
    public TextView txtProgCount, txtCertificatesCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main_student_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtProgCount = (TextView) view.findViewById(R.id.txtProgramsCount);
        txtCertificatesCount = (TextView) view.findViewById(R.id.txtCertificatesCount);
        view.findViewById(R.id.btnDonePrograms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCertificatesActivity.class);
            }
        });
        view.findViewById(R.id.btnCurrentPrograms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCoursesActivity.class);
            }
        });
        view.findViewById(R.id.btnRegisterInProgram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(RegisterProgramActivity.class);
            }
        });
    }
}
