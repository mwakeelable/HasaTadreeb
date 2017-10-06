package com.linked_sys.tadreeb_ihssa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.linked_sys.tadreeb_ihssa.R;
import com.linked_sys.tadreeb_ihssa.activities.StudentCoursesActivity;
import com.linked_sys.tadreeb_ihssa.utils.v4.FragmentPagerItemAdapter;
import com.linked_sys.tadreeb_ihssa.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import static com.linked_sys.tadreeb_ihssa.R.id.tab;

/**
 * Created by wakeel on 06/10/17.
 */

public class RecentTraineeProgramsFragment extends Fragment {
    StudentCoursesActivity activity;
    private ViewPager viewPager;
    public PendingProgramsFragment FRAGMENT_PENDING_PROGRAMS; //-1
    public StartedProgramsFragment FRAGMENT_STARTED_PROGRAMS; //0
    public ApprovedProgramsFragment FRAGMENT_APPROVED_PROGRAMS; //1
    public AttendProgramsFragment FRAGMENT_ATTEND_PROGRAMS; //3
    public RefusedProgramsFragment FRAGMENT_REFUSED_PROGRAMS; //4
    SmartTabLayout smartTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        activity = (StudentCoursesActivity) getActivity();
        return inflater.inflate(R.layout.user_programs_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FRAGMENT_PENDING_PROGRAMS = new PendingProgramsFragment();
        FRAGMENT_STARTED_PROGRAMS = new StartedProgramsFragment();
        FRAGMENT_APPROVED_PROGRAMS = new ApprovedProgramsFragment();
        FRAGMENT_ATTEND_PROGRAMS = new AttendProgramsFragment();
        FRAGMENT_REFUSED_PROGRAMS = new RefusedProgramsFragment();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
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
        viewPager.setOffscreenPageLimit(5);
        FragmentPagerItemAdapter pagerItemAdapter = new FragmentPagerItemAdapter(
                activity.getSupportFragmentManager(), FragmentPagerItems.with(activity)
                .add(R.string.tab_1, PendingProgramsFragment.class)
                .add(R.string.tab_2, StartedProgramsFragment.class)
                .add(R.string.tab_3, ApprovedProgramsFragment.class)
                .add(R.string.tab_4, AttendProgramsFragment.class)
                .add(R.string.tab_5, RefusedProgramsFragment.class)
                .create());
        viewPager.setAdapter(pagerItemAdapter);
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
