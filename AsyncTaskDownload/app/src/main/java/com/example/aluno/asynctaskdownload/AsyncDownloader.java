package com.example.aluno.asynctaskdownload;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class AsyncDownloader extends AsyncTask<String, Integer, Bitmap> {


    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Bitmap doInBackground(String... params){
        Bitmap img = null;

        try {
            img = ImageDownloader.baixarImagem(params[0]);
        } catch(IOException ex){
            Log.i("Download", ex.getMessage());
        }

        return img;
    }

    @Override
    protected void onPostExecute(Bitmap img){
        if(img != null){

        }
    }
}
