package com.example.aluno.navegador;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String sharedText = "";

        try {

            Intent it = this.getIntent();
            if (Intent.ACTION_SEND.equals(it.getAction()) && it.getType() != null) {
                if ("text/plain".equals(it.getType())) {
                    sharedText = it.getStringExtra(Intent.EXTRA_TEXT);
                    if (sharedText != null) {
                        Log.v("Debug", sharedText);
                    }
                }
            }

            int i = sharedText.indexOf(' ');
            String pesquisa = sharedText.substring(0, i);

            Intent abrirNavegador = new Intent(Intent.ACTION_WEB_SEARCH);
            abrirNavegador.putExtra(SearchManager.QUERY, pesquisa);
            startActivity(abrirNavegador);
        }catch (Exception erro){

        }

    }
}
