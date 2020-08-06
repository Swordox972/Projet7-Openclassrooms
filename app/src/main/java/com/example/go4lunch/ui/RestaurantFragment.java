package com.example.go4lunch.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.events.OpenRestaurantEvent;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.Restaurants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class RestaurantFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<MyRestaurantModel> restaurantList;
    private MyRestaurantRecyclerViewAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        //Initialize recyclerView
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }

    private void initList() {
        restaurantList = Restaurants.getInstance().getMyRestaurantList();
        myAdapter = new MyRestaurantRecyclerViewAdapter(restaurantList);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onOpenRestaurant(OpenRestaurantEvent event) {
        MyRestaurantModel myRestaurantModel= event.myRestaurantModel;
        if (myRestaurantModel != null) {
            startActivity(new Intent(getContext() , onClickRestaurantActivity.class)
                    .putExtra("Restaurant", myRestaurantModel));
        }
    }

    }
