package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.AllProgramsActivity;
import com.linked_sys.hasatraining.activities.MainActivity;
import com.linked_sys.hasatraining.activities.MyCertificatesActivity;
import com.linked_sys.hasatraining.activities.MyCoursesActivity;
import com.linked_sys.hasatraining.activities.SettingsActivity;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btnMyCertificates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(MyCertificatesActivity.class);
            }
        });
        view.findViewById(R.id.btnMyCourses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(MyCoursesActivity.class);
            }
        });
        view.findViewById(R.id.btnAllPrograms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(AllProgramsActivity.class);
            }
        });
        view.findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openActivity(SettingsActivity.class);
            }
        });
    }
}
