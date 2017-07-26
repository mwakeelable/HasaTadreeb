package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.RegisterProgramActivity;

public class RegisterProgramTwoFragment extends Fragment {
    RegisterProgramActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_two_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
