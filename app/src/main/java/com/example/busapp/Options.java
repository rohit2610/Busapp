package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatViewInflater;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Options extends MenuClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }




    public void viewTimeTable(View view){

        Intent i = new Intent(this,ViewTimeTable.class);
        startActivity(i);
    }


    public void bookTicket(View view) {
        Intent i = new Intent(this,FromWhere.class);
        startActivity(i);
    }

    public void viewTicket(View view){

        Intent i = new Intent(this,ViewTickets.class);
        startActivity(i);

    }

    public void cancelTicket(View view){

        Intent i = new Intent(this,CancelTicket.class);
        startActivity(i);
    }
}
