package com.example.olio_project;

import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Week {

    private LocalDate weekStartDate;                                             //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    private ArrayList<Day> days;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Week(LocalDate date){
        weekStartDate = date;

        while(weekStartDate.getDayOfWeek().toString()!="MONDAY"){
            weekStartDate = weekStartDate.minusDays(1);
        }
        System.out.println(weekStartDate.getDayOfWeek().toString());


        days = new ArrayList<Day>();
        for (int i = 0; i < 7; i++) {
            days.add(new Day(i,weekStartDate));
        }
        }

    public LocalDate getWeekDate() {
        return weekStartDate;
    }

    public double getWeeksEmissions(){
        double totalEmissions = 0;
        for (int i = 0; i < days.size(); i++) {
            totalEmissions = days.get(i).getDaysEmissions();
        }
        return totalEmissions;
    }

    public Day getDay(int dayIndex){
        return days.get(dayIndex);
    }

   public int getWeeksCalories(){
        int calories = 0;
        return calories;
   }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addEmissionData(DataEntry emissionData, LocalDate date){
        Day currentDay = days.get(date.getDayOfWeek().getValue()-1);
        currentDay.addDataEntryToDay(emissionData);
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
