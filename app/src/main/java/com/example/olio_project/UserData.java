package com.example.olio_project;

import android.content.Context;
import android.os.Build;
import android.os.health.SystemHealthManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;


public class UserData implements Serializable {

    private ArrayList<Week> weekList;

    private String apiUrl = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore";

    private int averageFinnishBeefGramsPerDay = 57;                                               // Used https://ilmastodieetti.ymparisto.fi/ilmastodieetti/food as source for averages
    private int averageFinnishFishGramsPerDay = 86;
    private int averageFinnishPorkPoultryGramsPerDay = 143;
    private int averageFinnishDairyGramsPerDay = 543;
    private int averageFinnishCheeseGramsPerDay = 42;
    private int averageFinnishPlantGramsPerDay = 200;

    private int beefGrams;
    private int fishGrams;
    private int porkPoultryGrams;
    private int dairyGrams;
    private int cheeseGrams;
    private int plantGrams;

    private double beefMultiplier;
    private double fishMultiplier;
    private double porkPoultryMultiplier;
    private double meatMultiplier;
    private double dairyMultiplier;
    private double cheeseMultiplier;
    private double plantMultiplier;

    private double beefCalories;
    private double fishCalories;
    private double porkPoultryCalories;
    private double dairyCalories;
    private double cheeseCalories;
    private double riceCalories;
    private double eggCalories;
    private double plantCalories;
    private int totalCalories;



    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");


    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserData(){
        weekList = new ArrayList<Week>();
        weekList.add(new Week(LocalDate.now()));
    }

    public void setWeekList(ArrayList<Week> newWeekList) {
        System.out.println("setWeeklist");
        if(weekList != null) {
            weekList.clear();
        }
        weekList = newWeekList;
    }

    public ArrayList<Week> getWeekList() {
        System.out.println("getWeekList");
        return weekList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getCurrentWeekIndex(){
        System.out.println("getCurrentWeekIndex");
        int weekIndex = 0;
        LocalDate thisWeeksMonday = LocalDate.now();
        for (int i = 0; i < 7 && thisWeeksMonday.getDayOfWeek().toString() != "MONDAY"; i++) {
            thisWeeksMonday = thisWeeksMonday.minusDays(i);
        }
        System.out.println("Weeklist's size is "+weekList.size());
        for (int i = 0; i < weekList.size(); i++) {
            System.out.println("Weeklists' "+i+". monday is on :"+weekList.get(i).getWeekDate()+"\nWe are looking for "+thisWeeksMonday);
            if(weekList.get(i).getWeekDate().equals(thisWeeksMonday)){
                weekIndex = i;
                System.out.println("Found a matching week, index: "+weekIndex);
                break;
            }
        }
        return weekIndex;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)                                                       // ReadXml required newer api, check if this is problem
    public DataEntry createNewEmissionEntry(int beefInGrams, int fishInGrams, int porkPoultryInGrams, int dairyInGrams, int cheeseInGrams, int plantInGrams, LocalDateTime date) throws IOException, JSONException {             //Update api call URL based on grams of food
        System.out.println("createNewEmissionEntry");
        apiUrl = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore";
        if(beefInGrams != 0){
            beefMultiplier = beefInGrams/(averageFinnishBeefGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if(beefMultiplier < 1) {
                apiUrl = apiUrl + "&query.beefLevel=" + (int) (((float) beefInGrams / (float) averageFinnishBeefGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.beefLevel=200";
            }
            beefGrams = beefInGrams;
        }
        if(fishInGrams != 0){
            fishMultiplier = fishInGrams/(averageFinnishFishGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if(fishMultiplier < 1) {
                apiUrl = apiUrl + "&query.fishLevel=" + (int) (((float) fishInGrams / (float) averageFinnishFishGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.fishLevel=200";
            }
            fishGrams = fishInGrams;
        }
        if(porkPoultryInGrams != 0){
            porkPoultryMultiplier = porkPoultryInGrams/(averageFinnishPorkPoultryGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if(porkPoultryMultiplier < 1) {
                apiUrl = apiUrl + "&query.porkPoultryLevel=" + (int) (((float) porkPoultryInGrams / (float) averageFinnishPorkPoultryGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.porkPoultryLevel=200";
            }
            porkPoultryGrams = porkPoultryInGrams;
        }
        if(dairyInGrams != 0) {
            dairyMultiplier = dairyInGrams / (averageFinnishDairyGramsPerDay * 2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if (dairyMultiplier < 1) {
                apiUrl = apiUrl + "&query.dairyLevel=" + (int) (((float) dairyInGrams / (float) averageFinnishDairyGramsPerDay) * 100);
            } else {
                apiUrl = apiUrl + "&query.dairyLevel=200";
            }
            dairyGrams = dairyInGrams;
        }

        if(cheeseInGrams != 0) {
            cheeseMultiplier = cheeseInGrams / (averageFinnishCheeseGramsPerDay * 2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if (cheeseMultiplier < 1) {
                apiUrl = apiUrl + "&query.cheeseLevel=" + (int) (((float) cheeseInGrams / (float) averageFinnishCheeseGramsPerDay) * 100);
            } else {
                apiUrl = apiUrl + "&query.cheeseLevel=200";
            }
            cheeseGrams = cheeseInGrams;
        }

        if(plantInGrams != 0) {
            plantMultiplier = plantInGrams / (averageFinnishPlantGramsPerDay * 2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if (plantMultiplier < 1) {
                apiUrl = apiUrl + "&query.plantLevel=" + (int) (((float) plantInGrams / (float) averageFinnishPlantGramsPerDay) * 100);
            } else {
                apiUrl = apiUrl + "&query.plantLevel=200";
            }
            plantGrams = plantInGrams;
        }

        System.out.println(apiUrl);
        JSONObject data = JsonReader.readJsonFromUrl(apiUrl);                                       //Read ilmastodieetti's API response into a single string

        if(beefMultiplier > 1 || fishMultiplier > 1 || porkPoultryMultiplier > 1) {
            meatMultiplier = ((beefInGrams * beefMultiplier) + (fishInGrams * fishMultiplier) + (porkPoultryInGrams * porkPoultryMultiplier)) / (beefInGrams + fishInGrams + porkPoultryInGrams);
        }
        else{
            meatMultiplier = 1;
        }

        if(dairyMultiplier > 1 || cheeseMultiplier > 1) {
            dairyMultiplier = ((dairyInGrams * dairyMultiplier) + (cheeseInGrams * cheeseMultiplier)) / (dairyInGrams + cheeseInGrams);
        }
        else{
            dairyMultiplier = 1;
        }

        double dairyEmission = ((double) data.get("Dairy")-24.6281058495822)/365*dairyMultiplier;                                           // Extract floats from response. Subtract default values to only count emissions caused by food
        double meatEmission = (((double) data.get("Meat")-33.3533426183844)/365)*meatMultiplier;                            // These also divide the data by 365 since Ilmastodieetti returns emission data for the whole year instead of one day
        double plantEmission = ((double) data.get("Plant")-340.159042085224)/365;                                           // Meatmultiplier counts a weighted average of inputted meats and calculates a multiplier to estimate emissions

        double dairyCalories = ((dairyInGrams * 0.97)+(cheeseInGrams*2.72));
        double meatCalories = ((beefInGrams*2.09)+(fishInGrams*1.27)+(porkPoultryInGrams*2.16));
        double plantCalories = (plantInGrams*0.016);

        DataEntry emissionData = (new DataEntry(dairyEmission,meatEmission,plantEmission,dairyCalories,meatCalories,plantCalories,LocalDateTime.now()));            // Create a new DataEntry with calculated emissionData.
        System.out.println("MeatEmissions from API: "+emissionData.getMeatEmissions());
        System.out.println("DairyEmissions from API: "+emissionData.getDairyEmissions());
        System.out.println("PlantEmissions from API: "+emissionData.getPlantEmissions());
        // ##################################################

            weekList.get(0).addEmissionData(emissionData,LocalDate.now());

        return emissionData;
        }

    public int calculateCalories(int beefInGrams, int fishInGrams, int porkPoultryInGrams, int dairyInGrams, int cheeseInGrams,int riceInGrams,int eggInGrams, int plantInGrams) {
        if (beefInGrams != 0) {
            beefCalories = beefInGrams * 2.09;
            totalCalories += beefCalories;
        }
        if(fishInGrams != 0){
            fishCalories = fishInGrams * 1.27;
            totalCalories += fishCalories;
        }
        if (porkPoultryInGrams != 0){
            porkPoultryCalories = porkPoultryInGrams * 2.16;
            totalCalories += porkPoultryCalories;
        }
        if (dairyInGrams != 0){
            dairyCalories = dairyInGrams * 0.97;
            totalCalories += dairyCalories;
        }
        if (cheeseInGrams != 0){
            cheeseCalories = cheeseInGrams * 2.72;
            totalCalories += cheeseCalories;
        }
        if (riceInGrams != 0){
            riceCalories = riceInGrams * 3.48;
            totalCalories += riceCalories;
        }
        if (eggInGrams != 0){
            eggCalories = eggInGrams * 1.34;
            totalCalories += eggCalories;
        }
        if (plantInGrams != 0){
            plantCalories = plantInGrams * 0.016;
            totalCalories += plantCalories;
        }
        return totalCalories;
    }

    }

