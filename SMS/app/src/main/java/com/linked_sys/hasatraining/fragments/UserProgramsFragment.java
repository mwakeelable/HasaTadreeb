package com.linked_sys.hasatraining.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etiya.etiyabadgetablib.EtiyaBadgeTab;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.MyCoursesActivity;

public class UserProgramsFragment extends Fragment {
    MyCoursesActivity activity;
    private ViewPager viewPager;
    public PendingProgramsFragment FRAGMENT_PENDING_PROGRAMS; //-1
    public StartedProgramsFragment FRAGMENT_STARTED_PROGRAMS; //0
    public ApprovedProgramsFragment FRAGMENT_APPROVED_PROGRAMS; //1
    public AchievedProgramsFragment FRAGMENT_ACHIEVED_PROGRAMS; //2
    public AttendProgramsFragment FRAGMENT_ATTEND_PROGRAMS; //3
    public RefusedProgramsFragment FRAGMENT_REFUSED_PROGRAMS; //4
    private EtiyaBadgeTab etiyaBadgeTab;
    private String[] titles = new String[6];
    private MainAdapter adapter;
    String[] programStatus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new MainAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        activity = (MyCoursesActivity) getActivity();
        return inflater.inflate(R.layout.user_programs_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        defineControls(view);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);
        etiyaBadgeTab.setupWithViewPager(viewPager);
        setupTabs();
    }

    private void setupTabs() {
        programStatus = activity.getResources().getStringArray(R.array.program_status);
        etiyaBadgeTab.setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.black));
        etiyaBadgeTab.setSelectedTabIndicatorHeight(5);
        etiyaBadgeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        etiyaBadgeTab.setTabGravity(TabLayout.GRAVITY_CENTER);
        etiyaBadgeTab.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        etiyaBadgeTab.selectEtiyaBadgeTab(0)
                .tabTitle(programStatus[0])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(10)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();

        etiyaBadgeTab.selectEtiyaBadgeTab(1)
                .tabTitle(programStatus[1])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(20)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();

        etiyaBadgeTab.selectEtiyaBadgeTab(2)
                .tabTitle(programStatus[2])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(15)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();

        etiyaBadgeTab.selectEtiyaBadgeTab(3)
                .tabTitle(programStatus[3])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(2)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();

        etiyaBadgeTab.selectEtiyaBadgeTab(4)
                .tabTitle(programStatus[4])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(2)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();

        etiyaBadgeTab.selectEtiyaBadgeTab(5)
                .tabTitle(programStatus[5])
                .tabTitleColor(R.color.White)
                .tabIconColor(R.color.White)
                .tabBadge(false)
                .tabBadgeCount(2)
                .tabBadgeBgColor(R.color.red_400)
                .tabBadgeTextColor(R.color.White)
                .tabBadgeStroke(1, R.color.White)
                .tabBadgeCornerRadius(10)
                .createEtiyaBadgeTab();
    }

    private void defineControls(View view) {
        FRAGMENT_PENDING_PROGRAMS = new PendingProgramsFragment();
        FRAGMENT_STARTED_PROGRAMS = new StartedProgramsFragment();
        FRAGMENT_ACHIEVED_PROGRAMS = new AchievedProgramsFragment();
        FRAGMENT_APPROVED_PROGRAMS = new ApprovedProgramsFragment();
        FRAGMENT_ATTEND_PROGRAMS = new AttendProgramsFragment();
        FRAGMENT_REFUSED_PROGRAMS = new RefusedProgramsFragment();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        etiyaBadgeTab = (EtiyaBadgeTab) view.findViewById(R.id.etiyaBadgeTabs);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MainAdapter extends FragmentStatePagerAdapter {
        MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (FRAGMENT_PENDING_PROGRAMS == null)
                    FRAGMENT_PENDING_PROGRAMS = new PendingProgramsFragment();
                return FRAGMENT_PENDING_PROGRAMS;
            } else if (position == 1) {
                if (FRAGMENT_STARTED_PROGRAMS == null)
                    FRAGMENT_STARTED_PROGRAMS = new StartedProgramsFragment();
                return FRAGMENT_STARTED_PROGRAMS;
            } else if (position == 2) {
                if (FRAGMENT_APPROVED_PROGRAMS == null)
                    FRAGMENT_APPROVED_PROGRAMS = new ApprovedProgramsFragment();
                return FRAGMENT_APPROVED_PROGRAMS;
            } else if (position == 3) {
                if (FRAGMENT_ACHIEVED_PROGRAMS == null)
                    FRAGMENT_ACHIEVED_PROGRAMS = new AchievedProgramsFragment();
                return FRAGMENT_ACHIEVED_PROGRAMS;
            } else if (position == 4) {
                if (FRAGMENT_ATTEND_PROGRAMS == null)
                    FRAGMENT_ATTEND_PROGRAMS = new AttendProgramsFragment();
                return FRAGMENT_ATTEND_PROGRAMS;
            } else {
                if (FRAGMENT_REFUSED_PROGRAMS == null)
                    FRAGMENT_REFUSED_PROGRAMS = new RefusedProgramsFragment();
                return FRAGMENT_REFUSED_PROGRAMS;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
