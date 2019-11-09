package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.busapp.tab_slide.Fragment_Upcoming;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<User>  {
    LayoutInflater layoutInflater ;
    Context context ;
    ArrayList<User> user ;
    ArrayList<String> keys ;
    ArrayList<Integer> seats = new ArrayList<>();
    ArrayList<Integer> bookedSeats2  ;

    String date ;
    String time ;
    public CustomListView(@NonNull Context context,ArrayList<User> data,ArrayList<String> key) {
        super(context,R.layout.activity_custom_list_view,data);

        keys = new ArrayList<>();
        user = new ArrayList<>();
        this.context = context ;
        this.user = data ;
        this.keys = key ;
        layoutInflater = LayoutInflater.from(context);

    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final User user2 = user.get(position);



        convertView = layoutInflater.inflate(R.layout.activity_custom_list_view,null);


        final TextView dateTextView = convertView.findViewById(R.id.customListViewDateTextView);
        TextView timeTextView = convertView.findViewById(R.id.cutomListViewTimrTextView);
        final TextView bookedSeats = convertView.findViewById(R.id.customListViewBookedSeatsTextView);




        dateTextView.setText(user2.getDate());
        timeTextView.setText(user2.getTime());
        bookedSeats.setText(user2.getSelectedSeats().toString());
        bookedSeats2 = new ArrayList<>();

        Button cancelButton = convertView.findViewById(R.id.deleteCustomListViewButton);


       cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                        .child(keys.get(position));

                 reference.setValue(null);

                date = user.get(position).getDate();
                time = user.get(position).getTime();
                seats = user.get(position).getSelectedSeats();

                user.remove(position);
                Log.i("Date",date);




               DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Booked Seats").child(date).child(time);
               reference1.addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                       Log.i("DataSnapShoot",dataSnapshot.getValue().toString());
                       bookedSeats2 = (ArrayList<Integer>) dataSnapshot.getValue();


                       for(int i = 0 ; i < seats.size();i++){
                           bookedSeats2.remove(Long.valueOf(seats.get(i).toString()));

                       }

                       Log.i("SEats",seats.toString());
                       //bookedSeats2.removeAll(seats);
                       FirebaseUserClass userClass = new FirebaseUserClass(bookedSeats2);
                       Log.i("1",bookedSeats2.toString());

                       dataSnapshot.getRef().setValue(userClass.getBookedSeats());





                   }

                   @Override
                   public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   }

                   @Override
                   public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                   }

                   @Override
                   public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               })  ;


               /*reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               dataSnapshot.getRef().setValue(bookedSeats2);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

               /*DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Booked Seats");
                reference2.child(date).child(time);



                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BusSeat.FirebaseUserClass userClass = dataSnapshot.getValue(BusSeat.FirebaseUserClass.class);

                        bookedSeats2 = userClass.getBookedSeats();
                        bookedSeats2.remove(seats);
                        Log.i("SEARS",bookedSeats2.toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/









            }
        });








        return convertView;
    }
}
