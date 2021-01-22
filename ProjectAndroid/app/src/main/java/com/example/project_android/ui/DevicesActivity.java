package com.example.project_android.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project_android.R;
import com.example.project_android.dataClases.DeviceInfo;
import com.example.project_android.services.EarthquakeService;
import com.example.project_android.ui.adapter.DevicesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class DevicesActivity extends AppCompatActivity {

    private List<DeviceInfo> listDevices = new ArrayList<>();
    private RecyclerView recyclerView;
    private DevicesAdapter devicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        Intent intentEarthquake = new Intent(this, EarthquakeService.class);
        startService(intentEarthquake);

        FloatingActionButton fabAddDevice = findViewById(R.id.fabAddDevice);
        fabAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddDeviceActivity.class);
                intent.putExtra("LAST_ID", listDevices.size());
                startActivity(intent);
            }
        });

        FloatingActionButton regex = findViewById(R.id.voice);
        regex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿En qué te puedo ayudar");

                try {
                    startActivityForResult(intent,1000);
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), " "+e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }

        });

        recyclerView = findViewById(R.id.rvDevicesList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
            {
                if(resultCode == RESULT_OK && null!= data)
                {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String m = result.get(0).toLowerCase();
                    boolean prende= Pattern.matches("enciende.*|prende.*|activar.*|arranca.*|encender.*|ilumina.*", m);
                    if(prende)
                    {
                        //Aquí iría lo de enviar a la base de dato qué cuarto prender
                        Toast.makeText(getBaseContext(), " Se ha prendido",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        boolean apagar= Pattern.matches("apaga.*|desactiva.*|quita.*|",m);
                        if(apagar)
                        {
                            //Aquí iría lo de enviar a la base de datos qué cuarto apagar
                            Toast.makeText(getBaseContext(), " Se ha apagado",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetDevices getDevices = new GetDevices("https://tsmpjgv9.000webhostapp.com/get_devices.php", this);
        getDevices.execute();
    }

    class GetDevices extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final Context context;

        private GetDevices(String urlWebService, Context context){
            this.urlWebService = urlWebService;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlWebService);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json).append("\n");
                }
                Log.d("DevicesActivity", sb.toString().trim());
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d("DevicesActivity", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null){
                listDevices.clear();
                GsonBuilder gsonBuilder = new GsonBuilder();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++){
                        listDevices.add(gsonBuilder.create().fromJson(jsonArray.getString(i), DeviceInfo.class));
                    }
                    if (devicesAdapter != null){
                        devicesAdapter.updateDevices(listDevices);
                    }
                    else{
                        devicesAdapter = new DevicesAdapter(context, listDevices);
                        recyclerView.setAdapter(devicesAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}