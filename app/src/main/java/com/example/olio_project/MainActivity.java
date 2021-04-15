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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;


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

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newEntry(View v){
        try {
            DataEntry emissionData = user.userData.createNewEmissionEntry(1000,200,3000); //TODO go through createNewEmissionentry and make it so that weeklist is handled here instead
            user.userData.getWeekList().get(user.userData.getCurrentWeekIndex()).getDay(LocalDate.now().getDayOfWeek().getValue()).addDataEntryToDay(emissionData);
            System.out.println("New data added! Week starting at:"+user.userData.getWeekList().get(0).getWeekDate().toString()+" MeatEmissions for new entry: "+user.userData.getWeekList().get(0).getDay(3).getEntries().get(0).getMeatEmissions());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void printEmissionData(View v){
        emissions.setText((int) user.userData.getWeekList().get(user.userData.getCurrentWeekIndex()).getDay(LocalDate.now().getDayOfWeek().getValue()).getDaysEmissions()+"");
    }

}

// Tommi push