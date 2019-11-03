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
    int[] k ;



    public CustomAdaptar(Context context,ArrayList<Integer> seats){
        this.context = context ;
        displaySeats = new ArrayList<>();
        bookedSeats = new ArrayList<>();

        k = new int[seats.size()];
        Log.i("Seats",String.valueOf(seats));
        this.bookedSeats = seats ;
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




        Log.i("2",String.valueOf(bookedSeats));

        ImageView imageView = convertView.findViewById(R.id.layoutImageView);


        boolean flag = false ;

        Object seat = position + 1;



        flag = bookedSeats.contains(seat);

          Log.i("type", String.valueOf(bookedSeats.contains(seat)));

           Log.i("Flag",String.valueOf(flag));



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
