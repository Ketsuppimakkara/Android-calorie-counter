package com.example.olio_project;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.loader.content.Loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;


public class UserList extends LoginActivity implements Serializable {

    Context context = this;
    private ArrayList<User> userList = new ArrayList<User>();

    public void getUserListFromFile(){
            readFile();
    }

    public void readFile(){
        try{
            FileInputStream fis = context.openFileInput("users.txt");
            ObjectInputStream ois = null;
            userList = (ArrayList<User>) ois.readObject();
            System.out.println(userList);
            fis.close();
        }
        catch(FileNotFoundException e){
            Log.e("FileNotFoundException","File not found");
            userList = new ArrayList<User>();
            User newUser = new User("Ketsuppimakkara","1234");  //TODO Get username and password from UI
            userList.add(newUser);
            addNewUserToFile(newUser);
            System.out.println(userList);

        }
        catch(IOException e){
            Log.e("IOException","Error in input");
        } catch (ClassNotFoundException e) {
            Log.e("IOException","Class not found");
        }

    }
    public void addNewUserToFile(User user) {
        try {
            FileOutputStream fos = context.openFileOutput("users.txt",Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user);
            oos.close();
        }
        catch(FileNotFoundException e){
            Log.e("FileNotFoundException","File not found");
        }
        catch(IOException e){
            Log.e("IOException","Error in input");
        }

    }
}
