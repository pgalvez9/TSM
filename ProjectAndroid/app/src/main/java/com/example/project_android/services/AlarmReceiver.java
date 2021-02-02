<<<<<<< HEAD
package com.example.project_android.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {
    Context context1;
    private Integer ID;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            ID = bundle.getInt("ID", -1);
            Intent service1 = new Intent(context, NotificationService.class);
            service1.putExtra("ID", ID);
            service1.putExtra("NAME", bundle.getString("NAME"));
            service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            ContextCompat.startForegroundService(context, service1);
            Log.d("TSM", " Alarma recibida");
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }
    }

}

=======
package com.example.project_android.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {
    Context context1;
    private Integer ID;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            ID = bundle.getInt("ID", -1);
            Intent service1 = new Intent(context, NotificationService.class);
            service1.putExtra("ID", ID);
            service1.putExtra("NAME", bundle.getString("NAME"));
            service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            ContextCompat.startForegroundService(context, service1);
            Log.d("TSM", " Alarma recibida");
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }
    }

}

>>>>>>> origin/master
