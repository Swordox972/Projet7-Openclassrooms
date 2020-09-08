package com.example.go4lunch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Colleague implements Serializable, Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Colleague>() {
        @Override
        public Colleague createFromParcel(Parcel parcel) {
            return new Colleague(parcel);
        }

        @Override
        public Colleague[] newArray(int i) {
            return new Colleague[i];
        }
    };

    int colleagueId;
    String colleagueName;
    String colleagueIsJoining;
    String colleagueChoice;
    String avatarUrl;


    //Empty constructor
    public Colleague() {
    }

    //Constructor with parameters
    public Colleague(int colleagueId, String colleagueName, String colleagueIsJoining,
                     String colleagueChoice, String avatarUrl) {
        this.colleagueId = colleagueId;
        this.colleagueName = colleagueName;
        this.colleagueIsJoining = colleagueIsJoining;
        this.colleagueChoice = colleagueChoice;
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

    public String getColleagueIsJoining() {
        return colleagueIsJoining;
    }

    public void setColleagueIsJoining(String colleagueIsJoining) {
        this.colleagueIsJoining = colleagueIsJoining;
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

    //Parceling part
    public Colleague(Parcel in) {
        this.colleagueId = in.readInt();
        this.colleagueName = in.readString();
        this.colleagueIsJoining = in.readString();
        this.colleagueChoice = in.readString();
        this.avatarUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(colleagueId);
        parcel.writeString(colleagueName);
        parcel.writeString(colleagueIsJoining);
        parcel.writeString(colleagueChoice);
        parcel.writeString(avatarUrl);
    }

}
