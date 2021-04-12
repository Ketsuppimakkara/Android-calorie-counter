package com.example.olio_project;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Day {

    private LocalDate date;                                             //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    private ArrayList<DataEntry> entries;

    public boolean isEmpty(){
        if(entries.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
}

