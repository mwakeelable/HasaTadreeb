package com.linked_sys.hasatraining.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.fragments.UserProgramsFragment;

public class MyCoursesActivity extends BaseActivity {
    public FrameLayout mFrameLayout;
    UserProgramsFragment FRAGMENT_USER_PROGRAMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_my_courses));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View shadow = findViewById(R.id.toolbar_shadow);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shadow.setVisibility(View.VISIBLE);
        else
            shadow.setVisibility(View.GONE);
        mFrameLayout = (FrameLayout) findViewById(R.id.containerView);
        FRAGMENT_USER_PROGRAMS = new UserProgramsFragment();
        drawUsersProgramsFragment();
    }

    private void drawUsersProgramsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFrameLayout.getChildCount() == 0) {
            transaction.replace(R.id.containerView, FRAGMENT_USER_PROGRAMS);
        } else {
            transaction.add(R.id.containerView, FRAGMENT_USER_PROGRAMS);
        }
        transaction.commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.my_courses_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}
