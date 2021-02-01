package com.example.project_android.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

public class AlarmReceiver extends BroadcastReceiver {
    private Integer ID;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            ID = bundle.getInt("ID", -1);
            if(ID==1){
              Alert alert1= new Alert();
              Method m=Alert.class.getDeclaredMethod("flash_effect");
              m.setAccessible(true);
              m.invoke(alert1);
              Log.d("TSM", " Alerta recibida");
            }
            else {
                Intent service1 = new Intent(context, NotificationService.class);
                service1.putExtra("ID", ID);
                service1.putExtra("NAME", bundle.getString("NAME"));
                service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
                ContextCompat.startForegroundService(context, service1);
                Log.d("TSM", " Alarma recibida");
            }
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }
    }


}

