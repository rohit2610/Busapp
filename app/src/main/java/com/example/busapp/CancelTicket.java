package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.busapp.tab_slide.PageViewAdapter;


import android.os.Bundle;



public class CancelTicket extends AppCompatActivity {

    PageViewAdapter pageViewAdapter ;
    ViewPager viewPager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_ticket);

        pageViewAdapter = new PageViewAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.fragment_container);


        viewPager.setAdapter(pageViewAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
