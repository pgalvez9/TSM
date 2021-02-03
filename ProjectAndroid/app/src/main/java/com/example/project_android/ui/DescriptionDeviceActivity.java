package com.example.project_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_android.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DescriptionDeviceActivity extends AppCompatActivity {

    private ImageView imageView;
    private Integer ID;
    private TextView tvStatus;
    private SeekBar seekBar;
    private TextView textDimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvName = findViewById(R.id.textName);
        TextView tvID = findViewById(R.id.textNumberID);
        tvStatus = findViewById(R.id.textstatusID);
        imageView = findViewById(R.id.ImageFocoID);
        seekBar = findViewById(R.id.barra_1);
        textDimmer=findViewById(R.id.textDimmer);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            ID = bundle.getInt("ID", -1);
            tvName.setText("Dispositivo: " + bundle.getString("NAME"));
            tvID.setText("ID: " + ID);
            if (bundle.getInt("STATUS") == 1){
                imageView.setBackgroundResource(R.drawable.lighton);
                tvStatus.setText("Status: On");
            }
            else {
                imageView.setBackgroundResource(R.drawable.lightoff);
                tvStatus.setText("Status: Off");
            }
        }
        catch (Exception e){
            Log.d("DescriptionDevice", e.toString());
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private Integer progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textDimmer.setText("Nivel de luz: "+progress + "/1023");
                this.progress=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("STOP TRACKING","On stop tracking"+this.progress);
                changeIntensity(progress);
            }
        });
    }

    public void ButtonStatus(View view)
    {
        GetJSON getJSON = new GetJSON("https://tsmpjgv9.000webhostapp.com/luzPost.php");
        getJSON.execute();
    }

    public void ButtonOn(View view)
    {
        updateStatus("ON", 1);
    }

    public void ButtonOff(View view)
    {
        updateStatus("OFF", 0);
    }

    private void updateStatus(String action, Integer status){
        ChangeStatusService statusService
                = new ChangeStatusService("https://tsmpjgv9.000webhostapp.com/change_status.php", action, status);
        statusService.execute();
    }

    private void changeIntensity(Integer intensity){
        ChangeIntensityService intensityService = new ChangeIntensityService("https://tsmpjgv9.000webhostapp.com/setIntensity.php",intensity);
        intensityService.execute();
    }

    class ChangeStatusService extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final String action;
        private final Integer status;

        private ChangeStatusService(String urlWebService, String action, Integer status){
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
            super.onPostExecute(response);
            if (response.equals("OK")){
                if (action.equals("ON")){
                    imageView.setBackgroundResource(R.drawable.lighton);
                    tvStatus.setText("Status: On");
                }
                else{
                    imageView.setBackgroundResource(R.drawable.lightoff);
                    tvStatus.setText("Status: Off");
                }
            }

        }
    }

    class ChangeIntensityService extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final Integer intensity;

        private ChangeIntensityService(String urlWebService, Integer intensity){
            this.urlWebService = urlWebService;
            this.intensity = intensity;
        }

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlWebService);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", ID);
                jsonParam.put("intensidad", intensity);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
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
            Log.i("POST INTENSIDAD",""+response);
            if (response.equals("OK")){
                textDimmer.setText("Intensidad cambiada ("+this.intensity+"/250)");
            }
            else{
                textDimmer.setText("Error al cambiar la intensidad");
            }

        }
    }


    class GetJSON extends AsyncTask<Void, Void, String> {

        private final String urlWebService;

        private GetJSON(String urlWebService){
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
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", ID);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                Log.d("MSG" , conn.getResponseMessage());

                if (conn.getResponseMessage().equals("OK")){
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
                } else {
                    Log.e("DescriptionDevice", conn.getResponseMessage());
                    conn.disconnect();
                    return null;
                }

            } catch (Exception e) {
                Log.d("DescriptionDevice", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if ("[{\"status\":\"0\"}]".equals(response)){
                imageView.setBackgroundResource(R.drawable.lightoff);
                tvStatus.setText("Status: Off");
            }
            else if ("[{\"status\":\"1\"}]".equals(response)){
                imageView.setBackgroundResource(R.drawable.lighton);
                tvStatus.setText("Status: On");
            }
        }
    }
}
