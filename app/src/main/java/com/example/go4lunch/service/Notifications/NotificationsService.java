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
import com.example.go4lunch.service.Users;
import com.example.go4lunch.service.restaurant.Restaurants;
import com.example.go4lunch.ui.activity.MainActivity;

import java.util.List;


public class NotificationsService extends IntentService {
    private static final int NOTIFICATION_ID = 7;
    private static final String NOTIFICATION_TAG = "Go4Lunch";

    public NotificationsService() {
        super("NotificationsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String CHANNEL_ID = "channel_1";
        String textTitle = this.getApplicationContext().getString(R.string.chosen_restaurant);
        //Get the restaurant name chosen
        Colleague me;
        List<MyRestaurantModel> myRestaurantList = Restaurants.getInstance().getMyRestaurantList();
        MyRestaurantModel restaurantChosenByMe = new MyRestaurantModel();
        if (!Users.getInstance().getMyUserList().isEmpty()) {
            me = Users.getInstance().getMyUserList().get(0);
            //Get restaurant chosen by me
            for (int i = 0; i < myRestaurantList.size(); i++) {
                if (me.getColleagueRestaurantChoice().equals(myRestaurantList.get(i).getRestaurantName())) {
                    restaurantChosenByMe = myRestaurantList.get(i);
                }
            }
            String participants = "";
            //Get colleague list of restaurant chosen by me and add colleagues in string
            for (int i = 0; i < restaurantChosenByMe.getColleagueList().size(); i++) {
                if (i < restaurantChosenByMe.getColleagueList().size() - 1 &&
                        restaurantChosenByMe.getColleagueList().get(i).getColleagueName().equals(me.getColleagueName())) {
                    participants = restaurantChosenByMe.getColleagueList().get(i).getColleagueName() + ", ";
                } else if (i == restaurantChosenByMe.getColleagueList().size() - 1 &&
                        restaurantChosenByMe.getColleagueList().get(i).getColleagueName().equals(me.getColleagueName())) {
                    participants = restaurantChosenByMe.getColleagueList().get(i).getColleagueName();
                }
            }
            //Make the messageBody after retrieve the information of restaurant and colleague list
            String messageBody = this.getApplicationContext().getString(R.string.you_will_eat_at) +
                    " " + me.getColleagueRestaurantChoice() + " " + this.getApplicationContext()
                    .getString(R.string.at) + " " + restaurantChosenByMe.getRestaurantAddress() + " "
                    + this.getApplicationContext().getString(R.string.with) + participants;

            //Create a pendingIntent
            Context context = this.getApplicationContext();
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1,
                    PendingIntent.FLAG_ONE_SHOT);
            //Create Notification
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
            NotificationManager notificationManager = (NotificationManager) this.
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

}



