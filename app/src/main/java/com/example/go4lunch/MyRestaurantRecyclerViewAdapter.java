package com.example.go4lunch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRestaurantRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    //Create a list of restaurant object
     private final List<MyRestaurantModel> mRestaurants;

     public MyRestaurantRecyclerViewAdapter (List<MyRestaurantModel> items) {
         mRestaurants = items;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.fragment_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyRestaurantModel myRestaurantModel= mRestaurants.get(position);
        holder.name_restaurant.setText(myRestaurantModel.getRestaurantName());
        holder.address_restaurant.setText(myRestaurantModel.getRestaurantAddress());
        holder.opening_restaurant.setText(myRestaurantModel.getRestaurantOpening());
        holder.distance_restaurant.setText(myRestaurantModel.getRestaurantDistance());


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_name)
        TextView name_restaurant;
        @BindView(R.id.restaurant_address)
        TextView address_restaurant;
        @BindView(R.id.restaurant_opening)
        TextView opening_restaurant;
        @BindView(R.id.restaurant_distance)
        TextView distance_restaurant;
        @BindView(R.id.restaurant_image_view)
        ImageView restaurantImageView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
