package com.example.go4lunch.ui.restaurant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.RestaurantInformation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private MyRestaurantModel myRestaurant;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantPhoto;
    private FloatingActionButton floatingActionButton;
    private boolean isSelected=false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_restaurant);

        //Get restaurant data to know which restaurant the user clicked on
        if ((MyRestaurantModel) getIntent().getSerializableExtra("Restaurant") != null) {
            this.initializeRestaurant("Restaurant");
        }

        if ((MyRestaurantModel) getIntent().getSerializableExtra("MapsActivityRestaurant") !=
        null) {
            this.initializeRestaurant("MapsActivityRestaurant");
        }

        this.initializeFao();
    }

    @Override
    protected void onStop() {
        myRestaurant= null;
        super.onStop();
    }

    private void initializeRestaurant(String restaurantIntentCode) {

            myRestaurant= (MyRestaurantModel) getIntent().getSerializableExtra(restaurantIntentCode);

            restaurantName = findViewById(R.id.detail_restaurant_name);
            restaurantName.setText(myRestaurant.getRestaurantName());
            restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
            restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                    myRestaurant.getRestaurantImageName()));
            restaurantAddress = findViewById(R.id.detail_restaurant_address);
            restaurantAddress.setText(myRestaurant.getRestaurantAddress());

    }

    private void initializeFao() {

        Drawable fao_not_selected=ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_24, null );
        Drawable fao_selected=ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_selected_24, null);


        floatingActionButton= findViewById(R.id.fao_chose_restaurant);
        floatingActionButton.setOnClickListener((View view) -> {
            if (!isSelected ) {
                floatingActionButton.setImageDrawable(fao_selected);
                isSelected=true;
            } else {
                floatingActionButton.setImageDrawable(fao_not_selected);
                isSelected= false;
            }

        });
    }
}