package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ViewTickets extends AppCompatActivity {

    DatabaseReference databaseReference ;
    ArrayList<Integer> bookedSeats ;
    private ArrayList<Integer> printingList ;
    private ListView viewTicketsListView ;
    private ArrayAdapter<Integer> viewTicketArrayAdaptor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);

        bookedSeats = new ArrayList<>();
        printingList = new ArrayList<>();
        viewTicketsListView  = findViewById(R.id.viewTicketsListView);
        viewTicketArrayAdaptor = new ArrayAdapter(this,android.R.layout.simple_list_item_1,printingList);
        bookedSeats.add(10);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bookedSeats = (ArrayList<Integer>) dataSnapshot.getValue();



                        for(int i = 0 ; i < bookedSeats.size() ; i++) {
                            printingList.add(bookedSeats.get(i));

                        }

                        viewTicketsListView.setAdapter(viewTicketArrayAdaptor);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        },5000);




    }


}
