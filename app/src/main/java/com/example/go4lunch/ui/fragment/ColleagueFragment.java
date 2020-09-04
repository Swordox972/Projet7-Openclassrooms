package com.example.go4lunch.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.ui.adapter.MyColleagueRecyclerViewAdapter;

import java.util.List;


public class ColleagueFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ColleagueApiService mApiService;
    List<Colleague> myColleagueList;
    private MyColleagueRecyclerViewAdapter myAdapter;

    public ColleagueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getColleagueApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colleague_list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        initList();
        return view;
    }

    private void initList() {
        myColleagueList = mApiService.getColleagues();
        myAdapter = new MyColleagueRecyclerViewAdapter(myColleagueList);
        mRecyclerView.setAdapter(myAdapter);
    }
}