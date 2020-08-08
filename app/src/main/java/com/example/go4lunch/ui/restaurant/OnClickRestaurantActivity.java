package com.example.go4lunch.ui.restaurant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.RestaurantInformation;

public class OnClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private MyRestaurantModel myRestaurant;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantPhoto;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_restaurant);

        //Get restaurant data to know which restaurant the user clicked on
        if ((MyRestaurantModel) getIntent().getSerializableExtra("Restaurant") != null) {
            myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra("Restaurant");

            restaurantName = findViewById(R.id.detail_restaurant_name);
            restaurantName.setText(myRestaurant.getRestaurantName());
            restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
            restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                    myRestaurant.getRestaurantImageName()));
            restaurantAddress = findViewById(R.id.detail_restaurant_address);
            restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        }

        if ((MyRestaurantModel) getIntent().getSerializableExtra("MapsActivityRestaurant") !=
        null) {
            myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra("MapsActivityRestaurant");

            restaurantName = findViewById(R.id.detail_restaurant_name);
            restaurantName.setText(myRestaurant.getRestaurantName());
            restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
            restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                    myRestaurant.getRestaurantImageName()));
            restaurantAddress = findViewById(R.id.detail_restaurant_address);
            restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        }

    }

    @Override
    protected void onStop() {
        myRestaurant= null;
        super.onStop();
    }
}