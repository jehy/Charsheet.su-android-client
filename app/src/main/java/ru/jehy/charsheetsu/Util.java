package ru.jehy.charsheetsu;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bond on 01-Dec-15.
 */
public class Util {
    private static final int BUFFER_SIZE = 512;

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @throws Exception
     */
    public static String file_get_contents(String fileURL)
            throws Exception {

        Log.d("file_get_contents", "getting url " + fileURL);
        String result = "";
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            Log.d("file_get_contents", "Content-Type = " + contentType);
            Log.d("file_get_contents", "Content-Disposition = " + disposition);
            Log.d("file_get_contents", "Content-Length = " + contentLength);
            Log.d("file_get_contents", "fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                Log.d("file_get_contents", "bytes read: " + bytesRead);
                String bufferString = new String(buffer, 0, bytesRead, "UTF-8");
                Log.d("file_get_contents", "buffer string: " + bufferString);
                result = result.concat(bufferString);
                //outputStream.write(buffer, 0, bytesRead);
            }

            //outputStream.close();
            inputStream.close();

            Log.d("file_get_contents", "File downloaded");
        } else {
            Log.d("file_get_contents", "No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();

        Log.d("file_get_contents", "returning " + result);
        return result;
    }

    static boolean CheckEmailInJson(String result) {
        try {
            Log.d("CheckEmailInJson", "input value: " + result);
            JSONObject jObject = new JSONObject(result);
            String aJsonString = jObject.getString("email");
            if (aJsonString != null) {
                Log.d("CheckEmailInJson", "token is valid, email is " + aJsonString);
                return true;
            }

        } catch (JSONException e) {
            Log.d("CheckEmailInJson", "exception: " + e.getMessage());
            // Oops
        }
        Log.d("CheckEmailInJson", "token is invalid");
        return false;
    }
}