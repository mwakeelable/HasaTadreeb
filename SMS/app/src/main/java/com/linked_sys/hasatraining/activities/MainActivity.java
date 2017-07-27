package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.heinrichreimersoftware.materialdrawer.DrawerView;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.fragments.HomeFragment;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    DrawerLayout mDrawerLayout;
    CoordinatorLayout mCoordinatorLayout;
    ActionBar mActionBar;
    Toolbar mToolbar;
    public DrawerView mDrawer;
    HomeFragment FRAGMENT_HOME;
    int userViewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerView) findViewById(R.id.drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mDrawerLayout.addDrawerListener(drawerToggle);
        mDrawerLayout.closeDrawer(mDrawer);
        createTraineeNavMenu();
        FRAGMENT_HOME = new HomeFragment();
        drawFragment(FRAGMENT_HOME);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        else if (FRAGMENT_HOME.isVisible())
            finishAffinity();
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createTraineeNavMenu() {
        userViewID = 1;
        mDrawer.addItem(new DrawerItem()
                .setImage(ContextCompat.getDrawable(this, R.drawable.ic_assignment_black_24dp))
                .setTextPrimary(getString(R.string.nav_my_courses))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem drawerItem, long l, int i) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        openActivity(MyCoursesActivity.class);
                    }
                })
        );
        mDrawer.addItem(new DrawerItem()
                .setImage(ContextCompat.getDrawable(this, R.drawable.ic_school_black_24dp))
                .setTextPrimary(getString(R.string.nav_my_certificates))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem drawerItem, long l, int i) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        openActivity(MyCertificatesActivity.class);
                    }
                })
        );
        mDrawer.addDivider();
        mDrawer.addItem(new DrawerItem()
                .setImage(ContextCompat.getDrawable(this, R.drawable.ic_list_black_24dp))
                .setTextPrimary(getString(R.string.nav_register))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem drawerItem, long l, int i) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        openActivity(RegisterProgramActivity.class);
                    }
                })
        );
        mDrawer.addDivider();
        mDrawer.addItem(new DrawerItem()
                        .setImage(ContextCompat.getDrawable(this, R.drawable.ic_settings_black_24dp))
                        .setTextPrimary(getString(R.string.action_settings))
                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                            @Override
                            public void onClick(DrawerItem drawerItem, long l, int i) {
                                mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(SettingsActivity.class);
                                Toast.makeText(MainActivity.this, "Under Developing", Toast.LENGTH_SHORT).show();
                            }
                        })
        );
        mDrawer.addItem(new DrawerItem()
                .setImage(ContextCompat.getDrawable(this, R.drawable.ic_power_settings_new_black_24dp))
                .setTextPrimary(getString(R.string.action_sign_out))
                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                    @Override
                    public void onClick(DrawerItem drawerItem, long l, int i) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        new MaterialDialog.Builder(MainActivity.this)
                                .title(getResources().getString(R.string.action_sign_out))
                                .content(getResources().getString(R.string.txt_confirmation))
                                .positiveText(getResources().getString(R.string.txt_positive_btn))
                                .negativeText(getResources().getString(R.string.txt_negative_btn))
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        session.logoutUser();
                                    }
                                })
                                .show();
                    }
                })
        );
        mDrawer.addProfile(new DrawerProfile()
                .setId(1)
                .setBackground(ContextCompat.getDrawable(MainActivity.this, R.color.colorPrimary))
                .setName(session.getUserDetails().get(session.KEY_FULL_NAME))
                .setDescription(session.getUserDetails().get(session.KEY_USER_SCHOOL))
                .setAvatar(ContextCompat.getDrawable(this, R.drawable.avatar_placeholder))
        );
    }

    private void closeFragments() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getFragmentManager().popBackStackImmediate();
            }
    }

    public void drawFragment(Fragment fragment) {
        closeFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCertificateCount();
        refreshProgramsCount();
    }

    private void refreshCertificateCount() {
        String url = ApiEndPoints.GET_CERTIFICATE_COUNT + "?APPCode=" + CacheHelper.getInstance().appCode + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                FRAGMENT_HOME.txtCertificatesCount.setText(res.optString("ret"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(false, false);

    }

    private void refreshProgramsCount() {
        String url = ApiEndPoints.GET_PROGRAMS_COUNT + "?APPCode=" + CacheHelper.getInstance().appCode + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                FRAGMENT_HOME.txtProgCount.setText(res.optString("ret"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }
}
