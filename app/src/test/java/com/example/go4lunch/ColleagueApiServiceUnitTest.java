package com.example.go4lunch;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.service.colleague.DummyColleagueApiGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ColleagueApiServiceUnitTest {

    private ColleagueApiService apiService;

    @Before
    public void setUp() {
        apiService = DI.getColleagueApiService();
    }

    @Test
    public void getColleagueWithSuccess() {
        List<Colleague> colleagueList = apiService.getColleagues();
        List<Colleague> expectedColleagueList = DummyColleagueApiGenerator.DUMMY_COLLEAGUES;

        assertEquals(colleagueList, expectedColleagueList);
    }
}
