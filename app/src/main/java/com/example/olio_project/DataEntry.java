package com.example.olio_project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DataEntry {
    private double dairyEmissions;
    private double meatEmissions;
    private double plantEmissions;


    private LocalDateTime entryDate;

    public DataEntry(double dairy, double meat, double plant, LocalDateTime date){
        dairyEmissions = dairy;
        meatEmissions = meat;
        plantEmissions = plant;
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


