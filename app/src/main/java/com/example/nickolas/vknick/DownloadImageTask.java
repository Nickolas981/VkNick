package com.example.nickolas.vknick;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Nickolas on 01.05.2017.
 */

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;


    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        if (PhotoCash.photoCash.containsKey(urldisplay)){
            mIcon11 = PhotoCash.photoCash.get(urldisplay);
        } else {
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                PhotoCash.photoCash.put(urldisplay, mIcon11);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);
    }
}

class PhotoCash {
    public static HashMap<String, Bitmap> photoCash;

    public PhotoCash() {
        photoCash = new HashMap<String, Bitmap>();
    }
}
