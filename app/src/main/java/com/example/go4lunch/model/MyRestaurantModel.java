package com.example.go4lunch.model;

import java.io.Serializable;
import java.util.List;

public class MyRestaurantModel implements Serializable {

    String restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantOpening;
    private String restaurantDistance;
    private String restaurantImageName;
    private String restaurantPhoneNumber;
    private String restaurantWebsite;
    private boolean isLiked;
    private List<Colleague> colleagueList;
    private List<Colleague> colleagueLikeList;

    // Empty constructor
    public MyRestaurantModel() {
    }

    //Constructor with parameters
    public MyRestaurantModel(String restaurantId, String restaurantName, String restaurantAddress,
                             String restaurantOpening, String restaurantDistance,
                             String restaurantImageName, String restaurantPhoneNumber,
                             String restaurantWebsite, boolean isLiked,
                             List<Colleague> colleagueList, List<Colleague> colleagueLikeList) {

        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantOpening = restaurantOpening;
        this.restaurantDistance = restaurantDistance;
        this.restaurantImageName = restaurantImageName;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.restaurantWebsite = restaurantWebsite;
        this.isLiked= isLiked;
        this.colleagueList = colleagueList;
        this.colleagueLikeList = colleagueLikeList;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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

    public String getRestaurantImageName() {
        return restaurantImageName;
    }

    public void setRestaurantImageName(String restaurantImageName) {
        this.restaurantImageName = restaurantImageName;
    }

    public String getRestaurantPhoneNumber() {
        return restaurantPhoneNumber;
    }

    public void setRestaurantPhoneNumber(String restaurantPhoneNumber) {
        this.restaurantPhoneNumber = restaurantPhoneNumber;
    }

    public String getRestaurantWebsite() {
        return restaurantWebsite;
    }

    public void setRestaurantWebsite(String restaurantWebsite) {
        this.restaurantWebsite = restaurantWebsite;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public List<Colleague> getColleagueList() {
        return colleagueList;
    }

    public void setColleagueList(List<Colleague> colleagueList) {
        this.colleagueList = colleagueList;
    }

    public List<Colleague> getColleagueLikeList() {
        return colleagueLikeList;
    }

    public void setColleagueLikeList(List<Colleague> colleagueLikeList) {
        this.colleagueLikeList = colleagueLikeList;
    }
}
