package com.example.busapp.tab_slide;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.busapp.CustomListView;
import com.example.busapp.R;
import com.example.busapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Past extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<Integer> bookedSeats;
    private ArrayList<Integer> seats;
    private ArrayList<User> displaySeats;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private String username;
    private String time;
    private String date;
    private ArrayList<String> keys;
    private String from ;
    private Bundle args ;
    private String CURRENT_FRAGMENT  = "PAST";


    public Fragment_Past() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment__upcoming, container, false);
        args = getArguments();

        /*listView = rootView.findViewById(R.id.upcomingListView);
        arrayAdapter = new ArrayAdapter(getActivity() ,android.R.layout.simple_list_item_1,displaySeats);

        bookedSeats = new ArrayList<>();
        displaySeats = new ArrayList<>();
        displaySeats.add(10);
        listView.setAdapter(arrayAdapter);*/

        listView = rootView.findViewById(R.id.upcomingListView);
        Button cancelButton = rootView.findViewById(R.id.deleteCustomListViewButton);


        bookedSeats = new ArrayList<>();
        displaySeats = new ArrayList<User>();
        seats = new ArrayList<>();
        keys = new ArrayList<>();









        username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(keys.size() == 0)
                    keys = new ArrayList<>();

                final CustomListView customListView = new CustomListView(getContext(), displaySeats,keys,CURRENT_FRAGMENT);
                listView.setAdapter(customListView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                {
                    User user = dataSnapshot.getValue(User.class);
                    Log.i("USer", user.toString());
                    Log.i("Key",dataSnapshot.getKey());
                    keys.add(dataSnapshot.getKey());
                    Log.i("Time", user.getTime());

                    DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
                    String dateAsString = user.getDate() +" " + user.getTime();
                    try {
                        Date date = sourceFormat.parse(dateAsString);
                        Log.i("DAte",date.toString());

                        boolean k  = new Date().after(date);
                        if(k){
                            displaySeats.add(user);

                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    //Log.i("DisplaySeats", displaySeats.get(0).toString());


                }
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
        });

        return  rootView ;
    }

}
