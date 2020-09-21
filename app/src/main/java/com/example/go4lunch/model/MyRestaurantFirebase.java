package com.example.go4lunch.model;

public class MyRestaurantFirebase {

    private String restaurantId;
    private long likeNumber;

    public MyRestaurantFirebase() {
    }

    public MyRestaurantFirebase(String restaurantId, long likeNumber) {
        this.restaurantId = restaurantId;
        this.likeNumber = likeNumber;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(long likeNumber) {
        this.likeNumber = likeNumber;
    }
}
