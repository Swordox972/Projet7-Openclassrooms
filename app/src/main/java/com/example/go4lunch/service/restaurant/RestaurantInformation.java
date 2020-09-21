package com.example.go4lunch.service.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.go4lunch.api.RestaurantFirebaseHelper;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.colleague.ColleagueChoice;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RestaurantInformation {

    static String restaurantOpeningHours;
    public static String restaurantId1;
    public static String restaurantId2;
    public static String restaurantName1;
    public static String restaurantName2;
    public static LatLng restaurantLatLng1;
    public static LatLng restaurantLatLng2;
    public static boolean firstRestaurant;
    public static boolean secondRestaurant;
    public static List<Colleague> emptyColleagueList = new ArrayList<>();

    public static void getRestaurantInformation(PlacesClient placesClient, List<String> placeIdList
            , LatLng myCurrentLatLng) {

        // Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.TYPES,
                Place.Field.PHOTO_METADATAS, Place.Field.LAT_LNG, Place.Field.PHONE_NUMBER,
                Place.Field.WEBSITE_URI);

        //Do a loop for place id
        for (int i = 0; i < placeIdList.size(); i++) {
            // Construct a request object, passing the place ID and fields array.
            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeIdList.get(i), placeFields);
            //Fetch place
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                Log.i("success", "Place found: " + place.getName() + place.getAddress()
                        + place.getOpeningHours());

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
                    //Take restaurant information
                    String restaurantId = place.getId();

                    String restaurantName = place.getName();

                    String restaurantAddressNotFormated = place.getAddress();
                    String restaurantAddress= restaurantAddressNotFormated.substring(0,
                            restaurantAddressNotFormated.indexOf(","));

                    //Get opening hours
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek >= 1) {
                        if (place.getOpeningHours() != null && dayOfWeek != 1) {
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

                    // Get the phone number
                    String restaurantPhoneNumber = place.getPhoneNumber();
                    //Get bitmap to add in myRestaurantModel
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                    String bitmapName = bitmapToString(bitmap);
                    //Get the website
                     String restaurantWebsite;
                    if (place.getWebsiteUri() != null) {
                        restaurantWebsite = place.getWebsiteUri().toString();
                    } else {
                        restaurantWebsite = null;
                    }

                    //Create a new MyRestaurantModel and add it in the Singleton's list
                   final MyRestaurantModel restaurant;
                    //First restaurant
                    if (!firstRestaurant) {
                        restaurantId1 = place.getId();
                        restaurantName1 = place.getName();
                        restaurantLatLng1 = place.getLatLng();
                        restaurant = new MyRestaurantModel(restaurantId, restaurantName,
                                restaurantAddress, restaurantOpeningHours, restaurantDistance,
                                bitmapName, restaurantPhoneNumber, restaurantWebsite, false,
                                ColleagueChoice.setScarlettAndHughJoining(), 7);
                        firstRestaurant = true;
                        //Second restaurant
                    } else if (firstRestaurant && !secondRestaurant) {
                        restaurantId2 = place.getId();
                        restaurantName2 = place.getName();
                        restaurantLatLng2 = place.getLatLng();
                        restaurant = new MyRestaurantModel(restaurantId, restaurantName,
                               restaurantAddress, restaurantOpeningHours, restaurantDistance,
                                bitmapName, restaurantPhoneNumber, restaurantWebsite, false,
                                ColleagueChoice.setNanaAndGodfreyJoining(), 5);
                        secondRestaurant = true;
                        //Other restaurants
                    } else {
                        restaurant = new MyRestaurantModel(restaurantId, restaurantName,
                                restaurantAddress, restaurantOpeningHours, restaurantDistance,
                                bitmapName, restaurantPhoneNumber, restaurantWebsite, false,
                                emptyColleagueList, 2);
                    }

                    RestaurantFirebaseHelper.getRestaurantFirebase(restaurantId)
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                             DocumentSnapshot document= task.getResult();
                            boolean documentExists = document.exists();
                            if(documentExists){
                                restaurant.setLikeNumber(document.getLong("likeNumber"));
                            } else {
                                //Set RestaurantFirebase to firebase
                                RestaurantFirebaseHelper.createRestaurantFirebase(restaurant.getRestaurantId(),
                                        restaurant.getLikeNumber());
                            }

                            Restaurants.getInstance().getMyRestaurantList().add(restaurant);
                        }
                      });

                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        Log.e("error", "Place not found: " + exception.getMessage());
                        final int statusCode = apiException.getStatusCode();
                        Log.e("error", "status code: " + statusCode);
                    }
                });

                //On failure
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    final ApiException apiException = (ApiException) exception;
                    Log.e("error", "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    Log.e("error", "status code: " + statusCode);
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

