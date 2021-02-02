<<<<<<< HEAD
package com.example.project_android.services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.project_android.R;
import com.example.project_android.ui.DevicesActivity;

public class NotificationService extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;
    private Integer ID;
    private String string;


    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("SERVICE");
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {
        try {
            Bundle bundle = intent2.getExtras();
            ID = bundle.getInt("ID", -1);
            string=bundle.getString("NAME");
            String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name);
            Context context = this.getApplicationContext();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Intent mIntent = new Intent(this, DevicesActivity.class);
            Resources res = this.getResources();
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            String message = "Apagar";
            //String title = "Recordatorio";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final int NOTIFY_ID = 0; // ID of notification
                String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
                String title = NOTIFICATION_CHANNEL_ID; // Default Channel
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
                NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notifManager == null) {
                    notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(context, id);
                Intent intent = new Intent(this, DevicesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(this, ID, intent, 0);

                Intent broadcastIntent = new Intent(this, NotificationReciver.class); //*******
                broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                broadcastIntent.putExtra("ID", ID);
                broadcastIntent.putExtra("NAME", bundle.getString("NAME"));

                PendingIntent actionIntent=PendingIntent.getBroadcast(this,100+ID,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT); //******


                builder.setContentTitle("Recordatorio").setCategory(Notification.CATEGORY_SERVICE)
                        .setSmallIcon(R.drawable.alarmaoff)   // required
                        .setContentText(message+" "+string)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.alarmaoff))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)  //*******
                        .setColor(Color.BLUE)
                        .setSound(soundUri)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                builder.setContentIntent(pendingIntent);
                builder.addAction(R.drawable.alarmaoff, "Apagar " + string, actionIntent);


                Notification notification = builder.build();
                if(ID==1){
                    notifManager.notify(NOTIFY_ID, notification);
                }else {
                    notifManager.notify(ID++, notification);
                }

                startForeground(1, notification);

            } else {
                pendingIntent = PendingIntent.getActivity(context, ID, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent intent = new Intent(this, DevicesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                notification = new NotificationCompat.Builder(this)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.alarmaoff)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.alarmaoff))
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentTitle("Recordatorio").setCategory(Notification.CATEGORY_SERVICE)
                        .setContentText(message).build();
                notificationManager.notify(ID, notification);

            }
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }


    }
}
=======
package com.example.project_android.services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.project_android.R;
import com.example.project_android.ui.DevicesActivity;

public class NotificationService extends IntentService {

    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    Notification notification;
    private Integer ID;
    private String string;


    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super("SERVICE");
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent2) {
        try {
            Bundle bundle = intent2.getExtras();
            ID = bundle.getInt("ID", -1);
            string=bundle.getString("NAME");
            String NOTIFICATION_CHANNEL_ID = getApplicationContext().getString(R.string.app_name);
            Context context = this.getApplicationContext();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Intent mIntent = new Intent(this, DevicesActivity.class);
            Resources res = this.getResources();
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            String message = "Apagar";
            //String title = "Recordatorio";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final int NOTIFY_ID = 0; // ID of notification
                String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
                String title = NOTIFICATION_CHANNEL_ID; // Default Channel
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder;
                NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notifManager == null) {
                    notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(context, id);
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent intent = new Intent(this, DevicesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(this, ID, intent, 0);

                builder.setContentTitle("Recordatorio").setCategory(Notification.CATEGORY_SERVICE)
                        .setSmallIcon(R.drawable.alarmaoff)   // required
                        .setContentText(message+" "+string)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.alarmaoff))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                        builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                if(ID==1){
                    notifManager.notify(NOTIFY_ID, notification);
                }else {
                    notifManager.notify(ID++, notification);
                }

                startForeground(1, notification);

            } else {
                pendingIntent = PendingIntent.getActivity(context, ID, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent intent = new Intent(this, DevicesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                notification = new NotificationCompat.Builder(this)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.alarmaoff)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.alarmaoff))
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentTitle("Recordatorio").setCategory(Notification.CATEGORY_SERVICE)
                        .setContentText(message).build();
                notificationManager.notify(ID, notification);

            }
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }


    }
}
>>>>>>> origin/master
