package ru.jehy.charsheetsu;

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
class AsyncGetToken extends AsyncTask<Void, Void, String> {
    String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
    String mEmail = "";
    Context mContext;

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    public AsyncGetToken(String Email, Context ActivityContext) {
        mEmail = Email;
        mContext = ActivityContext;
    }

    @Override
    protected String doInBackground(Void... params) {
        //MyAccount account = new MyAccount(getApplicationContext());
        //String authtoken = account.getAuthToken());
        String token = "";
        try {
            token = GoogleAuthUtil.getToken(mContext, mEmail, SCOPE);
            TokenManager.put(mContext,token);
            Log.d("GetToken", token);
            //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            String info = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token;

        } catch (UserRecoverableAuthException e) {
            // Requesting an authorization code will always throw
            // UserRecoverableAuthException on the first call to GoogleAuthUtil.getToken
            // because the user must consent to offline access to their data.  After
            // consent is granted control is returned to your activity in onActivityResult
            // and the second call to GoogleAuthUtil.getToken will succeed.
            MainActivity x = (MainActivity) mContext;
            x.startActivityForResult(e.getIntent(), MainActivity.REQUEST_CODE_PICK_ACCOUNT);
        } catch (Exception e) {
            Log.d("GetToken", e.getMessage());
            //Toast.makeText(this, R.string.failed_login, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        return token;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(String token) {
        if (token != null) {
            MainActivity x = (MainActivity) mContext;
            x.RunWebView(token);
            //use token here
        }
    }
}
