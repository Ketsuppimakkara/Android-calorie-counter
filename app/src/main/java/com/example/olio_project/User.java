package com.example.olio_project;

public class User implements java.io.Serializable {
    UserData userData;                                              //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    String userName;
    String password;

    public User(String name, String pw){
        userData = new UserData();
        userName = name;
        password = pw;
    }

    public UserData getUserData() {
        return userData;
    }

}
