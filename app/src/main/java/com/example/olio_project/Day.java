package com.example.olio_project;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Day {

    private LocalDate date;                                             //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    private ArrayList<DataEntry> entries;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Day(int index, LocalDate weekStartDay){
        date = weekStartDay.plusDays(index);
        entries = new ArrayList<DataEntry>();
    }

    public ArrayList<DataEntry> getEntries(){
        return entries;
    }

    public double getDaysEmissions(){
        double dayEmissions = 0;

        for (int i = 0; i < entries.size(); i++) {
            dayEmissions = dayEmissions+entries.get(i).getMeatEmissions()+entries.get(i).getPlantEmissions()+entries.get(i).getDairyEmissions();
            System.out.println(dayEmissions);
        }

        return dayEmissions;
    }

    public void addDataEntryToDay(DataEntry newEntry){
        entries.add(newEntry);
    }

    public boolean isEmpty(){
        if(entries.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
}

