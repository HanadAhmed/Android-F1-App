package com.example.main.f1resultsapp.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.main.f1resultsapp.Fragments.ConstructorFragment;
import com.example.main.f1resultsapp.Fragments.DriverFragment;
import com.example.main.f1resultsapp.R;

import java.util.ArrayList;
import java.util.List;


public class StandingsFragment extends Fragment {


    public StandingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View standview = inflater.inflate(R.layout.standings_new_tabs,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = standview.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = standview.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);

        return standview;
    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new DriverFragment(), "Driver");
        adapter.addFragment(new ConstructorFragment(), "Constructor");
        viewPager.setAdapter(adapter);




    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}