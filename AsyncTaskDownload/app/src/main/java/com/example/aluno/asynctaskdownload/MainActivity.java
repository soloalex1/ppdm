package com.example.aluno.asynctaskdownload;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDownload;
    ImageView img;
    ProgressBar progressBar;
    EditText endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.btnDownload);
        endereco = findViewById(R.id.text);
        img = findViewById(R.id.imgDownload);
        progressBar = findViewById(R.id.progressBar);

        btnDownload.setOnClickListener(this);

    }

    private void chamarAsyncTask(String url){
        AsyncDownloader download = new AsyncDownloader();
        download.execute(url);
    }

    @Override
    public void onClick(View v){
        chamarAsyncTask(endereco.getText().toString());
    }
}
