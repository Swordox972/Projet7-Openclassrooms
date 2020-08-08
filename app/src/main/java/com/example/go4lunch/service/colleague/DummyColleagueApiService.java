package com.example.go4lunch.service.colleague;

import com.example.go4lunch.model.Colleague;

import java.util.List;

public class DummyColleagueApiService implements ColleagueApiService {

    private List<Colleague> colleagues = DummyColleagueApiGenerator.generateColleague();

    @Override
    public List<Colleague> getColleagues() {
        return colleagues;
    }
}
