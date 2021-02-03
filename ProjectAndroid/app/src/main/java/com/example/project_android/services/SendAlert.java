package com.example.project_android.services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android.R;
import com.example.project_android.ui.DevicesActivity;

public class SendAlert extends Activity {

    protected LocationManager locationManager;

    private TextView number1, number2, number3, link;
    private SharedPreferences sharedPref;
    Button Buttonenviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);

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

        sharedPref = this.getSharedPreferences(
                getString(R.string.preferences_file_key), Context.MODE_PRIVATE);


        number1 = findViewById(R.id.textNumberID1);
        number2 = findViewById(R.id.textNumberID2);
        number3 = findViewById(R.id.textNumberID3);
        link = findViewById(R.id.linkID);
        Buttonenviar = findViewById(R.id.EnviarID);



        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEditor = mPreferences.edit();

        SharedPreferences.Editor editor = sharedPref.edit();

        //String LAT = mPreferences.getString(getString(R.string.preferences_actual_lat),"");
        //String LONG = mPreferences.getString(getString(R.string.preferences_actual_long),"");

        String mNumber1 = mPreferences.getString(getString(R.string.number1), "");
        number1.setText(mNumber1);
        String mNumber2 = mPreferences.getString(getString(R.string.number2), "");
        number2.setText(mNumber2);
        String mNumber3 = mPreferences.getString(getString(R.string.number3), "");
        number3.setText(mNumber3);

        //////////
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        //String LAT = mPreferences.getString(getString(R.string.preferences_actual_lat),"");
        //String LONG = mPreferences.getString(getString(R.string.preferences_actual_long),"");

        String LAT = String.valueOf(latitude);
        String LONG = String.valueOf(longitude);

        String MessageLink = "https://www.google.com/maps/search/?api=1&query="+LAT+","+LONG;

        link.setText(MessageLink);


        Buttonenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    SmsManager smsManager =  SmsManager.getDefault();
                    smsManager.sendTextMessage(mNumber1, null, MessageLink, null, null);
                    Toast.makeText(SendAlert.this, "Enviado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(SendAlert.this, "Error", Toast.LENGTH_SHORT).show();
                }

                try
                {
                    SmsManager smsManager =  SmsManager.getDefault();
                    smsManager.sendTextMessage(mNumber2, null, MessageLink, null, null);
                    Toast.makeText(SendAlert.this, "Enviado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(SendAlert.this, "Error", Toast.LENGTH_SHORT).show();
                }

                try
                {
                    SmsManager smsManager =  SmsManager.getDefault();
                    smsManager.sendTextMessage(mNumber3, null, MessageLink, null, null);
                    //Toast.makeText(SendAlert.this, "Enviado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(SendAlert.this, "Error", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getBaseContext(), DevicesActivity.class);
                Toast.makeText(SendAlert.this, "Enviado", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });


    }

}