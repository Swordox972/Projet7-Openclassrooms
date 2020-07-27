package com.example.go4lunch.model;

import android.provider.ContactsContract;
import android.widget.ImageView;

import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.io.Serializable;

public class MyRestaurantModel implements Serializable {

    private String restaurantName;
    private String restaurantAddress;
    private OpeningHours restaurantOpening;
    private String restaurantDistance;

    // Empty constructor
    public MyRestaurantModel() {
    }

    //Constructor with parameters
    public MyRestaurantModel(String restaurantName, String restaurantAddress,
                             OpeningHours restaurantOpening, String restaurantDistance) {
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

    public OpeningHours getRestaurantOpening() {
        return restaurantOpening;
    }

    public void setRestaurantOpening(OpeningHours restaurantOpening) {
        this.restaurantOpening = restaurantOpening;
    }

    public String getRestaurantDistance() {
        return restaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance) {
        this.restaurantDistance = restaurantDistance;
    }


}
