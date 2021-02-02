package com.example.project_android.services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android.R;

public class SendAlert extends Activity implements LocationListener {

    protected LocationManager locationManager;

    private TextView number1, number2, number3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);


        number1 = findViewById(R.id.textNumberID1);
        number2 = findViewById(R.id.textNumberID2);
        number3 = findViewById(R.id.textNumberID3);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        String mNumber1 = mPreferences.getString(getString(R.string.number1), "");
        number1.setText(mNumber1);
        String mNumber2 = mPreferences.getString(getString(R.string.number1), "");
        number1.setText(mNumber2);
        String mNumber3 = mPreferences.getString(getString(R.string.number1), "");
        number1.setText(mNumber3);

        String[ ] PhoneNumbers = {mNumber1, mNumber2, mNumber3};



        if(ContextCompat.checkSelfPermission(SendAlert.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(SendAlert.this, Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(SendAlert.this, new String[]{Manifest.permission.SEND_SMS},1);
            }
            else
            {
                ActivityCompat.requestPermissions(SendAlert.this, new String[]{Manifest.permission.SEND_SMS},1);
            }

        }
        else
        {

        }


        for (int i=0;i<3;i=i++){



            try
            {
                SmsManager smsManager =  SmsManager.getDefault();
                smsManager.sendTextMessage(PhoneNumbers[i], null, "Mi ubicacion", null, null);
                Toast.makeText(SendAlert.this, "Enviado", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(SendAlert.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}