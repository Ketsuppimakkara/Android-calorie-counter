/*
Object oriented programming project work

testaus Akseli
Lassi Laukkarinen, Akseli Hulkkonen, Tommmi Paakkunainen
*/

package com.example.olio_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



public class MainActivity extends AppCompatActivity {


    UserData userData;
    Context context = null;

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

        //Read file, get users


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