package com.example.go4lunch.service.colleague;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;

import java.util.ArrayList;
import java.util.List;

public class ColleagueLike {

    static ColleagueApiService apiService = DI.getColleagueApiService();
    static List<Colleague> colleagueList = apiService.getColleagues();

    public static List<Colleague> setTwoPeopleLikes() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);

        List<Colleague> colleagueLikeList = new ArrayList<>();
        colleagueLikeList.add(scarlett);
        colleagueLikeList.add(hugh);

        return colleagueLikeList;
    }

    public static List<Colleague> setFivePeopleLikes() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        Colleague henry = colleagueList.get(4);

        List<Colleague> colleagueLikeList = new ArrayList<>();
        colleagueLikeList.add(scarlett);
        colleagueLikeList.add(hugh);
        colleagueLikeList.add(nana);
        colleagueLikeList.add(godfrey);
        colleagueLikeList.add(henry);

        return colleagueLikeList;
    }

    public static List<Colleague> setSevenPeopleLike() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        Colleague henry = colleagueList.get(4);
        Colleague angelina = colleagueList.get(5);
        Colleague robert = colleagueList.get(6);

        List<Colleague> colleagueLikeList = new ArrayList<>();
        colleagueLikeList.add(scarlett);
        colleagueLikeList.add(hugh);
        colleagueLikeList.add(nana);
        colleagueLikeList.add(godfrey);
        colleagueLikeList.add(henry);
        colleagueLikeList.add(angelina);
        colleagueLikeList.add(robert);

        return colleagueLikeList;
    }
}
