package ru.jehy.charsheetsu;

/**
 * Created by Bond on 01-Dec-15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

/**
 * Created by Bond on 01-Dec-15.
 */
class AsyncCheckToken extends AsyncTask<Void, Void, Boolean> {
    String mToken = "";
    Context mContext;

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param ActivityContext activity context.
     * @param token           token to validate.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    public AsyncCheckToken(Context ActivityContext, String token) {
        mContext = ActivityContext;
        mToken = token;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //MyAccount account = new MyAccount(getApplicationContext());
        //String authtoken = account.getAuthToken());
        boolean TokenGood = false;
        try {
            String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + mToken;
            Log.d("CheckToken", "fetching url " + url);
            String info = Util.file_get_contents(url);
            if (info != null)
                return Util.CheckEmailInJson(info);
        } catch (Exception e) {
            Log.d("CheckToken", "Exception fetching from server and parsing: " + e.toString());
            //Toast.makeText(this, R.string.failed_login, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        return TokenGood;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param TokenGood if token is really good.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Boolean TokenGood) {
        MainActivity x = (MainActivity) mContext;
        if (!TokenGood) {
            x.pickUserAccount();
            //use token here
        } else
            x.RunWebView(mToken);
    }
}

