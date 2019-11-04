package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<User>  {
    LayoutInflater layoutInflater ;
    Context context ;
    ArrayList<User> user ;

    public CustomListView(@NonNull Context context,ArrayList<User> data) {
        super(context,R.layout.activity_custom_list_view,data);

        user = new ArrayList<>();
        this.context = context ;
        this.user = data ;
        layoutInflater = LayoutInflater.from(context);

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user2 = user.get(position);

        convertView = layoutInflater.inflate(R.layout.activity_custom_list_view,null);

        TextView dateTextView = convertView.findViewById(R.id.customListViewDateTextView);
        TextView timeTextView = convertView.findViewById(R.id.cutomListViewTimrTextView);
        TextView bookedSeats = convertView.findViewById(R.id.customListViewBookedSeatsTextView);

        dateTextView.setText(user2.getDate());
        timeTextView.setText(user2.getTime());
        bookedSeats.setText(user2.getSelectedSeats().toString());





        return convertView;
    }
}
