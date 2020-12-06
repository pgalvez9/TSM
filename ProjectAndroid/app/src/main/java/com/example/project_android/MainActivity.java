package com.example.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.ImageFocoID);
        ButtonStatus(null);
    }

    public void ButtonStatus(View view)
    {
        getJSON("https://tsmpjgv9.000webhostapp.com/luz.php", "STATUS");
    }

    public void ButtonOn(View view)
    {
        getJSON("https://tsmpjgv9.000webhostapp.com/lon.php", "ON");
    }

    public void ButtonOff(View view)
    {
        getJSON("https://tsmpjgv9.000webhostapp.com/loff.php", "OFF");
    }

    private void getJSON(final String urlWebService, final String action) {

        GetJSON getJSON = new GetJSON(urlWebService, action);
        getJSON.execute();
    }

    class GetJSON extends AsyncTask<Void, Void, String> {

        private final String urlWebService;
        private final String action;

        private GetJSON(String urlWebService, String action){
            this.urlWebService = urlWebService;
            this.action = action;
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
                Log.d("MainActivity", sb.toString().trim());
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d("MainActivity", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            switch (action){
                case "ON":
                    imageView.setBackgroundResource(R.drawable.buttonon);
                    break;
                case "OFF":
                    imageView.setBackgroundResource(R.drawable.buttonoff);
                    break;
                case "STATUS":
                    if ("[{\"Status\":\"0\"}]".equals(response))
                        imageView.setBackgroundResource(R.drawable.buttonoff);
                    else if ("[{\"Status\":\"1\"}]".equals(response))
                        imageView.setBackgroundResource(R.drawable.buttonon);
                    break;
                default:
                    break;
            }
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        }
    }
}