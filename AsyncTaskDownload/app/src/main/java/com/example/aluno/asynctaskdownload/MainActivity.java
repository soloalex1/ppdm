package com.example.aluno.asynctaskdownload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener {

    private Context context;
    private Activity activity;
    private CoordinatorLayout layout;
    private AsyncTask task;

    public ImageView imagem;
    private Button baixar;
    private EditText endereco;
    private ProgressBar bar;

    private int progress = 0;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = MainActivity.this;

        imagem = findViewById(R.id.imgDownload);
        baixar = findViewById(R.id.btnDownload);
        endereco = findViewById(R.id.text);
        bar = findViewById(R.id.progressBar);

        baixar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        task = new DownloadTask().execute(stringToURL(endereco.getText().toString()));
    }

    protected URL stringToURL(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public class DownloadTask extends AsyncTask<URL, Integer, Bitmap> {

        protected void onPreExecute(){
            Log.v("Task", "Iniciando download em background!");

        }

        protected Bitmap doInBackground(URL... urls){
            URL url = urls[0];
            HttpURLConnection con = null;

            int imageLength = con.getContentLength();
            byte[] data = new byte[1024];

            long total = 0;

            try {
                con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream is = con.getInputStream();
                BufferedInputStream bufferedIS = new BufferedInputStream(is);

                while((progress = is.read(data)) != -1){
                    total += progress;
                    publishProgress((int) (total * 100) / imageLength);
                }

                return BitmapFactory.decodeStream(bufferedIS);

            } catch (IOException e){
                e.printStackTrace();
            } finally {
                con.disconnect();
            }

            return null;
        }

        protected void onPostExecute(Bitmap img){

            if(img != null){
                imagem.setImageBitmap(img);
            }
        }
    }

}

