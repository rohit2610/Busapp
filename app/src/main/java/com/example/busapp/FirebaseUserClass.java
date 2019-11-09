package com.example.busapp;

import java.util.ArrayList;

public class FirebaseUserClass {

    public String time ;
    public String date ;
    public ArrayList<Integer> bookedSeats = new ArrayList<>() ;

    public FirebaseUserClass(){

    }

    public FirebaseUserClass(ArrayList<Integer> bookedSeat){

        this.bookedSeats = bookedSeat ;


    }

    public ArrayList<Integer> getBookedSeats() {
        return bookedSeats;
    }
}
