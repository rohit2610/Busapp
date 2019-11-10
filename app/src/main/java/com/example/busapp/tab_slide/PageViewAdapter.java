package com.example.busapp.tab_slide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageViewAdapter extends FragmentPagerAdapter {
    String from ;
    public PageViewAdapter(@NonNull FragmentManager fm,String from) {
        super(fm);

        this.from = from ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null ;

        switch (position){
            case 0:
                fragment = new Fragment_Upcoming();
                Bundle args = new Bundle();
                args.putString("From",from);
                fragment.setArguments(args);
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
