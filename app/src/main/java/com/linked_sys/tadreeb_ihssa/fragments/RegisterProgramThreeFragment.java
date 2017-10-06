package com.linked_sys.tadreeb_ihssa.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.RegisterProgramActivity;
import com.linked_sys.tadreeb_ihssa.adapters.RegisterProgramAdapter;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;

public class RegisterProgramThreeFragment extends Fragment implements  RegisterProgramAdapter.RegisterProgramAdapterListener {
    RegisterProgramActivity activity;
    private RecyclerView recyclerView;
    public RegisterProgramAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_three_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new RegisterProgramAdapter(activity, CacheHelper.getInstance().programByPeriods, this);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onProgramRowClicked(int position) {
        activity.drawProgramDetails(position);
    }
}

