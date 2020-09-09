package com.example.go4lunch.service.colleague;

import com.example.go4lunch.R;
import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.App;
import com.example.go4lunch.service.restaurant.RestaurantInformation;

import java.util.ArrayList;
import java.util.List;

public class ColleagueChoice {

    static ColleagueApiService apiService = DI.getColleagueApiService();
    static List<Colleague> colleagueList = apiService.getColleagues();
    static String isEatingAt = App.getContext().getResources().getString(R.string.is_eating_at);
    static String isJoining = App.getContext().getResources().getString(R.string.is_joining);
    static String notDecided = App.getContext().getString(R.string.has_not_decided_yet);
    public static List<Colleague> setScarlettAndHughChoice() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueIsJoining(isJoining);
        hugh.setColleagueIsJoining(isJoining);
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(scarlett);
        colleagueChoiceList.add(hugh);

        return colleagueChoiceList;
    }

    public static List<Colleague> setNanaAndGodfreyChoice() {

        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueIsJoining(isJoining);
        godfrey.setColleagueIsJoining(isJoining);
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(nana);
        colleagueChoiceList.add(godfrey);

        return colleagueChoiceList;
    }

    public static void setScarlettAndHughChoiceRestaurant() {

        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueChoice(isEatingAt + RestaurantInformation.restaurantName1);
        hugh.setColleagueChoice(isEatingAt + RestaurantInformation.restaurantName1);
    }

    public static void setNanaAndGodfreyChoiceRestaurant() {

        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueChoice(isEatingAt + RestaurantInformation.restaurantName2);
        godfrey.setColleagueChoice(isEatingAt + RestaurantInformation.restaurantName2);
    }

    public static void setOtherColleagueChoiceRestaurant() {

        Colleague henry = colleagueList.get(4);
        Colleague angelina = colleagueList.get(5);
        Colleague robert = colleagueList.get(6);
        Colleague emma = colleagueList.get(7);
        henry.setColleagueChoice(notDecided);
        angelina.setColleagueChoice(notDecided);
        robert.setColleagueChoice(notDecided);
        emma.setColleagueChoice(notDecided);

    }
}

