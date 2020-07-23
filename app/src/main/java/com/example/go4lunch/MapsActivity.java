package com.example.go4lunch;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.libraries.places.api.model.Place.Type.RESTAURANT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private String API_KEY = BuildConfig.API_KEY;
    PlacesClient placesClient;
    private static final int RC_LOCATION = 10;
    LatLng latLng;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), API_KEY);

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this);

        getCurrentLocation();

        //Initialize bottom navigation
        bottomNavigationView= findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                switch (item.getItemId()) {
                    case R.id.page_1:
                        startActivity(intent);
                        break;

                    case R.id.page_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_fragment,
                                new RestaurantFragment()).commit();

                    case R.id.page_3:
                        startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    private void getCurrentLocation() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG,
                Place.Field.TYPES);

// Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();

                    boolean firstRestaurantFound = false;

                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Log.i("success", String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        final Place.Type lookingFor = RESTAURANT; // Place.Type.RESTAURANT
                        // if latitude and longitude aren't null and if the place type is RESTAURANT
                        final List<Place.Type> types = placeLikelihood.getPlace().getTypes();
                        Log.d("TYPES", types.toString());
                        if (placeLikelihood.getPlace().getLatLng() != null && types.contains(lookingFor)) {

                            latLng = placeLikelihood.getPlace().getLatLng();

                            //Move camera to the first restaurant found
                            if (!firstRestaurantFound) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                firstRestaurantFound = true;
                            }

                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .title(placeLikelihood.getPlace().getName());

                            mMap.addMarker(options);



                        }
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("error", "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        } else {
            getLocatePermission();

        }
    }


    private void getLocatePermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, RC_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RC_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                getLocatePermission();
            }
        }
    }



}
