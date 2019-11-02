package com.example.busapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomAdaptar extends BaseAdapter {

    Context context ;
    LayoutInflater layoutInflater ;
    int i = 1 ;
    ArrayList<Integer> bookedSeats ;
    private ArrayList<Integer> displaySeats ;




    public CustomAdaptar(Context context,ArrayList bookedSeat){
        this.context = context ;
        displaySeats = new ArrayList<>();
        bookedSeats = new ArrayList<>();
        this.bookedSeats = bookedSeat ;




        if(bookedSeat.size() > 0) {
            for (int i = 0; i < bookedSeats.size(); i++)
                displaySeats.add(bookedSeats.get(i));
        }






        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        convertView = layoutInflater.inflate(R.layout.layout,null);



        ImageView imageView = convertView.findViewById(R.id.layoutImageView);

        Log.i("Contains", String.valueOf(displaySeats.contains(position)));
        boolean flag = false ;

        /*for(int i = 0 ; i < displaySeats.size() ; i++){
            if(position == displaySeats.get(i)){
                flag = true ;
                Log.i("Flag",String.valueOf(flag));
                break;
            }
        }*/


        if(flag ){
            imageView.setImageResource(R.drawable.booked_seat);
        }

        else
        imageView.setImageResource(R.drawable.normal_seat);




        TextView textView = convertView.findViewById(R.id.layoutTextView);
        textView.setText(String.valueOf(position + 1));


        convertView.setMinimumHeight(150);



        return convertView;
    }


    public void getDataFromFirebase(){

        final TaskCompletionSource<DataSnapshot> dbsource = new TaskCompletionSource<>();
        Task dbtask = dbsource.getTask();


        DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookedSeats = (ArrayList<Integer>) dataSnapshot.child("Rohit Munjal").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       return;
    }
}
