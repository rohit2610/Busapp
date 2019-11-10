package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.busapp.tab_slide.PageViewAdapter;


import android.content.Intent;
import android.os.Bundle;



public class CancelTicket extends AppCompatActivity {

    PageViewAdapter pageViewAdapter ;
    ViewPager viewPager ;
    private String from ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_ticket);
        from = getIntent().getStringExtra("From");

        pageViewAdapter = new PageViewAdapter(getSupportFragmentManager(),from);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this,Options.class));
        finish();
    }
}
