package ru.jehy.charsheetsu;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bond on 01-Dec-15.
 */
public class Util {
    static String file_get_contents(String url) {
        Log.d("file_get_contents", "getting url "+url);
        String result = null;
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet(url);
// Depends on your web service
        //httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            Log.d("file_get_contents", e.getMessage());
            // Oops
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception squish) {
                Log.d("file_get_contents", squish.getMessage());
            }
        }
        Log.d("file_get_contents", "returning "+result);
        return result;
    }

    static boolean CheckEmailInJson(String result) {
        try {
            Log.d("CheckEmailInJson", "input value: "+result);
            JSONObject jObject = new JSONObject(result);
            String aJsonString = jObject.getString("email");
            if (aJsonString != null) {
                Log.d("CheckEmailInJson", "token is valid "+aJsonString);
                return true;
            }

        } catch (JSONException e) {
            Log.d("CheckEmailInJson", "exception: "+e.getMessage());
            // Oops
        }
        Log.d("CheckEmailInJson", "token is invalid");
        return false;
    }
}