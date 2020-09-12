package com.example.go4lunch.service.Notifications;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Colleague;
import com.example.go4lunch.model.MyRestaurantModel;
import com.example.go4lunch.service.App;
import com.example.go4lunch.service.Users;
import com.example.go4lunch.service.restaurant.Restaurants;
import com.example.go4lunch.ui.activity.MainActivity;
import com.example.go4lunch.ui.activity.OnClickRestaurantActivity;

import java.util.List;


public class NotificationsService extends IntentService {
    private static final int NOTIFICATION_ID = 7;
    private static final String NOTIFICATION_TAG = "Go4Lunch";

    public NotificationsService() {
        super("NotificationsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String CHANNEL_ID= "channel_1";
        String textTitle= "Chosen restaurant";
        Colleague me;
        List<MyRestaurantModel> myRestaurantList= Restaurants.getInstance().getMyRestaurantList();
        MyRestaurantModel restaurantChosenByMe = new MyRestaurantModel();
        if (!Users.getInstance().getMyUserList().isEmpty()) {
         me = Users.getInstance().getMyUserList().get(0);
         //Get restaurant chosen by me
            for (int i = 0; i<myRestaurantList.size(); i++) {
                if (me.getColleagueChoice().equals(myRestaurantList.get(i).getRestaurantName())) {
                    restaurantChosenByMe= myRestaurantList.get(i);
                }
            }
            String participants = "";
            //Get colleague list of restaurant chosen by me and add colleagues in string
            for (int i=0; i<restaurantChosenByMe.getColleagueList().size(); i++) {
                if (i< restaurantChosenByMe.getColleagueList().size() - 1 &&
                        restaurantChosenByMe.getColleagueList().get(i).getColleagueName().equals(me.getColleagueName())) {
                 participants= restaurantChosenByMe.getColleagueList().get(i).getColleagueName() + ", ";
                }
                else if (i== restaurantChosenByMe.getColleagueLikeList().size() - 1 &&
                        restaurantChosenByMe.getColleagueList().get(i).getColleagueName().equals(me.getColleagueName())){
                    participants= restaurantChosenByMe.getColleagueList().get(i).getColleagueName();
                }
            }

        String messageBody= "You will eat at " + me.getColleagueChoice() + " at " +
                restaurantChosenByMe.getRestaurantAddress() + " with " + participants;

                sendVisualNotification(CHANNEL_ID, textTitle,  messageBody);
        }

    }

    public static void sendVisualNotification(String CHANNEL_ID, String textTitle, String messageBody) {

        // 1 - Create an Intent that will be shown when user will click on the Notification
        Context context= App.getContext().getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.bol_fumant)
                .setContentTitle(textTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        //Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) App.getContext().
                getSystemService(Context.NOTIFICATION_SERVICE);

        //Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Message showing the choosen restaurant and the colleague who" +
                    "come in this restaurant";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        //Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }

}
