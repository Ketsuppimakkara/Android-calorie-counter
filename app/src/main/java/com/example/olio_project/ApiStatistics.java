package com.example.olio_project;

import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Math.round;

public class ApiStatistics {

    private String apiUrl = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore";

    private int averageFinnishBeefGramsPerDay = 57;                                               // Used https://ilmastodieetti.ymparisto.fi/ilmastodieetti/food as source for averages
    private int averageFinnishFishGramsPerDay = 86;
    private int averageFinnishPorkPoultryGramsPerDay = 143;

    private int beefGrams;
    private int fishGrams;
    private int porkPoultryGrams;


    public ApiStatistics(){
    }


    @RequiresApi(api = Build.VERSION_CODES.O)                                                       // ReadXml required newer api, check if this is problem
    public void addNewEmissionEntry(int beefInGrams, int fishInGrams, int porkPoultryInGrams) throws IOException, JSONException {             //Update api call URL based on grams of food
        if(beefInGrams != 0){
            double beefMultiplier = beefInGrams/(averageFinnishBeefGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            System.out.println("###########"+beefMultiplier);
            if(beefMultiplier < 1) {
                apiUrl = apiUrl + "&query.beefLevel=" + (int) (((float) beefInGrams / (float) averageFinnishBeefGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.beefLevel=200";
            }
            beefGrams = beefInGrams;
        }
        if(fishInGrams != 0){
            double fishMultiplier = fishInGrams/(averageFinnishFishGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if(fishMultiplier < 1) {
                apiUrl = apiUrl + "&query.fishLevel=" + (int) (((float) fishInGrams / (float) averageFinnishFishGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.fishLevel=200";
            }
            fishGrams = fishInGrams;
        }
        if(porkPoultryInGrams != 0){
            double porkPoultryMultiplier = porkPoultryInGrams/(averageFinnishPorkPoultryGramsPerDay*2);            //This multiplier is a dirty workaround of the fact that Ilmastodieetti does not allow you to log more than  2x the average grams of food at one time.
            if(porkPoultryMultiplier < 1) {
                apiUrl = apiUrl + "&query.porkPoultryLevel=" + (int) (((float) porkPoultryInGrams / (float) averageFinnishPorkPoultryGramsPerDay) * 100);
            }
            else{
                apiUrl = apiUrl + "&query.porkPoultryLevel=200";
            }
            porkPoultryGrams = porkPoultryInGrams;
        }

        System.out.println(apiUrl);
        JSONObject data = JsonReader.readJsonFromUrl(apiUrl);                                       //Read ilmastodieetti's API response into a single string

        double dairyEmission = ((double) data.get("Dairy")-24.6281058495822)/365;                                           // Extract floats from response. Subtract default values to only count emissions caused by food
        double meatEmission = ((double) data.get("Meat")-33.3533426183844)/365;                                             // These also divide the data by 365 since Ilmastodieetti returns emission data for the whole year instead of one day
        double plantEmission = ((double) data.get("Plant")-340.159042085224)/365;
        DataEntry emissionData = (new DataEntry(dairyEmission,meatEmission,plantEmission, LocalDate.now()));          // Create a new DataEntry with calculated emissionData.
        emissionData.printAllEmissions();
    }
}
