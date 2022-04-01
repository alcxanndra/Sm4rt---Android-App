package com.example.sm4rt.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {

    public static JSONArray loadJSONFromAsset(Activity activity, String jsonFile) throws JSONException {
        String json = null;
        try {
            InputStream is = activity.getApplicationContext().getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        JSONObject obj = new JSONObject(json);
        JSONArray array = obj.getJSONArray(jsonFile.substring(0, jsonFile.indexOf(".")));
        return array;
    }

    public static Integer getImage(Context c, String ImageName) {
        return c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
    }

    // Retrieving the url to share
    public static Uri getImageToShare(Activity activity, Bitmap bitmap) {
        File imageFolder = new File(activity.getCacheDir(), "images");
        Uri uri = null;
        try {
            imageFolder.mkdirs();
            File file = new File(imageFolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(activity.getApplicationContext(), "com.example.sm4rt.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}
