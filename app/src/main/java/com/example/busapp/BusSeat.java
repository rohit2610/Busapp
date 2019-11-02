package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BusSeat extends AppCompatActivity {

    public GridView gridView;
    ArrayList<Integer> selectedSeatNumber;
    ArrayList<Integer> bookedSeats;
    Button bookTicket;
    CustomAdaptar customAdaptar;
    private String date ;
    private String time ;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat);
        bookedSeats = new ArrayList<>();
        gridView = findViewById(R.id.gridView);


        Intent i = getIntent();




        date  = i.getStringExtra("Date");
        time = i.getStringExtra("Time");

        databaseReference = FirebaseDatabase.getInstance().getReference().child(date).child(time);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    bookedSeats = (ArrayList<Integer>) dataSnapshot.getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Log.i("Booked Seats 1", Arrays.toString(bookedSeats.toArray()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                customAdaptar = new CustomAdaptar(getApplicationContext(), bookedSeats);
                gridView.setAdapter(customAdaptar);

            }
        }, 3000);


        selectedSeatNumber = new ArrayList<>();


        bookTicket = findViewById(R.id.bookTicketButton);

        bookTicket.setVisibility(View.INVISIBLE);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ImageView imageView = view.findViewById(R.id.layoutImageView);
                if (bookedSeats.contains(position + 1)) {
                    Object deletedSeat = (Integer) position +1 ;
                    bookedSeats.remove(deletedSeat);
                    imageView.setImageResource(R.drawable.normal_seat);
                } else {
                    bookedSeats.add(position + 1);
                    imageView.setImageResource(R.drawable.processing_seat);
                }


                bookTicket.setVisibility(View.VISIBLE);


            }
        });


    }

    public void bookTickets(View view) {



        Log.i("Date", date);
        Log.i("Time", time);
        Log.i("Booked Seats 1",Arrays.toString(bookedSeats.toArray()));
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(date).child(time).setValue(bookedSeats);




    }


}
