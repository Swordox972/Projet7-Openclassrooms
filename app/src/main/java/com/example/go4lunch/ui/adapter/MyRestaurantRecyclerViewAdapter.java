package com.example.go4lunch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.events.OpenRestaurantEvent;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.RestaurantInformation;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRestaurantRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    private List<MyRestaurantModel> myRestaurantList;
    private Context context;

    public MyRestaurantRecyclerViewAdapter(List<MyRestaurantModel> items) {
        this.myRestaurantList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant,
                parent, false);

        context= parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyRestaurantModel myRestaurant = myRestaurantList.get(position);

        holder.restaurantName.setText(myRestaurant.getRestaurantName());
        holder.restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        holder.restaurantOpeningHours.setText(myRestaurant.getRestaurantOpening());
        holder.restaurantDistance.setText(myRestaurant.getRestaurantDistance());
        holder.restaurantImageView.setImageBitmap(RestaurantInformation.StringToBitMap(
                myRestaurant.getRestaurantImageName()));
        if (myRestaurant.getColleagueList().size() != 0) {
            holder.restaurantPersonImageView.setImageDrawable( ContextCompat.getDrawable(
                    context, R.drawable.ic_outline_person_24));
            holder.restaurantPersonNumber.setText("(" + myRestaurant.getColleagueList().size() + ")");
        }

        holder.itemView.setOnClickListener((View view) -> {
            EventBus.getDefault().post(new OpenRestaurantEvent(myRestaurant));
        });
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
        ImageView restaurantImageView;
        @BindView(R.id.restaurant_person_image_view)
        ImageView restaurantPersonImageView;
        @BindView(R.id.restaurant_person_number)
        TextView restaurantPersonNumber;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}