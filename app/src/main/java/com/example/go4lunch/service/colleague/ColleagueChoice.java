package com.example.go4lunch.service.colleague;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;

import java.util.ArrayList;
import java.util.List;

public class ColleagueChoice {

    static ColleagueApiService apiService = DI.getColleagueApiService();
    static List<Colleague> colleagueList = apiService.getColleagues();

    public static List<Colleague> setScarlettAndHughJoining() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueStatus(Colleague.Status.isJoining);
        hugh.setColleagueStatus(Colleague.Status.isJoining);
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(scarlett);
        colleagueChoiceList.add(hugh);

        return colleagueChoiceList;
    }

    public static List<Colleague> setNanaAndGodfreyJoining() {
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueStatus(Colleague.Status.isJoining);
        godfrey.setColleagueStatus(Colleague.Status.isJoining);
        List<Colleague> colleagueChoiceList = new ArrayList<>();
        colleagueChoiceList.add(nana);
        colleagueChoiceList.add(godfrey);

        return colleagueChoiceList;
    }

    public static void setScarlettAndHughEatingAt() {
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);
        scarlett.setColleagueStatus(Colleague.Status.isEatingAt);
        hugh.setColleagueStatus(Colleague.Status.isEatingAt);
    }

    public static void setNanaAndGodfreyEatingAt() {
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);
        nana.setColleagueStatus(Colleague.Status.isEatingAt);
        godfrey.setColleagueStatus(Colleague.Status.isEatingAt);
    }

    public static void setOtherColleagueNotDecided() {
        Colleague henry = colleagueList.get(4);
        Colleague angelina = colleagueList.get(5);
        Colleague robert = colleagueList.get(6);
        Colleague emma = colleagueList.get(7);
        henry.setColleagueStatus(Colleague.Status.notDecided);
        angelina.setColleagueStatus(Colleague.Status.notDecided);
        robert.setColleagueStatus(Colleague.Status.notDecided);
        emma.setColleagueStatus(Colleague.Status.notDecided);

    }
}

