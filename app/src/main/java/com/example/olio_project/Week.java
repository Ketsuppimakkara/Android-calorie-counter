package com.example.olio_project;

import java.time.LocalDate;
import java.util.ArrayList;

public class Week {
    private LocalDate date;                                             //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    private ArrayList<Day> days;

    public boolean isEmpty(){
        if(days.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
