package com.trulyfuture.seklo.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.trulyfuture.seklo.MainActivity;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.screens.home.HomeFragment;
import com.trulyfuture.seklo.screens.login.LoginActivity;

import org.json.JSONObject;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Log.d("FIREBASE NOTIFICATION", "onMessageReceived: Message Received: \n" +
                "Title: " + title + "\n" +
                "Message: " + message);

   //     sendNotification(title,message);*/

        String ID = "";
        String title, body;
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "" + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                title = data.getJSONObject("notification").getString("title");

                body = data.getJSONObject("notification").getString("body");

                Log.v("Notification", title + "/n" + body);


            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
        }
        if (remoteMessage.getNotification() != null) {
            String title1 = remoteMessage.getNotification().getTitle();
            String message1 = remoteMessage.getNotification().getBody();
            String ClickAction = remoteMessage.getNotification().getClickAction();
            //    Toast.makeText(this, "title"+title1+","+"notify", Toast.LENGTH_SHORT).show();
//            Log.d("title1", title1);
//            Log.d("message", message1);
//            Log.d("ClickAction", ClickAction);
            createNotification(title1, message1, ClickAction, ID);
        }


    }

    @Override
    public void onDeletedMessages() {

    }

    private void createNotification(String title, String messageBody, String ClickAction, String ID) {


        Intent intent;
//        if(ClickAction.equals("UserHome")){
//            intent = new Intent(this,UserHome.class);
//            intent.putExtra("ID",ID);
//            intent.putExtra("Notification",true);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }
//        else{
//            intent = new Intent(this,Splash.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }

        Intent activityIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, activityIntent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Job Notification", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("You will receive all job notifications here");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_app_logo)
                .setTicker(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setContentInfo("Info")
                .setContentIntent(pendingIntent);

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
