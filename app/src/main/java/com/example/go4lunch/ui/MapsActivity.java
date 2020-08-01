package com.example.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.Restaurants;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.libraries.places.api.model.Place.Type.RESTAURANT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String API_KEY = BuildConfig.API_KEY;
    PlacesClient placesClient;
    private static final int RC_LOCATION = 10;
    LatLng myLatLng;
    private BottomNavigationView bottomNavigationView;
    //Stock place id
    List<String> placeIdList;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_view);



        mapFragment.getMapAsync(this);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), API_KEY);

        //Initialize placeIdList
        placeIdList= new ArrayList<>();

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this);
imageView= findViewById(R.id.restaurant_imageview);
        getCurrentLocation();


        //Initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Clear restaurant list while changing fragment
                        Restaurants.getInstance().getMyRestaurantList().clear();
                        startActivity(intent);
                        break;

                    case R.id.page_2:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                                new RestaurantFragment()).commit();

                        break;
                    case R.id.page_3:
                        //Clear restaurant list while changing fragment
                        Restaurants.getInstance().getMyRestaurantList().clear();
                        break;
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
        List<Place.Field> placeFields = Arrays.asList( Place.Field.ID,Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.TYPES);
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

                            //Get restaurant place id and stock it in placeId list
                            String placeId= placeLikelihood.getPlace().getId();
                            placeIdList.add(placeId);
                            //Get latitude and longitude
                            myLatLng = placeLikelihood.getPlace().getLatLng();

                            //Move camera to the first restaurant found
                            if (!firstRestaurantFound) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15));
                                firstRestaurantFound = true;
                            }

                            MarkerOptions options = new MarkerOptions()
                                    .position(myLatLng)
                                    .title(placeLikelihood.getPlace().getName());

                            mMap.addMarker(options);


                        }
                    }
                    //Call method to get restaurant information
                    getRestaurantInformation();
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

    private void getRestaurantInformation() {

// Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.BUSINESS_STATUS, Place.Field.TYPES,
                Place.Field.PHOTO_METADATAS);

        //Do a loop for place id
        for(int i=0; i<placeIdList.size(); i++) {
        // Define a Place ID.

// Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeIdList.get(i), placeFields);
//Fetch place
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i("success", "Place found: " + place.getName() + place.getAddress()
                    + place.getOpeningHours());

            //Take restaurant information
           String restaurantName= place.getName();
           String restaurantAddress= place.getAddress();

            // Get the photo metadata.
            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                Log.w("success", "No photo metadata.");
                return;
            }
            final PhotoMetadata photoMetadata = metadata.get(0);

            // Get the attribution text.
            final String attributions = photoMetadata.getAttributions();

            // Create a FetchPhotoRequest.
            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(300) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build();
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                //reset bitmap value;
                Bitmap bitmap = fetchPhotoResponse.getBitmap();

                 //Create a new MyRestaurantModel and add it in the Singleton's list
                MyRestaurantModel restaurant = new MyRestaurantModel(restaurantName,
                        restaurantAddress.substring(0, restaurantAddress.indexOf(", Le Diamant")),
                        place.getBusinessStatus().toString(), "210m", bitmap);

                Restaurants.getInstance().getMyRestaurantList().add(restaurant);
                //reset restaurant value
                restaurant= null;
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e("error", "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                }
            });


        //On failure
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e("error", "Place not found: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();
                // TODO: Handle error with given status code.
            }
        });
    }}
    }


