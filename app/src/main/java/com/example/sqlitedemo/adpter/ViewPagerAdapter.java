package com.example.sqlitedemo.adpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sqlitedemo.fragment.FragmentHistory;
import com.example.sqlitedemo.fragment.FragmentHome;
import com.example.sqlitedemo.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int page_number;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.page_number = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentHistory();
            case 2:
                return new FragmentSearch();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return page_number;
    }
}
