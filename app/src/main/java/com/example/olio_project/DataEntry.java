package com.example.olio_project;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DataEntry implements Serializable {
    private double dairyEmissions;
    private double meatEmissions;
    private double plantEmissions;

    private double dairyCalories;
    private double meatCalories;
    private double plantCalories;


    private LocalDateTime entryDate;

    public DataEntry(double dairyCo2, double meatCo2, double plantCo2, double meatCal, double dairyCal, double plantCal, LocalDateTime date){
        dairyEmissions = dairyCo2;
        meatEmissions = meatCo2;
        plantEmissions = plantCo2;
        dairyCalories = dairyCal;
        meatCalories = meatCal;
        plantCalories = plantCal;

        entryDate = date;
    }



    public double getMeatEmissions() {
        return meatEmissions;
    }
    public double getDairyEmissions(){
        return dairyEmissions;
    }
    public double getPlantEmissions() {
        return plantEmissions;
    }

    public double getMeatCalories() {
        return meatCalories;
    }
    public double getDairyCalories(){
        return dairyCalories;
    }
    public double getPlantCalories() {
        return plantCalories;
    }

    public void setMeatEmissions(float emissions){
        meatEmissions = emissions;
    }

    public void setDairyEmissionsEmissions(float emissions){
        dairyEmissions = emissions;
    }

    public void setPlantEmissions(float emissions){
        plantEmissions = emissions;
    }

    public void printAllEmissions(){
        System.out.println("Dairy: "+dairyEmissions+" Meat: "+meatEmissions+" Plant: "+plantEmissions);
    }
}


