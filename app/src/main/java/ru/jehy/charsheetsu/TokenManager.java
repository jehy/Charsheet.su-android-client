package ru.jehy.charsheetsu;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Bond on 01-Dec-15.
 */
public class TokenManager {
    public static String KEY = "access_token";

    static String get(Context mContext) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        //if(!settings.contains(KEY))
        //    return null;
        String access_token = settings.getString(KEY, null);
        if (access_token == null)
            Log.d("TokenManager", "No token stored! ");
        else
            Log.d("TokenManager", "Got token " + access_token);
        return access_token;
    }

    static void put(Context mContext, String token) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY, token);
        Log.d("TokenManager", "Saved token " + token);
        editor.commit();
    }
}
