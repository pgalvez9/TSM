package com.example.project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMS extends AppCompatActivity {

    Button ButtonSend;
    EditText editTextPhone1, editTextPhone2, editTextPhone3, editTextMessage;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s);

        ButtonSend = findViewById(R.id.buttonSendID);
        editTextPhone1 = findViewById(R.id.editTextNumber1ID);
        editTextPhone2 = findViewById(R.id.editTextNumber2ID);
        editTextPhone3 = findViewById(R.id.editTextNumber3ID);
        //editTextMessage = findViewById(R.id.editTextSmsID);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        /*

        if(ContextCompat.checkSelfPermission(SMS.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(SMS.this, Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(SMS.this, new String[]{Manifest.permission.SEND_SMS},1);
            }
            else
            {
                ActivityCompat.requestPermissions(SMS.this, new String[]{Manifest.permission.SEND_SMS},1);
            }

        }
        else
        {

        }

         */





        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String number = editTextPhone1.getText().toString();
                //String sms = editTextMessage.getText().toString();

                //save the number1
                String number1 = editTextPhone1.getText().toString();
                mEditor.putString(getString(R.string.number1), number1);
                mEditor.commit();

                //save the number2
                String number2 = editTextPhone2.getText().toString();
                mEditor.putString(getString(R.string.number2), number2);
                mEditor.commit();

                //save the number3
                String number3 = editTextPhone3.getText().toString();
                mEditor.putString(getString(R.string.number3), number3);
                mEditor.commit();

                /*
                try
                {
                    SmsManager smsManager =  SmsManager.getDefault();
                    smsManager.sendTextMessage(number1, null, sms, null, null);
                    Toast.makeText(SMS.this, "Enviado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(SMS.this, "Error", Toast.LENGTH_SHORT).show();
                }

                 */


            }
        });
    }

    /**
     * Check the shared preferences and set them accordingly
     */
    private void checkSharedPreferences(){

        String number1 = mPreferences.getString(getString(R.string.number1), "");
        String number2 = mPreferences.getString(getString(R.string.number2), "");
        String number3 = mPreferences.getString(getString(R.string.number3), "");


        editTextPhone1.setText(number1);
        editTextPhone2.setText(number2);
        editTextPhone3.setText(number3);

    }

}