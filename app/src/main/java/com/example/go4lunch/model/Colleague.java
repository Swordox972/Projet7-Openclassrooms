package com.example.go4lunch.model;

import java.io.Serializable;

public class Colleague implements Serializable {
    int colleagueId;
    String colleagueName;
    String colleagueJoinOrNot;
    String colleagueChoice;
    String avatarUrl;


    //Empty constructor
    public Colleague() {
    }

    //Constructor with parameters
    public Colleague(int colleagueId, String colleagueName, String colleagueJoinOrNot,
            String colleagueChoice,String avatarUrl) {
        this.colleagueId = colleagueId;
        this.colleagueName = colleagueName;
        this.colleagueJoinOrNot= colleagueJoinOrNot;
        this.colleagueChoice= colleagueChoice;
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

    public String getColleagueJoinOrNot() {
        return colleagueJoinOrNot;
    }

    public void setColleagueJoinOrNot(String colleagueJoinOrNot) {
        this.colleagueJoinOrNot = colleagueJoinOrNot;
    }

    public String getColleagueChoice() {
        return colleagueChoice;
    }

    public void setColleagueChoice(String colleagueChoice) {
        this.colleagueChoice = colleagueChoice;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
