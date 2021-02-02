package com.example.project_android.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project_android.R;

public class MessageActivity extends AppCompatActivity {

    Button ButtonSend;
    EditText editTextPhone, editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if(ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MessageActivity.this, Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(MessageActivity.this, new String[]{Manifest.permission.SEND_SMS},1);
            }
            else
            {
                ActivityCompat.requestPermissions(MessageActivity.this, new String[]{Manifest.permission.SEND_SMS},1);
            }

        }
        else
        {

        }

        ButtonSend = findViewById(R.id.buttonSendID);
        editTextPhone = findViewById(R.id.editTextNumberID);
        editTextMessage = findViewById(R.id.editTextSmsID);

        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editTextPhone.getText().toString();
                String sms = editTextMessage.getText().toString();

                try
                {
                    SmsManager smsManager =  SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(MessageActivity.this, "Enviado", Toast.LENGTH_SHORT).show();
                    editTextPhone.setText("");
                    editTextMessage.setText("");
                }
                catch (Exception e)
                {
                    Toast.makeText(MessageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void OnRequestPermissionResult(int requesCode, String[] permissions, int[] grantResults)
    {
        switch (requesCode)
        {
            case 1:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(MessageActivity.this, "Permiso Otorgado", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MessageActivity.this, "Permiso Denegado", Toast.LENGTH_SHORT).show();
                    }
                }return;
            }
        }
    }
}