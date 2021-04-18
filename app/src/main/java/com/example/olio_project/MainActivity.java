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

    UserData userData;
    Context context = null;
    User user = null;
    TextView emissions;

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
        // Intent comes from LoginActivity after successful login, includes index of current user from userList.
        Intent intent = getIntent();
        intent.getExtras();
        int userIndex = intent.getIntExtra("Index",0);
        user = userList.get(userIndex);
        System.out.println("Got user data! User name:"+user.userName);
        readWeekListFromFile();
        try {
            printEmissionData();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newEntry(View v){
        try {
            readWeekListFromFile();
            DataEntry emissionData = user.userData.createNewEmissionEntry(1000,200,3000,5000,600,5000);
            //user.userData.getWeekList().get(user.userData.getCurrentWeekIndex()).getDay(LocalDate.now().getDayOfWeek().getValue()).addDataEntryToDay(emissionData);
            System.out.println("New data added! Week starting at:"+user.userData.getWeekList().get(0).getWeekDate().toString()+" Total Emissions for new entry: "+user.userData.getWeekList().get(0).getDay(LocalDate.now().getDayOfWeek().getValue()-1).getEntries().get(0).getMeatEmissions()+user.userData.getWeekList().get(0).getDay(LocalDate.now().getDayOfWeek().getValue()-1).getEntries().get(0).getDairyEmissions()+user.userData.getWeekList().get(0).getDay(LocalDate.now().getDayOfWeek().getValue()-1).getEntries().get(0).getPlantEmissions());
            printEmissionData();
            saveWeekListToFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printEmissionData() throws IOException, JSONException {
        double thisWeeksEmissions = 0;

        for (int i = 0; i < 7; i++) {
            thisWeeksEmissions = thisWeeksEmissions + (user.userData.getWeekList().get(user.userData.getCurrentWeekIndex()).getDay(i).getDaysEmissions());
            System.out.println("Week's total emissions are thus far: "+thisWeeksEmissions);
        }
        emissions.setText(String.format("%.2f",thisWeeksEmissions)+" kg/CO2");
    }

    public void readWeekListFromFile(){
        try{
                try {
                    FileInputStream fis = context.openFileInput(user.userName+"_Log.txt");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Object object = ois.readObject();
                    if (object != null) {
                        user.userData.setWeekList((ArrayList<Week>)object);
                        //System.out.println("User found! Emissions from today: "+user.userData.getWeekList().get(0).getDay(4).getDaysEmissions());
                    } else {

                    }
                    ois.close();
                    fis.close();
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

    }

    public void saveWeekListToFile(){
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(user.userName+"_Log.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(user.userData.getWeekList());
                System.out.println("Wrote weeklist to user'sfile.");
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

}

// Tommi push