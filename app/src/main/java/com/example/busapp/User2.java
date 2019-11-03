package com.example.busapp;

import java.util.ArrayList;

public class User2 {

    public String time;
    public String date ;
    public ArrayList<Integer> bookedSeats = new ArrayList<>();

    public User2() {
    }

    public User2(String time, String date , ArrayList<Integer> seats){


        this.time = time ;
        this.date = date;
        this.bookedSeats = seats ;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Integer> getBookedSeats() {
        return bookedSeats;
    }
}
