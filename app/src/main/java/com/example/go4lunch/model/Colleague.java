package com.example.go4lunch.model;

import java.io.Serializable;

public class Colleague implements Serializable {
    int colleagueId;
    String colleagueNameAndChoice;
    String avatarUrl;


    //Empty constructor
    public Colleague() {
    }

    //Constructor with parameters
    public Colleague(int colleagueId, String colleagueNameAndChoice, String avatarUrl) {
        this.colleagueId = colleagueId;
        this.colleagueNameAndChoice = colleagueNameAndChoice;
        this.avatarUrl = avatarUrl;
    }

    //Getters and setters
    public int getColleagueId() {
        return colleagueId;
    }

    public void setColleagueId(int userId) {
        this.colleagueId = userId;
    }

    public String getColleagueNameAndChoice() {
        return colleagueNameAndChoice;
    }

    public void setColleagueNameAndChoice(String colleagueNameAndChoice) {
        this.colleagueNameAndChoice = colleagueNameAndChoice;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
