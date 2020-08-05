package com.example.go4lunch.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRestaurantRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    private List<MyRestaurantModel> myRestaurantList;

    public MyRestaurantRecyclerViewAdapter(List<MyRestaurantModel> items) {
        this.myRestaurantList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant,
                parent, false);

        myRestaurantList.size();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyRestaurantModel myRestaurant = myRestaurantList.get(position);
       myRestaurantList.size();
        holder.restaurantName.setText(myRestaurant.getRestaurantName());
        holder.restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        holder.restaurantOpeningHours.setText(myRestaurant.getRestaurantOpening());
        holder.restaurantDistance.setText(myRestaurant.getRestaurantDistance());
        holder.imageView.setImageBitmap(myRestaurant.getRestaurantImage());
    }

    @Override
    public int getItemCount() {
        return myRestaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.restaurant_address)
        TextView restaurantAddress;
        @BindView(R.id.restaurant_opening_hours)
        TextView restaurantOpeningHours;
        @BindView(R.id.restaurant_distance)
        TextView restaurantDistance;
        @BindView(R.id.restaurant_imageview)
        ImageView imageView;


        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
