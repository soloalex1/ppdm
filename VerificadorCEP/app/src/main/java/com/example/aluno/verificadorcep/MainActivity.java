package com.example.aluno.verificadorcep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public JSONObject obterEndereco(String cep){
        JSONObject obj = null;

        if(cep.length() <= 8 && cep.matches("^[0-9]*$")){

            String cepToURL = "http://viacep.com.br/ws/" + cep + "/json/";

            try {
                URL url = new URL(cepToURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();

                InputStream is = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String data = null, content = "";

                while((data = reader.readLine()) != null){
                    content += data + "\n";
                }

                obj = new JSONObject(content);
                return obj;


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
}
