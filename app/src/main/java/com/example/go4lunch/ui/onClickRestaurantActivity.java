package com.example.go4lunch.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.RestaurantInformation;

public class onClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private MyRestaurantModel myRestaurant;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantPhoto;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        //Get restaurant data to know which restaurant the user clicked on
        myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra("Restaurant");

        restaurantName= findViewById(R.id.detail_restaurant_name);
        restaurantName.setText(myRestaurant.getRestaurantName());
        restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
        restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                myRestaurant.getRestaurantImageName()));
        restaurantAddress= findViewById(R.id.detail_restaurant_address);
        restaurantAddress.setText(myRestaurant.getRestaurantAddress());



    }
}