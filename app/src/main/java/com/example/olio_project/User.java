package com.example.olio_project;

public class User {
    UserData userData;                                              //User has a single UserData, UserData has a list of Weeks, each Week has a list of Days, each Day has a list of DataEntries
    String userName;
    String password;
}
