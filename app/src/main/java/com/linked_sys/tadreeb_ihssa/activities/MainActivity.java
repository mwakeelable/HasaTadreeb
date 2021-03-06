package com.linked_sys.tadreeb_ihssa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.core.CacheHelper;
import com.linked_sys.tadreeb_ihssa.fragments.MainAdminFragment;
import com.linked_sys.tadreeb_ihssa.fragments.MainStudentFragment;
import com.linked_sys.tadreeb_ihssa.fragments.MainTeacherFragment;
import com.linked_sys.tadreeb_ihssa.network.ApiCallback;
import com.linked_sys.tadreeb_ihssa.network.ApiEndPoints;
import com.linked_sys.tadreeb_ihssa.network.ApiHelper;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    //    DrawerLayout mDrawerLayout;
    CoordinatorLayout mCoordinatorLayout;
    Toolbar mToolbar;
    //    public DrawerView mDrawer;
    MainStudentFragment FRAGMENT_STUDENT_MAIN;
    MainTeacherFragment FRAGMENT_TEACHER_MAIN;
    MainAdminFragment FRAGMENT_ADMIN_MAIN;
    public static final int REQUEST_TEACHER_CODE = 0;
    LinearLayout btnMenu;
    TextView headerTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mDrawer = (DrawerView) findViewById(R.id.drawer);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnMenu = (LinearLayout) findViewById(R.id.menuImgView);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Profile
                openActivity(ProfileActivity.class);
            }
        });
        headerTXT = (TextView) findViewById(R.id.mainHeaderTxt);
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
//                this,
//                mDrawerLayout,
//                mToolbar,
//                R.string.drawer_open,
//                R.string.drawer_close
//        ) {
//            public void onDrawerClosed(View view) {
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu();
//            }
//        };
//        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        mDrawerLayout.addDrawerListener(drawerToggle);
//        mDrawerLayout.closeDrawer(mDrawer);

        //Handle interface based on user type
        if (session.getUserDetails().get(session.KEY_USER_TYPE).equals("0")) {
            //Teacher
//            createTeacherNavMenu();
            FRAGMENT_TEACHER_MAIN = new MainTeacherFragment();
            drawFragment(FRAGMENT_TEACHER_MAIN);
//            headerTXT.setText("المدرب");
        } else if (session.getUserDetails().get(session.KEY_USER_TYPE).equals("1")) {
            //Student
//            createStudentNavMenu();
            FRAGMENT_STUDENT_MAIN = new MainStudentFragment();
            drawFragment(FRAGMENT_STUDENT_MAIN);
//            headerTXT.setText("المتدرب");
        } else if (session.getUserDetails().get(session.KEY_USER_TYPE).equals("2")) {
            //Admin
//            createAdminNavMenu();
            FRAGMENT_ADMIN_MAIN = new MainAdminFragment();
            drawFragment(FRAGMENT_ADMIN_MAIN);
//            headerTXT.setText("منسق التدريب");
        }
        getUnreadMessagesCount();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
//        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
//            this.mDrawerLayout.closeDrawer(GravityCompat.START);
//        else

        if (FRAGMENT_STUDENT_MAIN != null && FRAGMENT_STUDENT_MAIN.isVisible())
            finishAffinity();
        else if (FRAGMENT_TEACHER_MAIN != null && FRAGMENT_TEACHER_MAIN.isVisible())
            finishAffinity();
        else if (FRAGMENT_ADMIN_MAIN != null && FRAGMENT_ADMIN_MAIN.isVisible())
            finishAffinity();
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else {
            super.onBackPressed();
//            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (FRAGMENT_TEACHER_MAIN != null && FRAGMENT_TEACHER_MAIN.isVisible()) {
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.search_menu, menu);
//            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//            MenuItem searchMenuItem = menu.findItem(R.id.search);
//            SearchView searchView = (SearchView) searchMenuItem.getActionView();
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setSubmitButtonEnabled(true);
//            searchView.setOnQueryTextListener(this);
//            return true;
//        } else if (FRAGMENT_STUDENT_MAIN != null && FRAGMENT_STUDENT_MAIN.isVisible()) {
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            return true;
//        } else {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (FRAGMENT_TEACHER_MAIN != null && FRAGMENT_TEACHER_MAIN.isVisible()) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    MainActivity.this.finish();
                    hideSoftKeyboard(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        } else {
            int id = item.getItemId();
            switch (id) {
                case android.R.id.home:
//                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

//    private void createStudentNavMenu() {
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.program_register_icon))
//                .setTextPrimary("الرسـائل")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(MailActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.current_programs_icon))
//                .setTextPrimary("البرامج الحالية")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(StudentCoursesActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.certificate_icon))
//                .setTextPrimary("البرامج المنجزة")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(StudentCertificatesActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.register_icon))
//                .setTextPrimary(getString(R.string.nav_register))
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(RegisterProgramActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                        .setImage(ContextCompat.getDrawable(this, R.drawable.sign_out_icon))
//                        .setTextPrimary(getString(R.string.action_sign_out))
//                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                            @Override
//                            public void onClick(DrawerItem drawerItem, long l, int i) {
//                                mDrawerLayout.closeDrawer(GravityCompat.START);
//                                new MaterialDialog.Builder(MainActivity.this)
//                                        .title(getResources().getString(R.string.action_sign_out))
//                                        .content(getResources().getString(R.string.txt_confirmation))
//                                        .positiveText(getResources().getString(R.string.txt_positive_btn))
//                                        .negativeText(getResources().getString(R.string.txt_negative_btn))
//                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                            @Override
//                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                                removeFBToken();
////                                        session.logoutUser();
//                                            }
//                                        })
//                                        .show();
//                            }
//                        })
//        );
//        mDrawer.addProfile(new DrawerProfile()
//                .setId(1)
//                .setBackground(ContextCompat.getDrawable(MainActivity.this, R.color.colorPrimary))
//                .setName(session.getUserDetails().get(session.KEY_FULL_NAME))
//                .setDescription(session.getUserDetails().get(session.KEY_USER_SCHOOL))
//                .setAvatar(ContextCompat.getDrawable(this, R.drawable.profile_with_frame_icon))
//        );
//    }
//
//    private void createTeacherNavMenu() {
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.program_register_icon))
//                .setTextPrimary("الرسـائل")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(MailActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                        .setImage(ContextCompat.getDrawable(this, R.drawable.current_programs_icon))
//                        .setTextPrimary("البرامج الحـالية")
//                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                            @Override
//                            public void onClick(DrawerItem drawerItem, long l, int i) {
////                        if (!FRAGMENT_TEACHER_MAIN.isVisible()) {
////                            drawFragment(FRAGMENT_TEACHER_MAIN);
////                            mDrawerLayout.closeDrawer(GravityCompat.START);
////                        } else
////                            mDrawerLayout.closeDrawer(GravityCompat.START);
//                                mDrawerLayout.closeDrawer(GravityCompat.START);
//                                openActivity(TeacherAttendProgramsActivity.class);
//                            }
//                        })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.done_programs_icon))
//                .setTextPrimary("البرامج المنجزة")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(TeacherCertificatesActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                        .setImage(ContextCompat.getDrawable(this, R.drawable.sign_out_icon))
//                        .setTextPrimary(getString(R.string.action_sign_out))
//                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                            @Override
//                            public void onClick(DrawerItem drawerItem, long l, int i) {
//                                mDrawerLayout.closeDrawer(GravityCompat.START);
//                                new MaterialDialog.Builder(MainActivity.this)
//                                        .title(getResources().getString(R.string.action_sign_out))
//                                        .content(getResources().getString(R.string.txt_confirmation))
//                                        .positiveText(getResources().getString(R.string.txt_positive_btn))
//                                        .negativeText(getResources().getString(R.string.txt_negative_btn))
//                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                            @Override
//                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                                removeFBToken();
////                                        session.logoutUser();
//                                            }
//                                        })
//                                        .show();
//                            }
//                        })
//        );
//        mDrawer.addProfile(new DrawerProfile()
//                .setId(1)
//                .setBackground(ContextCompat.getDrawable(MainActivity.this, R.color.colorPrimary))
//                .setName(session.getUserDetails().get(session.KEY_FULL_NAME))
//                .setAvatar(ContextCompat.getDrawable(this, R.drawable.profile_with_frame_icon))
//        );
//    }
//
//    private void createAdminNavMenu() {
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.program_register_icon))
//                .setTextPrimary("الرسـائل")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(MailActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.current_programs_icon))
//                .setTextPrimary("قائمة طلبات المعلمين المعلقة")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(AdminPendingProgramsActivity.class);
//
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.profile_with_frame_icon))
//                .setTextPrimary("بيانات المعلمين")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(TeachersActivity.class);
//
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.support_icon))
//                .setTextPrimary("تذكرة الدعم الفني")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(SendTechnicalTicketActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                .setImage(ContextCompat.getDrawable(this, R.drawable.current_programs_icon))
//                .setTextPrimary("عرض برامج معلم / موظف")
//                .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                    @Override
//                    public void onClick(DrawerItem drawerItem, long l, int i) {
//                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        openActivity(SearchActivity.class);
//                    }
//                })
//        );
//        mDrawer.addDivider();
//        mDrawer.addItem(new DrawerItem()
//                        .setImage(ContextCompat.getDrawable(this, R.drawable.sign_out_icon))
//                        .setTextPrimary(getString(R.string.action_sign_out))
//                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
//                            @Override
//                            public void onClick(DrawerItem drawerItem, long l, int i) {
//                                mDrawerLayout.closeDrawer(GravityCompat.START);
//                                new MaterialDialog.Builder(MainActivity.this)
//                                        .title(getResources().getString(R.string.action_sign_out))
//                                        .content(getResources().getString(R.string.txt_confirmation))
//                                        .positiveText(getResources().getString(R.string.txt_positive_btn))
//                                        .negativeText(getResources().getString(R.string.txt_negative_btn))
//                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                            @Override
//                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                                removeFBToken();
////                                        session.logoutUser();
//                                            }
//                                        })
//                                        .show();
//                            }
//                        })
//        );
//        mDrawer.addProfile(new DrawerProfile()
//                .setId(1)
//                .setBackground(ContextCompat.getDrawable(MainActivity.this, R.color.colorPrimary))
//                .setName(session.getUserDetails().get(session.KEY_FULL_NAME))
//                .setAvatar(ContextCompat.getDrawable(this, R.drawable.school_placeholder))
//        );
//    }

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
        if (session.getUserDetails().get(session.KEY_USER_TYPE).equals("1")) {
//            refreshStudentCertificateCount();
//            refreshStudentProgramsCount();
        }
    }

    private void refreshStudentCertificateCount() {
        String url = ApiEndPoints.GET_CERTIFICATE_COUNT + "?APPCode=" + CacheHelper.getInstance().appCode + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
//                FRAGMENT_STUDENT_MAIN.txtCertificatesCount.setText(res.optString("ret"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(false, false);

    }

    private void refreshStudentProgramsCount() {
        String url = ApiEndPoints.GET_PROGRAMS_COUNT + "?APPCode=" + CacheHelper.getInstance().appCode + "&UserID=" + session.getUserDetails().get(session.KEY_NATIONAL_ID);
        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
//                FRAGMENT_STUDENT_MAIN.txtProgCount.setText(res.optString("ret"));
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        FRAGMENT_TEACHER_MAIN.FRAGMENT_ATTEND_PROGRAMS.mAdapter.getFilter().filter(newText);
//        FRAGMENT_TEACHER_MAIN.FRAGMENT_DONE_PROGRAMS.mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TEACHER_CODE) {

        }
    }
}
