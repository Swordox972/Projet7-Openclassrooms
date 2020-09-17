package com.example.go4lunch;

import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.service.colleague.ColleagueChoice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ColleagueChoiceUnitTest {

    private ColleagueApiService apiService;
    private List<Colleague> colleagueList;

    @Before
    public void setUp() {
        apiService = DI.getColleagueApiService();
        colleagueList = apiService.getColleagues();
    }

    @Test
    public void setScarlettAndHughJoiningWithSuccess() {
        List<Colleague> scarlettAndHughList = ColleagueChoice.setScarlettAndHughJoining();
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);

        assert scarlettAndHughList.contains(scarlett);
        assert scarlettAndHughList.contains(hugh);
        assertEquals(scarlettAndHughList.get(0).getColleagueStatus(), Colleague.Status.isJoining);
        assertEquals(scarlettAndHughList.get(1).getColleagueStatus(), Colleague.Status.isJoining);
    }

    @Test
    public void setNanaAndHughJoiningWithSuccess() {
        List<Colleague> nanaAndGodfreyList = ColleagueChoice.setNanaAndGodfreyJoining();
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);

        assert nanaAndGodfreyList.contains(nana);
        assert nanaAndGodfreyList.contains(godfrey);
        assertEquals(nanaAndGodfreyList.get(0).getColleagueStatus(), Colleague.Status.isJoining);
        assertEquals(nanaAndGodfreyList.get(1).getColleagueStatus(), Colleague.Status.isJoining);
    }

    @Test
    public void setScarlettAndHughEatingAtWithSuccess() {
        ColleagueChoice.setScarlettAndHughEatingAt();
        Colleague scarlett = colleagueList.get(0);
        Colleague hugh = colleagueList.get(1);

        assertEquals(scarlett.getColleagueStatus(), Colleague.Status.isEatingAt);
        assertEquals(hugh.getColleagueStatus(), Colleague.Status.isEatingAt);
    }

    @Test
    public void setNanaAndGodfreyEatingAtWithSuccess() {
        ColleagueChoice.setNanaAndGodfreyEatingAt();
        Colleague nana = colleagueList.get(2);
        Colleague godfrey = colleagueList.get(3);

        assertEquals(nana.getColleagueStatus(), Colleague.Status.isEatingAt);
        assertEquals(godfrey.getColleagueStatus(), Colleague.Status.isEatingAt);

    }

    @Test
    public void setOtherColleagueNotDecidedWithSuccess() {
        ColleagueChoice.setOtherColleagueNotDecided();
        Colleague henry = colleagueList.get(4);
        Colleague angelina = colleagueList.get(5);
        Colleague robert = colleagueList.get(6);
        Colleague emma = colleagueList.get(7);

        assertEquals(henry.getColleagueStatus(), Colleague.Status.notDecided);
        assertEquals(angelina.getColleagueStatus(), Colleague.Status.notDecided);
        assertEquals(robert.getColleagueStatus(), Colleague.Status.notDecided);
        assertEquals(emma.getColleagueStatus(), Colleague.Status.notDecided);
    }
}
