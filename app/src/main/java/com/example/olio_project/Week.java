package com.example.olio_project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Week {

    private LocalDate weekDate;                                             //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    private ArrayList<Day> days;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Week(LocalDate date){
            weekDate = date;
        }


    public LocalDate getWeekDate() {
        return weekDate;
    }

    public void addEmissionData(DataEntry emissionData){

    }

    public boolean isEmpty(){
        if(days.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
