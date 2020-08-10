package com.example.go4lunch.ui.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.Restaurants;
import com.example.go4lunch.ui.colleague.MyColleagueRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnClickRestaurantFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Colleague> colleagueList;
    private MyColleagueRecyclerViewAdapter myAdapter;

    public OnClickRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_on_click_colleague_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        initChoiceList();
        return view;
    }

    private void initChoiceList() {
        colleagueList= new ArrayList<>();
        if (getActivity().getIntent().getSerializableExtra("Restaurant")!= null) {
            MyRestaurantModel myRestaurant = (MyRestaurantModel) getActivity().getIntent()
                    .getSerializableExtra("Restaurant");
            if (myRestaurant.getColleagueList()!= null)
            colleagueList= myRestaurant.getColleagueList();
        }
        if (getActivity().getIntent().getSerializableExtra("MapsActivityRestaurant") != null) {
            MyRestaurantModel myRestaurant = (MyRestaurantModel) getActivity().getIntent()
                    .getSerializableExtra("MapsActivityRestaurant");

            if (myRestaurant.getColleagueList()!= null)
                colleagueList= myRestaurant.getColleagueList();
        }
        myAdapter = new MyColleagueRecyclerViewAdapter(colleagueList);
        mRecyclerView.setAdapter(myAdapter);
    }
}