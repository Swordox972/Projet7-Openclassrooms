package com.example.go4lunch.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.go4lunch.R;
import com.example.go4lunch.di.DI;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.colleague.ColleagueApiService;
import com.example.go4lunch.service.colleague.ColleagueChoice;
import com.example.go4lunch.service.restaurant.RestaurantInformation;
import com.example.go4lunch.ui.fragment.OnClickRestaurantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class OnClickRestaurantActivity extends AppCompatActivity {
    //Initialize variables
    private MyRestaurantModel myRestaurant;
    private TextView restaurantName;
    private TextView restaurantAddress;
    private ImageView restaurantPhoto;
    private FloatingActionButton floatingActionButton;
    private ImageButton restaurantCall;
    private ImageButton restaurantWebsite;
    private boolean isSelected = false;
    Drawable fao_not_selected;
    Drawable fao_selected;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_restaurant);
        // commit fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.on_click_activity_fragment_container_view,
                new OnClickRestaurantFragment()).commit();

        //Get restaurant data to know which restaurant the user clicked on
        //From List View
        if ((MyRestaurantModel) getIntent().getSerializableExtra("Restaurant") != null) {
            this.initializeRestaurant("Restaurant");

        }
        //From MapView
        if ((MyRestaurantModel) getIntent().getSerializableExtra("MapsActivityRestaurant") !=
                null) {
            this.initializeRestaurant("MapsActivityRestaurant");
        }

        this.initializeFao();
    }

    @Override
    protected void onStop() {
        myRestaurant = null;
        super.onStop();
    }

    private void initializeRestaurant(String restaurantIntentCode) {

        myRestaurant = (MyRestaurantModel) getIntent().getSerializableExtra(restaurantIntentCode);

        restaurantName = findViewById(R.id.detail_restaurant_name);
        restaurantName.setText(myRestaurant.getRestaurantName());
        restaurantPhoto = findViewById(R.id.detail_restaurant_photo);
        restaurantPhoto.setImageBitmap(RestaurantInformation.StringToBitMap(
                myRestaurant.getRestaurantImageName()));
        restaurantAddress = findViewById(R.id.detail_restaurant_address);
        restaurantAddress.setText(myRestaurant.getRestaurantAddress());
        restaurantCall = findViewById(R.id.restaurant_call);

            restaurantCall.setOnClickListener((View view) -> {
                if (myRestaurant.getRestaurantPhoneNumber() != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + myRestaurant.getRestaurantPhoneNumber()));
                startActivity(intent);
                } else {
                    Toast.makeText(this, "No Phone number", Toast.LENGTH_SHORT).show();
                }
            });
        restaurantWebsite= findViewById(R.id.restaurant_website);
            restaurantWebsite.setOnClickListener((View view) -> {
                if (myRestaurant.getRestaurantWebsite() != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(myRestaurant.getRestaurantWebsite()));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No website", Toast.LENGTH_SHORT).show();
                }
            });

    }
    private void initializeFao() {

        fao_not_selected = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_24, null);
         fao_selected = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_circle_selected_24, null);

        floatingActionButton = findViewById(R.id.fao_chose_restaurant);
        floatingActionButton.setOnClickListener((View view) -> {
            if (!isSelected) {
                floatingActionButton.setImageDrawable(fao_selected);
                isSelected=true;

            } else {
                floatingActionButton.setImageDrawable(fao_not_selected);
                isSelected = false;
            }

        });
    }


}