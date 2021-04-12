/*
Object oriented programming project work

testaus Akseli
Lassi Laukkarinen, Akseli Hulkkonen, Tommmi Paakkunainen
*/

package com.example.olio_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    UserData userData;

    @RequiresApi(api = Build.VERSION_CODES.O)                                                       // updateEmissionUrl required newer api, check if this is problem
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Settings
        //Android settings out of strict mode. This is to connect to ilmastodieetti.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Program

        User user = new User("Ketsuppimakkara","1234");           //Replace with loading user from file, if file doesn't exist, create new file


        try {
            user.userData.addNewEmissionEntry(300,150,100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

// Tommi push