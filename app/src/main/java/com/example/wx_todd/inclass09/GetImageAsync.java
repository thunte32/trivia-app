package com.example.wx_todd.inclass09;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by WX-Todd on 11/15/2016.
 */

public class GetImageAsync extends AsyncTask<URL, Void, Bitmap> {
    private GetImage activity;
    public GetImageAsync(GetImage getImage){activity = getImage;}
    @Override
    protected Bitmap doInBackground(URL... params) {
        URL url = params[0];
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
    }
    public interface GetImage{
        void setImage(Bitmap bitmap);
    }
}
