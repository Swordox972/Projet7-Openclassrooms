package com.example.go4lunch.service.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;

import com.example.go4lunch.R;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.ui.MapsActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RestaurantInformation {

    static String restaurantOpeningHours;

    public static void getRestaurantInformation(PlacesClient placesClient, List<String> placeIdList
            , LatLng myCurrentLatLng) {

// Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.TYPES,
                Place.Field.PHOTO_METADATAS, Place.Field.LAT_LNG);

        //Do a loop for place id
        for (int i = 0; i < placeIdList.size(); i++) {
// Construct a request object, passing the place ID and fields array.
            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeIdList.get(i), placeFields);
//Fetch place
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                Log.i("success", "Place found: " + place.getName() + place.getAddress()
                        + place.getOpeningHours());

                //Take restaurant information
                String restaurantName = place.getName();
                String restaurantAddress = place.getAddress();

                //Get opening hours
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                if (dayOfWeek > 1) {
                    if (place.getOpeningHours() != null) {
                        restaurantOpeningHours = place.getOpeningHours().getWeekdayText().get(dayOfWeek - 2);
                    } else {
                        if (place.getOpeningHours() != null)
                            restaurantOpeningHours = place.getOpeningHours().getWeekdayText().get(dayOfWeek + 5);
                    }
                }

                //Get restaurant distance
                float[] results = new float[1];
                Location.distanceBetween(myCurrentLatLng.latitude,
                        myCurrentLatLng.longitude, place.getLatLng().latitude,
                        place.getLatLng().longitude, results);
                //format the distance value to be round
                String format = String.format("%.0f", results[0]);
                String restaurantDistance = format + "m";

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
                    //Get bitmap to add in myRestaurantModel
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                    String bitmapName = bitmapToString(bitmap);


                    //Create a new MyRestaurantModel and add it in the Singleton's list
                    MyRestaurantModel restaurant = new MyRestaurantModel(restaurantName,
                            restaurantAddress.substring(0, restaurantAddress.indexOf(",")),
                            restaurantOpeningHours, restaurantDistance, bitmapName);

                    Restaurants.getInstance().getMyRestaurantList().add(restaurant);

                    //reset restaurant value
                    restaurant = null;
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
        }
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}

