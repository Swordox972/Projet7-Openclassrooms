package com.example.go4lunch.service.colleague;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.restaurant.RestaurantInformation;

import java.util.ArrayList;
import java.util.List;

public class ColleagueChoice {
    static ColleagueApiService apiService;

    public static List<Colleague> setScarlettAndHughChoice() {
        apiService = DI.getColleagueApiService();

        List<Colleague> colleagueList = apiService.getColleagues();

        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueJoinOrNot("is joining!");
        hugh.setColleagueJoinOrNot("is joining!");
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(scarlett);
        colleagueChoiceList.add(hugh);

        return colleagueChoiceList;
    }

    public static List<Colleague> setNanaAndGodfreyChoice() {
        apiService = DI.getColleagueApiService();
        List<Colleague> colleagueList = apiService.getColleagues();

        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueJoinOrNot("is joining!");
        godfrey.setColleagueJoinOrNot("is joining!");
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(nana);
        colleagueChoiceList.add(godfrey);

        return colleagueChoiceList;
    }

    public static void setScarlettAndHughChoiceRestaurant() {
        apiService=DI.getColleagueApiService();
        List<Colleague> colleagueList= apiService.getColleagues();
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName1);
        hugh.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName1);
    }

    public static void setNanaAndGodfreyChoiceRestaurant() {
        apiService=DI.getColleagueApiService();
        List<Colleague> colleagueList= apiService.getColleagues();
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName2);
        godfrey.setColleagueChoice("is eating at " + RestaurantInformation.restaurantName2);
    }

    public static void setOtherColleagueChoiceRestaurant() {
        apiService=DI.getColleagueApiService();
        List<Colleague> colleagueList= apiService.getColleagues();
        Colleague henry= colleagueList.get(4);
        Colleague angelina= colleagueList.get(5);
        Colleague robert=colleagueList.get(6);
        Colleague emma=colleagueList.get(7);
        henry.setColleagueChoice(" hasn't decided yet");
        angelina.setColleagueChoice(" hasn't decided yet");
        robert.setColleagueChoice(" hasn't decided yet");
        emma.setColleagueChoice(" hasn't decided yet");

    }
}
