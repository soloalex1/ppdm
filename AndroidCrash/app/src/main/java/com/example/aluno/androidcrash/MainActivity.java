package com.example.aluno.androidcrash;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mImageView;
    Button btnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.mImageView);
        btnImg = findViewById(R.id.btnImg);

        btnImg.setOnClickListener(this);
    }

    public void onClick(View v){
        Log.v("teste", "botão clicado");
        new Thread(new Runnable(){
            @Override
            public void run(){
                final Bitmap bmp = loadImageFromNetwork("https://consequenceofsound.net/wp-content/uploads/2018/08/maxresdefault2.jpg?quality=80&w=807");
                Log.v("teste", "imagem baixada");

                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bmp);
                    }
                });
            }
        }).start();
    }

    private Bitmap loadImageFromNetwork(String url){
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

