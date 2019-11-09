package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BusSeat extends AppCompatActivity {

    public GridView gridView;
    private ArrayList<Integer> selectedSeatNumber;
    private ArrayList<Integer> bookedSeats;
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
        selectedSeatNumber = new ArrayList<>();


        Intent i = getIntent();




        date  = i.getStringExtra("Date");
        time = i.getStringExtra("Time");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUserClass user = dataSnapshot.child("Booked Seats").child(date).child(time).getValue(FirebaseUserClass.class);
                //Log.i("User",String.valueOf(user.getBookedSeats().get(0).getClass().getName()));

                 if(user != null)
                    bookedSeats = user.getBookedSeats();

                else
                    bookedSeats.clear();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(postListener);







        /*databaseReference = FirebaseDatabase.getInstance().getReference().child("Booked Seats");
        databaseReference.child(date).child(time).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                bookedSeats = (ArrayList<Integer>) dataSnapshot.child("bookedSeats").getValue();
                FirebaseUserClass userClass = dataSnapshot.getValue(FirebaseUserClass.class);



                if(bookedSeats == null)
                    bookedSeats = new ArrayList<>();
                Log.i("DAta Snapshot 2", String.valueOf(userClass.getBookedSeats()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*databaseReference = FirebaseDatabase.getInstance().getReference().child(date).child(time);

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
        bookedSeats.add(100);*/


        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.i("DAta Snapshot", String.valueOf(bookedSeats));
                customAdaptar = new CustomAdaptar(getApplicationContext(),bookedSeats);
                gridView.setAdapter(customAdaptar);
            }
        };
        handler.postDelayed(r,5000);
        /*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.i("DAta Snapshot", String.valueOf(bookedSeats));
                customAdaptar = new CustomAdaptar(getApplicationContext(),bookedSeats);
                gridView.setAdapter(customAdaptar);

            }
        }, 5000);*/





        bookTicket = findViewById(R.id.bookTicketButton);

        bookTicket.setVisibility(View.INVISIBLE);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ImageView imageView = view.findViewById(R.id.layoutImageView);
                Object selectedSeat = position+1 ;

                if( ! bookedSeats.contains(position+1)) {

                    if (selectedSeatNumber.contains(selectedSeat)) {
                        selectedSeatNumber.remove(selectedSeat);
                        imageView.setImageResource(R.drawable.normal_seat);
                    } else {
                        selectedSeatNumber.add((Integer) selectedSeat);
                        imageView.setImageResource(R.drawable.processing_seat);
                    }


                    bookTicket.setVisibility(View.VISIBLE);
                }


            }
        });


    }

    public void bookTickets(View view) {



        Log.i("Date", date);
        Log.i("Time", time);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Booked Seats");
        for(int i = 0 ; i < selectedSeatNumber.size() ; i++){
            bookedSeats.add(selectedSeatNumber.get(i));
        }

        FirebaseUserClass userClass = new FirebaseUserClass(bookedSeats);
        databaseReference.child(date).child(time).setValue(userClass)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isCanceled()){
                    Log.i("Error",task.getException().toString());
                }
            }
        });
        Log.i("Booked Seats 1",Arrays.toString(bookedSeats.toArray()));
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        User user = new User(selectedSeatNumber,time,date);

        ;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference reference = databaseReference.child(username).push();
        reference.setValue(user);
        /*databaseReference.child(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isCanceled()){
                    Log.i("Error2",task.getException().toString());
                }
            }
        });*/




    }




}
