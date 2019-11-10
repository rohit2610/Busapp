package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.sql.Time;

public class FromWhere extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_where);
    }


    public void click(View view){

        int id = view.getId();

        if(id == R.id.fromCityButton){
            Intent i = new Intent(this, TimeTable.class);
            i.putExtra("From","FromCity");
            startActivity(i);
        }

        else if(id  == R.id.fromCollegeButton){
            Intent  i = new Intent(this,TimeTable.class);
            i.putExtra("From","FromCollege");
            startActivity(i);

        }
    }

}
