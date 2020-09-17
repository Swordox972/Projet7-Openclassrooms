package com.example.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Colleague implements Serializable{
    private int colleagueId;
    private String colleagueName;
    private Status colleagueStatus = Status.notDecided;
    private String colleagueRestaurantChoice;
    private String avatarUrl;

    //Empty constructor
    public Colleague() {
    }

    //Constructor with parameters
    public Colleague(int colleagueId, String colleagueName, Status colleagueStatus,
                     String colleagueRestaurantChoice,String avatarUrl) {
        this.colleagueId = colleagueId;
        this.colleagueName = colleagueName;
        this.colleagueStatus = colleagueStatus;
        this.colleagueRestaurantChoice= colleagueRestaurantChoice;
        this.avatarUrl = avatarUrl;
    }

    //Getters and setters
    public int getColleagueId() {
        return colleagueId;
    }

    public void setColleagueId(int userId) {
        this.colleagueId = userId;
    }

    public String getColleagueName() {
        return colleagueName;
    }

    public void setColleagueName(String colleagueName) {
        this.colleagueName = colleagueName;
    }

    public Status getColleagueStatus() {
        return colleagueStatus;
    }

    public void setColleagueStatus(Status colleagueStatus) {
        this.colleagueStatus = colleagueStatus;
    }

    public String getColleagueRestaurantChoice() {
        return colleagueRestaurantChoice;
    }

    public void setColleagueRestaurantChoice(String colleagueRestaurantChoice) {
        this.colleagueRestaurantChoice = colleagueRestaurantChoice;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }



    public enum Status {
        isJoining,
        isEatingAt,
        notDecided
    }

}
