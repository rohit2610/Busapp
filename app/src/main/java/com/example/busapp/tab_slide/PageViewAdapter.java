package com.example.busapp.tab_slide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageViewAdapter extends FragmentPagerAdapter {
    public PageViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null ;

        switch (position){
            case 0:
                fragment = new Fragment_Upcoming( );
                break;

            case 1:
                fragment = new Fragment_Past();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
