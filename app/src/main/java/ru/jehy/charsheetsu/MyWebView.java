package ru.jehy.charsheetsu;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Bond on 01-Dec-15.
 */
 class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}