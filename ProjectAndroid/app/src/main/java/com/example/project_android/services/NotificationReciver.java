package com.example.project_android.services;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project_android.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NotificationReciver extends BroadcastReceiver {
    int ID;
    String nombre;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            ID = bundle.getInt("ID", -1);
            nombre = bundle.getString("NAME");
            Log.d("TSM","Foco " + ID + " apagado (" + nombre + ")");
            ChangeStatusService1 statusService = new ChangeStatusService1("https://tsmpjgv9.000webhostapp.com/change_status.php", "OFF", 0);
            statusService.execute();
            Toast.makeText(context, "Foco " + ID + " apagado (" + nombre + ")", Toast.LENGTH_SHORT).show();
            if(ID==1){
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(0);
            }else {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(ID++);
            }

        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }


/*        private void updateStatus(String action, Integer status){
            ChangeStatusService1 statusService = new ChangeStatusService1("https://tsmpjgv9.000webhostapp.com/change_status.php", action, status);
            statusService.execute();
        }*/


    }

    class ChangeStatusService1 extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final String action;
        private final Integer status;

        private ChangeStatusService1(String urlWebService, String action, Integer status){
            this.urlWebService = urlWebService;
            this.action = action;
            this.status = status;
        }

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlWebService);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", ID);
                jsonParam.put("status", status);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                String message = conn.getResponseMessage();
                Log.d("STATUS", String.valueOf(conn.getResponseCode()));
                Log.d("MSG" , conn.getResponseMessage());

                conn.disconnect();
                return message;
            } catch (Exception e) {
                Log.d("DevicesActivity", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            try{super.onPostExecute(response);
                Log.d("ActionButton", "OK");
            }
            catch (Exception e) {
                Log.d("DevicesActivity", e.toString());
            }
        }
    }



}

