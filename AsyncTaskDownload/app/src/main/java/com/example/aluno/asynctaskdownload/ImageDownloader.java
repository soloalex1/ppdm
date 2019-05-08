package com.example.aluno.asynctaskdownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {

    public static Bitmap baixarImagem(String url) throws IOException{

        URL endereco;
        InputStream inputStream;
        Bitmap img;

        endereco = new URL(url);
        inputStream = endereco.openStream();
        img = BitmapFactory.decodeStream(inputStream);

        inputStream.close();

        return img;
    }
}
