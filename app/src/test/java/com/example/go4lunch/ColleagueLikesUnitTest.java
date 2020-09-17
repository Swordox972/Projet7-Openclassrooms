package com.example.go4lunch;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.service.colleague.ColleagueLike;
import com.example.go4lunch.service.colleague.DummyColleagueApiGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ColleagueLikesUnitTest {

    private ColleagueApiService apiService;

    @Before
    public void setUp() {
        apiService = DI.getColleagueApiService();
    }

    //Method that test our interface and DI
    @Test
    public void getColleaguesWithSuccess() {
        List<Colleague> colleagues = apiService.getColleagues();
        List<Colleague> expectedColleagues = DummyColleagueApiGenerator.DUMMY_COLLEAGUES;

        assertEquals(colleagues, expectedColleagues);
    }

    //Methods that test our service class ColleagueLikes
    @Test
    public void setTwoPeopleLikesWithSuccess() {
        List<Colleague> colleagueLike = ColleagueLike.setTwoPeopleLikes();

        assertEquals(colleagueLike.size(), 2);
    }

    @Test
    public void setFivePeopleLikesWithSuccess() {
        List<Colleague> colleagueLike = ColleagueLike.setFivePeopleLikes();

        assertEquals(colleagueLike.size(), 5);
    }

    @Test
    public void setSevenPeopleLikesWithSuccess() {
        List<Colleague> colleagueLike = ColleagueLike.setSevenPeopleLike();

        assertEquals(colleagueLike.size(), 7);
    }
}