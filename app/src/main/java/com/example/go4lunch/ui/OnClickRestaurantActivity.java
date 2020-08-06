package com.example.go4lunch.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.RestaurantInformation;

public class OnClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private MyRestaurantModel myRestaurant;
    private ImageView restaurantPhoto;
    private TextView restaurantAddress;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        //Get restaurant data to know which restaurant the user clicked on
        myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra("Restaurant");
        // TODO: Bitmap is not serializable so getRestaurantImage return null
        restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
        restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                myRestaurant.getRestaurantImageName()));
        restaurantAddress= findViewById(R.id.detail_restaurant_address);
        restaurantAddress.setText(myRestaurant.getRestaurantAddress());

    }
}