package com.example.olio_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
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
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Context context = this;
    ArrayList<User> userList = new ArrayList<User>();

    EditText usernameField;
    EditText passwordField;
    Button newUserButton;

    int fileInitialized;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("CREATED");
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        newUserButton = findViewById(R.id.createUser);
        //See if login file exists on the phone already, if not, create one. NullPointerException detects corrupted data and deletes the offending file
        try {
                readFile();
                System.out.println(userList.size());
        }
        catch (NullPointerException e){
            deleteFile("users.txt");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RESUMED");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("PAUSED");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getThisWeeksMonday(){
        LocalDate thisWeeksMonday = LocalDate.now();

        while(thisWeeksMonday.getDayOfWeek().toString() != "MONDAY"){
            thisWeeksMonday = thisWeeksMonday.minusDays(1);
        }
        return (thisWeeksMonday);
    }

    public void login(View v){
        System.out.println("login");
        if(userList.size() == 0){
            System.out.println("Userlist size="+userList.size());
            Toast.makeText(context,"No users exist, create a new user!", Toast.LENGTH_SHORT).show();
        }
        else {
            int i = 0;
            for (i = 0; i < userList.size(); i++) {

                if(userList.get(i).userName.equals(usernameField.getText().toString()) == true && userList.get(i).password.equals(passwordField.getText().toString()) == true){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("Index",i);
                    startActivity(intent);
                    break;
                }
                else{
                    System.out.println("Userlistan nimi: "+userList.get(i).userName+" annettu nimi:"+usernameField.getText().toString());
                    System.out.println("Userlistan salasana: "+userList.get(i).password+" annettu salasana: "+passwordField.getText().toString());
                }

            }
            if(userList.size() == i){
                Toast.makeText(context,"Username or password is incorrect.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void addNewUser(View v){
        System.out.println("addNewUser");

        if(usernameField.getText().length() != 0 && passwordField.getText().length() != 0) {                                    //Check if user has inputted anything into both fields
            User newUser = new User(usernameField.getText().toString(), passwordField.getText().toString());
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).userName.equals(newUser.userName) == true){
                    System.out.println("Username already exists! Password in case you forgot: "+userList.get(i).password);
                    Toast.makeText(this,"Username already exists!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            userList.add(newUser);
            writeUserListToFile(userList);
            Toast.makeText(this,"New user added!",Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Username or password cannot be empty!");
            Toast.makeText(this,"Username or password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        readFile();
    }

    public void readFile(){
        System.out.println("readFile");
        if(userList.size() == 0){
        try{
            FileInputStream fis = context.openFileInput("users.txt");
            boolean cont = true;
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(cont){
                try {
                    Object object = ois.readObject();
                    if (object != null) {
                        System.out.println("User found");
                        userList.add((User) object);
                    }
                }
                catch (EOFException e){
                    cont = false;
                    fis.close();
                    System.out.println("File read! Found users:");
                    for (int i = 0; i < userList.size(); i++) {
                        System.out.println(userList.get(i).userName);
                    }
                }
            }
            ois.close();
            fis.close();
        }
        //Do this if no file exists
        catch(FileNotFoundException e){
            Log.e("FileNotFoundException","File not found, creating a new userfile!");
            //User newUser = new User("Ketsuppimakkara","1234");  //TODO Get username and password from UI
            //userList.add(newUser);
            User secondUser = new User("Admin","1234");  //TODO Get username and password from UI
            userList.add(secondUser);
            writeUserListToFile(userList);
            //System.out.println(userList.get(0).userName);
            //System.out.println(userList.get(1).userName);

        }
        catch(IOException e){
            Log.e("IOException",e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("IOException","Class not found");
        }
        }
        else{
        return;
        }
    }

    public void writeUserListToFile(ArrayList<User> writeUsers) {
        System.out.println("writeUserListToFile");
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("users.txt",Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            int i = 0;
            while(i < writeUsers.size()) {
                oos.writeObject(writeUsers.get(i));
                System.out.println("Wrote "+writeUsers.get(i).userName+" to file.");
                i++;
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
    public ArrayList<User> getUserList(){
        return userList;
    }
}