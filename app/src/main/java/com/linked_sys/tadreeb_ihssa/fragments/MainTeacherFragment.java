package com.linked_sys.tadreeb_ihssa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.MainActivity;
import com.linked_sys.tadreeb_ihssa.activities.TeacherAttendProgramsActivity;
import com.linked_sys.tadreeb_ihssa.activities.TeacherCertificatesActivity;


public class MainTeacherFragment extends Fragment {
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main_teacher_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnStudentLoginContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TeacherCertificatesActivity.class);
            }
        });

        view.findViewById(R.id.btnTeacherLoginContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TeacherAttendProgramsActivity.class);
            }
        });
    }
}
