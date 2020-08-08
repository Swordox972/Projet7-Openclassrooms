package com.example.go4lunch.events;

import com.example.go4lunch.model.MyRestaurantModel;

public class OpenRestaurantEvent {

    /**
     * Restaurant to open
     */

    public MyRestaurantModel myRestaurantModel;


    /**
     * Constructor
     *
     * @param myRestaurantModel
     */

    public OpenRestaurantEvent(MyRestaurantModel myRestaurantModel) {
        this.myRestaurantModel = myRestaurantModel;
    }
}
