package com.example.go4lunch.service.colleague;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class RestaurantChoice {
   static ColleagueApiService apiService;
    public static List<Colleague> setScarlettAndHughChoice() {
        apiService= DI.getColleagueApiService();

        List<Colleague> colleagueList= apiService.getColleagues();

        Colleague scarlett= colleagueList.get(0);
        Colleague hugh= colleagueList.get(1);
        scarlett.setColleagueJoinOrNot("is Joining");
        hugh.setColleagueJoinOrNot("is Joining");
        List<Colleague> colleagueChoiceList= new ArrayList<>();
        colleagueChoiceList.add(scarlett); colleagueChoiceList.add(hugh);

        return colleagueChoiceList;
    }

    public static List<Colleague> setNanaAndGodfreyChoice() {
        apiService= DI.getColleagueApiService();
        List<Colleague> colleagueList= apiService.getColleagues();

        Colleague nana= colleagueList.get(2);
        Colleague godfrey= colleagueList.get(3);
        nana.setColleagueJoinOrNot("is Joining");
        godfrey.setColleagueJoinOrNot("is Joining");
        List<Colleague> colleagueChoiceList= new ArrayList<>();
        colleagueChoiceList.add(nana); colleagueChoiceList.add(godfrey);

        return colleagueChoiceList;
    }
}
