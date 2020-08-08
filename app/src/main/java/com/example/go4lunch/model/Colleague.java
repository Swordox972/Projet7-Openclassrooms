package com.example.go4lunch.model;

public class Colleague {
    int colleagueId;
    String colleagueName;
    String avatarUrl;

    //Empty constructor
    public Colleague() {
    }

    //Constructor with parameters
    public Colleague(int colleagueId, String colleagueName, String avatarUrl) {
        this.colleagueId = colleagueId;
        this.colleagueName = colleagueName;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


}
