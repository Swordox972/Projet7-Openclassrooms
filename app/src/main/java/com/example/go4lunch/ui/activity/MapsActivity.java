package com.example.go4lunch.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.restaurant.RestaurantInformation;
import com.example.go4lunch.service.restaurant.Restaurants;
import com.example.go4lunch.ui.fragment.ColleagueFragment;
import com.example.go4lunch.ui.fragment.RestaurantFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.libraries.places.api.model.Place.Type.RESTAURANT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //Initialize variables
    public static GoogleMap mMap;
    private String API_KEY = BuildConfig.API_KEY;
    PlacesClient placesClient;
    private static final int RC_LOCATION = 10;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static LatLng restaurantLatLng;
    LatLng myCurrentLatLng;
    String hungry;
    String availableWorkmates;
    public Place placeSearch;
    //Stock place id
    List<String> placeIdList;
    private List<MyRestaurantModel> singletonListRestaurant;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.maps_container)
    RelativeLayout relativeLayout;
    @BindView(R.id.fao_current_location)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.autocomplete_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_dehaze)
    ImageButton toolbarMenu;
    @BindView(R.id.toolbar_search)
    ImageButton toolbarSearch;
    @BindView(R.id.toolbar_text)
    TextView toolbarTitle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        hungry = getResources().getString(R.string.im_hungry);
        availableWorkmates = getString(R.string.available_workmates);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_view);

        mapFragment.getMapAsync(this);
        // Initialize the SDK
        Places.initialize(getApplicationContext(), API_KEY);
        // Create a new PlacesClient instance
        placesClient = Places.createClient(this);
        //Initialize placeIdList
        placeIdList = new ArrayList<>();

        singletonListRestaurant = Restaurants.getInstance().getMyRestaurantList();

        //Initialize toolbar
        initializeAutocompleteToolbar();
        //Initialize floating action button
        floatingActionButton.setOnClickListener((View view) -> {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLatLng, 15));
        });
        initializeBottomNavigation();
        initializeNavigationViewHeader();
        initializeNavigationViewIconsAction();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        singletonListRestaurant.size();
        mMap = googleMap;
        getCurrentLocation();
    }


    private void getCurrentLocation() {
        Restaurants.getInstance().getMyRestaurantList().clear();
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.TYPES, Place.Field.PHOTO_METADATAS);
        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    boolean firstLocation = true;
                    boolean firstRestaurantFound = false;
                    boolean secondRestaurantFound = false;
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Log.i("success", String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        //Get my current latitude and longitude
                        if (firstLocation) {
                            myCurrentLatLng = placeLikelihood.getPlace().getLatLng();
                            firstLocation = false;
                        }

                        final Place.Type lookingFor = RESTAURANT; // Place.Type.RESTAURANT
                        // if latitude and longitude aren't null and if the place type is RESTAURANT
                        final List<Place.Type> types = placeLikelihood.getPlace().getTypes();
                        Log.d("TYPES", types.toString());
                        if (placeLikelihood.getPlace().getLatLng() != null && types.contains(lookingFor)) {

                            //Get restaurant place id and stock it in placeId list
                            String placeId = placeLikelihood.getPlace().getId();
                            placeIdList.add(placeId);

                            //Get latitude and longitude of the first restaurant
                            restaurantLatLng = placeLikelihood.getPlace().getLatLng();

                            /*Move camera to the first restaurant found, setFirstRestaurantFound
                             to true and set marker selected to the first and second restaurant */
                            //Add marker with mMap
                            if (!firstRestaurantFound &&
                                    placeLikelihood.getPlace().getPhotoMetadatas() != null) {
                                firstRestaurantFound = true;

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLatLng, 15));
                                MarkerOptions options = new MarkerOptions()
                                        .position(MapsActivity.restaurantLatLng)
                                        .icon(BitmapDescriptorFactory.fromResource
                                                (R.drawable.marker_restaurant_selected))
                                        .title(placeLikelihood.getPlace().getName());
                                mMap.addMarker(options);


                            } else if (firstRestaurantFound && !secondRestaurantFound &&
                                    placeLikelihood.getPlace().getPhotoMetadatas() != null) {
                                secondRestaurantFound = true;
                                //Add marker selected to second restaurant
                                MarkerOptions options = new MarkerOptions()
                                        .position(MapsActivity.restaurantLatLng)
                                        .icon(BitmapDescriptorFactory.fromResource
                                                (R.drawable.marker_restaurant_selected))
                                        .title(placeLikelihood.getPlace().getName());
                                mMap.addMarker(options);

                            } else if (firstRestaurantFound && secondRestaurantFound &&
                                    placeLikelihood.getPlace().getPhotoMetadatas() != null) {
                                MarkerOptions options = new MarkerOptions()
                                        .position(MapsActivity.restaurantLatLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurant))
                                        .title(placeLikelihood.getPlace().getName());

                                mMap.addMarker(options);
                            }
                        }
                    }
                    //Call method to get restaurant information
                    RestaurantInformation.getRestaurantInformation(placesClient, placeIdList,
                            myCurrentLatLng);

                    //Set marker clickable to open OnClickRestaurantActivity
                    Intent intent = new Intent(this, OnClickRestaurantActivity.class);
                    //OnClick marker
                    mMap.setOnMarkerClickListener((Marker marker) -> {
                        for (int i = 0; i < singletonListRestaurant.size(); i++) {
                            MyRestaurantModel myRestaurantModel = singletonListRestaurant.get(i);
                            if (myRestaurantModel.getRestaurantName().equals(marker.getTitle())) {
                                intent.putExtra("MapsActivityRestaurant", myRestaurantModel);
                                startActivity(intent);
                                break;
                            }
                        }
                        return true;
                    });
                    //Clear placeIdList for not duplicate the restaurant list
                    placeIdList.clear();
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

    private void onSearchCalled() {
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void initializeAutocompleteToolbar() {
        toolbarTitle.setText(hungry);
        toolbarMenu.setOnClickListener((View view) -> drawerLayout.openDrawer(GravityCompat.START));
        toolbarSearch.setOnClickListener((View view) -> onSearchCalled());
    }

    private void initializeNavigationViewHeader() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        ImageView userImageView = navigationView.getHeaderView(0).findViewById(R.id.nav_user_image_view);
        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform())
                .into(userImageView);
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
    }
    private void initializeNavigationViewIconsAction() {
        //Load image in navigation view image view and dark blur it
        ImageView navBackgroundImageView = navigationView.getHeaderView(0)
                .findViewById(R.id.nav_background_image_view);
        navBackgroundImageView.setImageDrawable(ContextCompat.getDrawable
                (this, R.drawable.collegues_qui_mangent_650_blur));
        navBackgroundImageView.setColorFilter(0xff555555, PorterDuff.Mode.MULTIPLY);

        MenuItem yourLunch= navigationView.getMenu().findItem(R.id.your_lunch);
        yourLunch.setOnMenuItemClickListener(menuItem -> {
            finish();
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        });

        MenuItem settings= navigationView.getMenu().findItem(R.id.settings);
        settings.setOnMenuItemClickListener(menuItem -> {
            finish();
            Intent intent= new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        });

        MenuItem logOut = navigationView.getMenu().findItem(R.id.log_out);
        logOut.setOnMenuItemClickListener(menuItem -> {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                            startMainActivityIfUserLogOut();
                        }
                    });
            return true;
        });


    }

    private void startMainActivityIfUserLogOut() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializeBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) ->
        {
            Intent intent = new Intent(this, MapsActivity.class);

            switch (item.getItemId()) {
                case R.id.page_1:
                    startActivity(intent);
                    Restaurants.getInstance().getFilteredRestaurantList().clear();
                    toolbarSearch.setVisibility(View.VISIBLE);
                    //Reset boolean variables of RestaurantInformation
                    RestaurantInformation.firstRestaurant = false;
                    RestaurantInformation.secondRestaurant = false;
                    //Clear restaurant list while changing fragment
                    floatingActionButton.setVisibility(View.VISIBLE);
                    singletonListRestaurant.clear();
                    onBackPressed();
                    toolbarTitle.setText(hungry);
                    break;

                case R.id.page_2:
                    toolbarTitle.setText(hungry);
                    toolbarSearch.setVisibility(View.VISIBLE);
                    floatingActionButton.hide();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                            new RestaurantFragment()).commit();

                    break;
                case R.id.page_3:
                    floatingActionButton.hide();
                    toolbarSearch.setVisibility(View.GONE);
                    toolbarTitle.setText(availableWorkmates);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,
                            new ColleagueFragment()).commit();
                    break;
            }
            return true;
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                placeSearch = Autocomplete.getPlaceFromIntent(data);
                Log.i("success", "Place: " + placeSearch.getName() + ", " + placeSearch.getId());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeSearch.getLatLng(), 20));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("error", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}