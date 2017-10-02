package com.linked_sys.tadreeb_ihssa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.MainActivity;
import com.linked_sys.tadreeb_ihssa.activities.RegisterProgramActivity;
import com.linked_sys.tadreeb_ihssa.activities.StudentCertificatesActivity;
import com.linked_sys.tadreeb_ihssa.activities.StudentCoursesActivity;

public class MainStudentFragment extends Fragment {
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main_student_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnFinished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCertificatesActivity.class);
            }
        });
        view.findViewById(R.id.btnRecent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(StudentCoursesActivity.class);
            }
        });
        view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(RegisterProgramActivity.class);
            }
        });
    }
}
