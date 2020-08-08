package com.example.go4lunch.di;

import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.service.colleague.DummyColleagueApiService;

public class DI {

    private static ColleagueApiService service = new DummyColleagueApiService();

    /**
     * Get an instance on @{@link ColleagueApiService}
     *
     * @return
     */
    public static ColleagueApiService getColleagueApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link ColleagueApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return
     */
    public static ColleagueApiService getNewInstanceApiService() {
        return new DummyColleagueApiService();
    }
}
