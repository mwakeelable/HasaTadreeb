package com.linked_sys.hasatraining.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.fragments.UserProgramsFragment;

public class MyCoursesActivity extends BaseActivity implements SearchView.OnQueryTextListener{
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MyCoursesActivity.this.finish();
                hideSoftKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        FRAGMENT_USER_PROGRAMS.FRAGMENT_PENDING_PROGRAMS.mAdapter.getFilter().filter(newText);
        FRAGMENT_USER_PROGRAMS.FRAGMENT_STARTED_PROGRAMS.mAdapter.getFilter().filter(newText);
        FRAGMENT_USER_PROGRAMS.FRAGMENT_APPROVED_PROGRAMS.mAdapter.getFilter().filter(newText);
//        FRAGMENT_USER_PROGRAMS.FRAGMENT_ACHIEVED_PROGRAMS.mAdapter.getFilter().filter(newText);
        FRAGMENT_USER_PROGRAMS.FRAGMENT_ATTEND_PROGRAMS.mAdapter.getFilter().filter(newText);
        FRAGMENT_USER_PROGRAMS.FRAGMENT_REFUSED_PROGRAMS.mAdapter.getFilter().filter(newText);
        return true;
    }
}
