package com.example.go4lunch.service.restaurant;

import com.example.go4lunch.model.MyRestaurantModel;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurants implements Serializable {

    // Singleton
    private static final Restaurants ourInstance = new Restaurants();
    private ArrayList<MyRestaurantModel> myRestaurantList = new ArrayList<>();
    private ArrayList<MyRestaurantModel> filteredRestaurantList = new ArrayList<>();

    //getInstance method to access Singleton
    public static Restaurants getInstance() {
        return ourInstance;
    }

    //Empty constructor
    private Restaurants() {
    }

    public ArrayList<MyRestaurantModel> getMyRestaurantList() {
        return this.myRestaurantList;
    }

    public ArrayList<MyRestaurantModel> getFilteredRestaurantList() {
        return this.filteredRestaurantList;
    }
}
