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
        String auth_token_string = settings.getString(KEY, ""/*default value*/);
        Log.d("TokenManager", "Got token " + auth_token_string);
        return auth_token_string;
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
