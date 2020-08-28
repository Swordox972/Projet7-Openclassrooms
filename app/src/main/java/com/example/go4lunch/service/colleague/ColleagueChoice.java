package com.example.go4lunch.service.colleague;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.restaurant.RestaurantInformation;

import java.util.ArrayList;
import java.util.List;

public class ColleagueChoice {

    static ColleagueApiService apiService = DI.getColleagueApiService();
    static List<Colleague> colleagueList= apiService.getColleagues();

    public static List<Colleague> setScarlettAndHughChoice() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueIsJoining("is joining!");
        hugh.setColleagueIsJoining("is joining!");
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(scarlett);
        colleagueChoiceList.add(hugh);

        return colleagueChoiceList;
    }

    public static List<Colleague> setNanaAndGodfreyChoice() {

        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueIsJoining("is joining!");
        godfrey.setColleagueIsJoining("is joining!");
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(nana);
        colleagueChoiceList.add(godfrey);

        return colleagueChoiceList;
    }

    public static void setScarlettAndHughChoiceRestaurant() {

        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName1);
        hugh.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName1);
    }

    public static void setNanaAndGodfreyChoiceRestaurant() {

        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName2);
        godfrey.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName2);
    }

    public static void setOtherColleagueChoiceRestaurant() {
        String notDecided = "hasn't decided yet";

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

