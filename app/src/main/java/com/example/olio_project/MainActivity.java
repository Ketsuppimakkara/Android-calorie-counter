/*
Object oriented programming project work

testaus Akseli
Lassi Laukkarinen, Akseli Hulkkonen, Tommmi Paakkunainen
*/

package com.example.olio_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class MainActivity extends LoginActivity {

    Context context = null;
    User user = null;
    TextView emissions;
    TextView calories;
    TextView dayCalories;
    int userIndex ;

    @RequiresApi(api = Build.VERSION_CODES.O)                                                       // updateEmissionUrl required newer api, check if this is problem
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Settings
        //Android settings out of strict mode. This is to connect to ilmastodieetti.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = MainActivity.this;
        // Program
        emissions = findViewById(R.id.emission);
        calories = findViewById(R.id.weekleyCalorieCount);
        dayCalories = findViewById(R.id.dailyCalorieCount);
        // Intent comes from LoginActivity after successful login, includes index of current user from userList.
        Intent intent = getIntent();
        intent.getExtras();
            try {

                userIndex = intent.getIntExtra("Index", 0);
                user = userList.get(userIndex);
                System.out.println("Got user data! User name:" + user.userName);
                readWeekListFromFile();
                System.out.println(user.userData.getWeekList().size());
                try {
                    printEmissionData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            readWeekListFromFile();
            user.userData.createNewEmissionEntry(intent.getIntExtra("beef",0),intent.getIntExtra("Fish",0),intent.getIntExtra("Pork",0),intent.getIntExtra("Dairy",0),intent.getIntExtra("Cheese",0),intent.getIntExtra("Salad",0),LocalDateTime.now());
            //user.userData.getWeekList().get(user.userData.getCurrentWeekIndex()).getDay(LocalDate.now().getDayOfWeek().getValue()).addDataEntryToDay(emissionData);
            printEmissionData();
            printCalorieData();
            printDayCalorieData(LocalDate.now());
            saveWeekListToFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newEntry(View v){
        System.out.println("newEntry");
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("Index",userIndex);
        System.out.println("###USERINDEX in mainactivity:"+userIndex);
        startActivity(intent);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printCalorieData() throws IOException, JSONException {
        System.out.println("printEmissionData");
        double thisWeeksCalories = 0;

        if(user.userData.getWeekList().size()==0){
            calories.setText("0 kCal");
        }
        else {
            //comment
            for (int i = 0; i < 7; i++) {
                thisWeeksCalories = thisWeeksCalories + (user.userData.getWeekList().get(0).getDay(i).getDaysCalories());
                System.out.println("Week's total calories are thus far: " + thisWeeksCalories);
            }
            calories.setText((int)(thisWeeksCalories) + " kCal");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printDayCalorieData(LocalDate date) throws IOException, JSONException {
        System.out.println("printDayCalorieData");

        if(user.userData.getWeekList().size()==0){
            calories.setText("0 kCal");
        }
        else {
                for (int j = 0; j < 7; j++) {
                    if(user.userData.getWeekList().get(0).getDay(j).getDate().equals(date)) {
                        double calories = user.userData.getWeekList().get(0).getDay(j).getDaysCalories();
                        System.out.println(calories);
                        int daysCalories = (int)calories;
                        dayCalories.setText(Integer.toString(daysCalories)+" kCal");
                        break;
                    }
                }
        }
    }

    public void goToGraph(View v){
        Intent graphIntent = new Intent(this,GraphActivity.class);
        graphIntent.putExtra("currentWeek",user.userData.getWeekList().get(0));
        graphIntent.putExtra("Index", userIndex);
        startActivity(graphIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printEmissionData() throws IOException, JSONException {
        System.out.println("printEmissionData");
        double thisWeeksEmissions = 0;

        if(user.userData.getWeekList().size()==0){
            emissions.setText("0 kg/CO2");
        }
        else {
            for (int i = 0; i < 7; i++) {
                thisWeeksEmissions = thisWeeksEmissions + (user.userData.getWeekList().get(0).getDay(i).getDaysEmissions());
                System.out.println("Week's total emissions are thus far: " + thisWeeksEmissions);
            }
            emissions.setText(String.format("%.2f", thisWeeksEmissions) + " kg/CO2");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Week> readWeekListFromFile(){
        System.out.println("readWeekListFromFile");
        try{
                try {
                    FileInputStream fis = context.openFileInput(user.userName+"_Log.txt");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Object object = ois.readObject();
                    if (object != null) {
                        user.userData.setWeekList((ArrayList<Week>)object);
                    }
                    ois.close();
                    fis.close();

                    LocalDate thisWeek = getWeeksMonday(LocalDate.now());
                    System.out.println("#################### ThisWeek "+thisWeek);
                    if(user.userData.getWeekList().size()!=0) {
                        System.out.println("#################### Compare these "+thisWeek+"###################"+user.userData.getWeekList().get(0).getWeekDate());
                        if (user.userData.getWeekList().get(0).getWeekDate().equals(thisWeek) == false) {
                            user.userData.getWeekList().add(0, new Week(LocalDate.now()));
                            System.out.println("This week not found in weeklist, adding it now.");
                            saveWeekListToFile();
                        }
                    }
                    else {
                        user.userData.getWeekList().add(0, new Week(LocalDate.now()));
                        System.out.println("No weeks found in weeklist, adding one now.");
                        saveWeekListToFile();
                    }
                    return (ArrayList<Week>) object;
                }
                catch (EOFException e){
                    System.out.println("File read! Found users:");
                    for (int i = 0; i < userList.size(); i++) {
                        System.out.println(userList.get(i).userName);
                    }
                }

        }
        //Do this if no file exists
        catch(FileNotFoundException e){
            Log.e("FileNotFoundException","File not found, creating a new file!");
            saveWeekListToFile();
        }
        catch(IOException e){
            Log.e("IOException",e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("IOException","Class not found");
        }
        return(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveWeekListToFile(){
        System.out.println("saveWeekListToFile");
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(user.userName+"_Log.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            if(user.userData.getWeekList() != null) {
                oos.writeObject(user.userData.getWeekList());
                System.out.println("Wrote weeklist to user'sfile.");
            }
            else{
                user.userData.setWeekList(new ArrayList<Week>());
                user.userData.getWeekList().add(0,new Week(LocalDate.now()));
                oos.writeObject(user.userData.getWeekList());
            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e){
            Log.e("FileNotFoundException","File not found");
        }
        catch(IOException e){
            Log.e("IOException","Error in input");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getWeeksMonday(LocalDate date){
        System.out.println("getWeeksMonday");
        for (int i = 0; i < 7 && date.getDayOfWeek().toString() != "MONDAY"; i++) {
            System.out.println("############## dates"+date.getDayOfWeek().toString());
            date = date.minusDays(1);
        }
        return date;
    }

}

