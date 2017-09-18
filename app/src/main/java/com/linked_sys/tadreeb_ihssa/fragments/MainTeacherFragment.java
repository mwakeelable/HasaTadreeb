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
//    private ViewPager viewPager;
//    public TeacherAttendProgramsFragment FRAGMENT_ATTEND_PROGRAMS;
//    public TeacherDoneProgramsFragment FRAGMENT_DONE_PROGRAMS;
//    private EtiyaBadgeTab etiyaBadgeTab;
//    private String[] titles = new String[2];
//    private MainAdapter adapter;
//    TextView txtTeacherName;
    LinearLayout btnCurrentPrograms, btnDonePrograms;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        adapter = new MainAdapter(getChildFragmentManager());
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.main_teacher_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btnCurrentPrograms = (LinearLayout) view.findViewById(R.id.btnCurrentPrograms);
        btnDonePrograms = (LinearLayout) view.findViewById(R.id.btnDonePrograms);

        btnDonePrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TeacherCertificatesActivity.class);
            }
        });

        btnCurrentPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openActivity(TeacherAttendProgramsActivity.class);
            }
        });


//        defineControls(view);
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(adapter);
//        etiyaBadgeTab.setupWithViewPager(viewPager);
//        setupTabs();
    }

//    private void setupTabs() {
//        etiyaBadgeTab.setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.black));
//        etiyaBadgeTab.setSelectedTabIndicatorHeight(5);
//        etiyaBadgeTab.setTabMode(TabLayout.GRAVITY_CENTER);
//        etiyaBadgeTab.setTabGravity(TabLayout.GRAVITY_FILL);
//        etiyaBadgeTab.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
//
//        etiyaBadgeTab.selectEtiyaBadgeTab(0)
//                .tabTitle("البرامج الحالـية")
//                .tabTitleColor(R.color.White)
//                .tabIconColor(R.color.White)
//                .tabBadge(false)
//                .tabBadgeCount(10)
//                .tabBadgeBgColor(R.color.red_400)
//                .tabBadgeTextColor(R.color.White)
//                .tabBadgeStroke(1, R.color.White)
//                .tabBadgeCornerRadius(10)
//                .createEtiyaBadgeTab();
//
//        etiyaBadgeTab.selectEtiyaBadgeTab(1)
//                .tabTitle("البرامج المنجـزة")
//                .tabTitleColor(R.color.White)
//                .tabIconColor(R.color.White)
//                .tabBadge(false)
//                .tabBadgeCount(20)
//                .tabBadgeBgColor(R.color.red_400)
//                .tabBadgeTextColor(R.color.White)
//                .tabBadgeStroke(1, R.color.White)
//                .tabBadgeCornerRadius(10)
//                .createEtiyaBadgeTab();
//    }

//    private void defineControls(View view) {
//        FRAGMENT_ATTEND_PROGRAMS = new TeacherAttendProgramsFragment();
//        FRAGMENT_DONE_PROGRAMS = new TeacherDoneProgramsFragment();
//        txtTeacherName = (TextView) view.findViewById(txtTeacherName);
//        txtTeacherName.setText(activity.session.getUserDetails().get(activity.session.KEY_FULL_NAME));
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        etiyaBadgeTab = (EtiyaBadgeTab) view.findViewById(R.id.etiyaBadgeTabs);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }

//    private class MainAdapter extends FragmentStatePagerAdapter {
//        MainAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if (position == 0) {
//                if (FRAGMENT_ATTEND_PROGRAMS == null)
//                    FRAGMENT_ATTEND_PROGRAMS = new TeacherAttendProgramsFragment();
//                return FRAGMENT_ATTEND_PROGRAMS;
//            } else {
//                if (FRAGMENT_DONE_PROGRAMS == null)
//                    FRAGMENT_DONE_PROGRAMS = new TeacherDoneProgramsFragment();
//                return FRAGMENT_DONE_PROGRAMS;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return titles.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
//    }
}
