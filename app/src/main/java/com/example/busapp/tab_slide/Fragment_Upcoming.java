package com.example.busapp.tab_slide;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.busapp.CustomAdaptar;
import com.example.busapp.CustomListView;
import com.example.busapp.R;
import com.example.busapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Upcoming extends Fragment {

    private DatabaseReference databaseReference ;
    private ArrayList<Integer> bookedSeats ;
    private ArrayList<User> displaySeats ;
    private ListView listView ;
    private ArrayAdapter arrayAdapter ;
    private String username ;
    private String  time ;
    private String date;
    private ArrayList<User> displayBooking ;



    public Fragment_Upcoming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment__upcoming, container, false);
        /*listView = rootView.findViewById(R.id.upcomingListView);
        arrayAdapter = new ArrayAdapter(getActivity() ,android.R.layout.simple_list_item_1,displaySeats);

        bookedSeats = new ArrayList<>();
        displaySeats = new ArrayList<>();
        displaySeats.add(10);
        listView.setAdapter(arrayAdapter);*/

        listView = rootView.findViewById(R.id.upcomingListView);


        bookedSeats = new ArrayList<>();
        displaySeats = new ArrayList<User>();




        username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot postDataSnapshot : dataSnapshot.getChildren()){
                   User user = postDataSnapshot.getValue(User.class);
                   Log.i("USer",user.toString());
                   Log.i("Time",user.getTime());
                   displaySeats.add(user);
                   Log.i("DisplaySeats",displaySeats.get(0).toString());



               }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Runnable r = new Runnable() {
            @Override
            public void run() {
                final CustomListView customListView = new CustomListView(getContext(),displaySeats);
                listView.setAdapter(customListView);
            }
        };

       new Handler().postDelayed(r,5000);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bookedSeats = (ArrayList<Integer>) dataSnapshot.getValue();

                        for(int i = 0 ; i < bookedSeats.size() ;i++){
                            displaySeats.add(bookedSeats.get(i));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        },5000);*/


//        Log.i("Display List", Arrays.toString(bookedSeats.toArray()));

        return rootView;
    }

}
