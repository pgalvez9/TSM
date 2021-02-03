package com.example.project_android.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.project_android.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class EarthquakeLocationService extends Service {

    private volatile boolean isConsulting = false;
    private static final String TAG = "EarthquakeService";
    private Runnable runnable;
    private Handler handler;

    private FusedLocationProviderClient fusedLocationClient;
    private SharedPreferences sharedPref;

    public EarthquakeLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sharedPref = this.getSharedPreferences(
                getString(R.string.preferences_file_key), Context.MODE_PRIVATE);

        HandlerThread handlerThread = new HandlerThread("EarthQuakeThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isConsulting) {
                    isConsulting = true;
                    GETEarquakeStatus getEarquakeStatus = new GETEarquakeStatus("https://tsmpjgv9.000webhostapp.com/temblor.php");
                    getEarquakeStatus.execute();
                }

                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }

                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 20000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        Log.d(TAG, "onDestroy: Service Destroyed");
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            compareLocations(location);
                        }
                    }
                });
    }

    private void compareLocations(Location actualLoc){
        float maxDistance = 1.0f;
        double savedLat = Double.parseDouble(sharedPref.getString(getString(R.string.preferences_actual_lat), "0"));
        double savedLong = Double.parseDouble(sharedPref.getString(getString(R.string.preferences_actual_long), "0"));
        Location savedLocation = new Location("saved");
        savedLocation.setLatitude(savedLat);
        savedLocation.setLongitude(savedLong);
        float distance = actualLoc.distanceTo(savedLocation);
        Log.d(TAG, "Latitude: "+((Double) actualLoc.getLatitude()).toString());
        Log.d(TAG, "Longitud: "+((Double) actualLoc.getLongitude()).toString());

        Log.d(TAG, String.format("%.2f", distance / 1000) + "km");

        if (0 < Float.compare(distance/1000, maxDistance)){
            ChangeStatusService changeStatusService = new ChangeStatusService(8, "https://tsmpjgv9.000webhostapp.com/change_status.php");
            changeStatusService.execute();
        }
    }

    class GETEarquakeStatus extends AsyncTask<Void, Void, String> {

        private final String urlWebService;

        private GETEarquakeStatus(String urlWebService){
            this.urlWebService = urlWebService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlWebService);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String response;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response).append("\n");
                }
                bufferedReader.close();

                Log.d("DescriptionDevice", stringBuilder.toString().trim());
                conn.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                Log.d("DescriptionDevice", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d(TAG, "onPostExecute: "+ response);
            if ("[{\"temblando\":\"1\"}]".equals(response)){
                ChangeStatusService changeStatusService = new ChangeStatusService(1, "https://tsmpjgv9.000webhostapp.com/change_status.php");
                changeStatusService.execute();
                try {
                    Alert alert1= new Alert();
                    Method m=Alert.class.getDeclaredMethod("flash_effect");
                    m.setAccessible(true);
                    m.invoke(alert1);
                    Log.d("TSM", " Alerta recibida");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            isConsulting = false;
        }
    }

    class ChangeStatusService extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final int ID;

        private ChangeStatusService(int id, String urlWebService){
            this.urlWebService = urlWebService;
            this.ID = id;
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
                jsonParam.put("status", 1);
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
            super.onPostExecute(response);
            if (response.equals("OK") && ID == 1){

            }
            else {
                Toast.makeText(getBaseContext(), "Radio encendida", Toast.LENGTH_LONG).show();
            }

        }
    }


}