package com.example.olio_project;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DataEntry implements Serializable {
    private double dairyEmissions = 0;
    private double meatEmissions = 0;
    private double plantEmissions = 0;

    double dairyCalories = 0;
    double meatCalories = 0;
    double plantCalories = 0;


    private LocalDateTime entryDate;

    public DataEntry(double dairy, double meat, double plant, double dairyCal, double meatCal, double plantCal, LocalDateTime date){
        dairyEmissions = dairy;
        meatEmissions = meat;
        plantEmissions = plant;
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

    public double getMeatCalories(){return meatCalories;}
    public double getDairyCalories(){return dairyCalories;}
    public double getPlantCalories(){return plantCalories;}

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


