package com.example.go4lunch.model;

public class MyRestaurantFirebase {

    private String restaurantId;
    private int likeNumber;

    public MyRestaurantFirebase() {
    }

    public MyRestaurantFirebase(String restaurantId, int likeNumber) {
        this.restaurantId = restaurantId;
        this.likeNumber = likeNumber;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }
}
