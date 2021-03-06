package com.example.go4lunch.service.colleague;

import com.example.go4lunch.model.Colleague;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyColleagueApiGenerator {


    public static List<Colleague> DUMMY_COLLEAGUES = Arrays.asList(
            new Colleague(1, "Scarlett", null, null,
                    "https://i.ibb.co/2tKXDwF/Scarlett-250.jpg"),
            new Colleague(2, "Hugh", null, null,
                    "https://i.ibb.co/TPbCPkG/Hugh-250.jpg"),
            new Colleague(3, "Nana", null, null,
                    "https://i.ibb.co/z8xVFmQ/Nana-250.jpg"),
            new Colleague(4, "Godfrey", null, null,
                    "https://i.ibb.co/yScPjRk/Godfrey-250.png"),
            new Colleague(5, "Henry", null, null,
                    "https://i.ibb.co/4ZXYbN0/Henry-250.jpg"),
            new Colleague(6, "Angelina", null, null,
                    "https://i.ibb.co/hBcbP7f/Angelina-250.jpg"),
            new Colleague(7, "Robert", null, null,
                    "https://i.ibb.co/xhcNTWM/Robert-250.jpg"),
            new Colleague(8, "Emma", null, null,
                    "https://i.ibb.co/0j18sWM/Emma-250.jpg")

    );


    static List<Colleague> generateColleague() {
        return new ArrayList<>(DUMMY_COLLEAGUES);
    }

}
