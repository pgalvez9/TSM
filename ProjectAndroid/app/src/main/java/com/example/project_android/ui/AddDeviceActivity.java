package com.example.project_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_android.R;
import com.example.project_android.dataClases.DeviceInfo;
import com.example.project_android.ui.adapter.DevicesAdapter;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddDeviceActivity extends AppCompatActivity {
    private Integer LAST_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Intent intent = getIntent();
        LAST_ID = intent.getIntExtra("LAST_ID", 0);

        EditText etName = findViewById(R.id.etName);
        Button btnAddDevice = findViewById(R.id.buttonAddID);
        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etName.getText().toString().isEmpty()){
                    AddDevice addDevice =
                            new AddDevice("https://tsmpjgv9.000webhostapp.com/new_device.php",
                                    etName.getText().toString());
                    addDevice.execute();

                }
            }
        });
    }

    class AddDevice extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final String name;

        private AddDevice(String urlWebService, String name){
            this.urlWebService = urlWebService;
            this.name = name;
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
                StringBuilder sb = new StringBuilder();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", LAST_ID+1);
                jsonParam.put("name", name);
                jsonParam.put("status", 0);
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
            if (response.equals("OK")){
                LAST_ID++;
                Toast.makeText(getBaseContext(), "Dispositivo agregado", Toast.LENGTH_LONG).show();
            }
        }
    }
}