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

import java.util.HashMap;

public class HomeFragment extends Fragment {
    MainActivity activity;
    HashMap<String,String> userData = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView txtFullName = (TextView) view.findViewById(R.id.txt_name);
        TextView txtId = (TextView) view.findViewById(R.id.txt_id);
        TextView txtRef = (TextView) view.findViewById(R.id.txt_ref);
        userData = activity.session.getUserDetails();
        txtFullName.setText(userData.get(activity.session.KEY_FULL_NAME));
        txtId.setText(userData.get(activity.session.KEY_EMAIL));
        txtRef.setText(userData.get(activity.session.KEY_REF));
    }
}
