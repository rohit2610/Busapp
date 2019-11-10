package com.example.busapp;

import java.util.ArrayList;

public class User {

    public String username ;
    public ArrayList<Integer> selectedSeats = new ArrayList<>() ;
    public String date ;
    public String time ;
    private String from ;

    public User(){

    }

    public User( ArrayList<Integer> seats,String time , String date,String from){


        this.from = from ;
        this.selectedSeats = seats ;
        this.date = date ;
        this.time = time ;
    }

    public User(ArrayList<Integer> seats){
        this.selectedSeats = seats ;
    }

    public ArrayList<Integer> getSelectedSeats() {
        return selectedSeats;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }
}
