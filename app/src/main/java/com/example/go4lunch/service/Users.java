package com.example.go4lunch.service;

import com.example.go4lunch.model.Colleague;

import java.io.Serializable;
import java.util.ArrayList;

public class Users implements Serializable {

    //Singleton
    public static final Users ourInstance= new Users();
    private ArrayList<Colleague> myUserList= new ArrayList<>();

    //getInstance method to access Singleton
    public static Users getInstance() {return ourInstance;}

    //Empty constructor
    private Users() {}

    public ArrayList<Colleague> getMyUserList() {return this.myUserList;}


}
