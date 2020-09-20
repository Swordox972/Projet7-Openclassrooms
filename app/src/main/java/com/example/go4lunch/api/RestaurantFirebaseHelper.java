package com.example.go4lunch.api;

import com.example.go4lunch.model.MyRestaurantFirebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantFirebaseHelper {
    private static final String COLLECTION_NAME= "RestaurantFirebase";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getRestaurantFirebaseCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
    // --- CREATE ---
    public static Task<Void> createRestaurantFirebase(String id, int likeNumber) {
        MyRestaurantFirebase  myRestaurantFirebase= new MyRestaurantFirebase(id, likeNumber);
        return RestaurantFirebaseHelper.getRestaurantFirebaseCollection().document(id)
                .set(myRestaurantFirebase);
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getRestaurantFirebase(String id) {
        return RestaurantFirebaseHelper.getRestaurantFirebaseCollection().document(id).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateRestaurantFirebase(String id, int likeNumber) {
        return RestaurantFirebaseHelper.getRestaurantFirebaseCollection().document(id)
                .update("likeNumber", likeNumber);
    }

    // --- DELETE ---

    public static Task<Void> deleteRestaurantFirebase(String id) {
        return RestaurantFirebaseHelper.getRestaurantFirebaseCollection().document(id).delete();
    }
}
