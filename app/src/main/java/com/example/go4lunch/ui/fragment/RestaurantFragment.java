package com.example.go4lunch.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.events.OpenRestaurantEvent;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.Restaurants;
import com.example.go4lunch.ui.activity.MapsActivity;
import com.example.go4lunch.ui.activity.OnClickRestaurantActivity;
import com.example.go4lunch.ui.adapter.MyRestaurantRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class RestaurantFragment extends Fragment {

    private MapsActivity mapsActivity;
    private RecyclerView mRecyclerView;
    private List<MyRestaurantModel> restaurantList = Restaurants.getInstance().getMyRestaurantList();
    private List<MyRestaurantModel> restaurantFilteredList;
    private MyRestaurantRecyclerViewAdapter myAdapter;
    private OnClickRestaurantActivity onClickRestaurantActivity;
    private static final int RESTAURANT_REQUEST_CODE = 18;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onClickRestaurantActivity= new OnClickRestaurantActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        mapsActivity = (MapsActivity) getActivity();
        //Initialize recyclerView
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (getContext() != null) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        initList();
        return view;
    }

    private void initList() {
        if (mapsActivity.placeSearch == null) {
            restaurantList = Restaurants.getInstance().getMyRestaurantList();
            myAdapter = new MyRestaurantRecyclerViewAdapter(restaurantList);
            mRecyclerView.setAdapter(myAdapter);
        } else {
            restaurantFilteredList = Restaurants.getInstance().getFilteredRestaurantList();
            MyRestaurantModel myRestaurantModel;
            for (int i = 0; i < restaurantList.size(); i++) {
                if (restaurantList.get(i).getRestaurantId().equals(
                        mapsActivity.placeSearch.getId())) {
                    myRestaurantModel = restaurantList.get(i);
                    restaurantFilteredList.add(myRestaurantModel);
                    break;
                }
            }
            myAdapter = new MyRestaurantRecyclerViewAdapter(restaurantFilteredList);
            mRecyclerView.setAdapter(myAdapter);

        }
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
        MyRestaurantModel myRestaurantModel = event.myRestaurantModel;
        Intent intent = new Intent(getContext(), OnClickRestaurantActivity.class);
        intent.putExtra("Restaurant", myRestaurantModel);
        if (myRestaurantModel != null) {
            startActivityForResult(intent, RESTAURANT_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESTAURANT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                MyRestaurantModel myRestaurantModel =(MyRestaurantModel) data.getSerializableExtra("Restaurant");
                for (int i = 0; i < restaurantList.size(); i++) {
                    String restaurantId = restaurantList.get(i).getRestaurantId();
                    if (restaurantId.equals(myRestaurantModel.getRestaurantId())) {//REPLACE RESTAURANT MODIFIED
                        restaurantList.set(i,myRestaurantModel);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }


        }
    }
}
