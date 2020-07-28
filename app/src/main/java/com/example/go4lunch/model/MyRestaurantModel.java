package com.example.go4lunch.model;

import java.io.Serializable;

public class MyRestaurantModel implements Serializable {

    private String restaurantName;
    private String restaurantAddress;
    private String restaurantOpening;
    private String restaurantDistance;

    // Empty constructor
    public MyRestaurantModel() {
    }

    //Constructor with parameters
    public MyRestaurantModel(String restaurantName, String restaurantAddress,
                             String restaurantOpening, String restaurantDistance) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantOpening = restaurantOpening;
        this.restaurantDistance = restaurantDistance;

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantOpening() {
        return restaurantOpening;
    }

    public void setRestaurantOpening(String restaurantOpening) {
        this.restaurantOpening = restaurantOpening;
    }

    public String getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }


}
