package com.arfideveloper.novelnest.utilities;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import pl.droidsonroids.gif.GifImageView;

public class AppWebViewClients extends WebViewClient {
    View shadowView;
    GifImageView loadingView;

    public AppWebViewClients(View shadowView, GifImageView loadingView) {
        this.shadowView=shadowView;
        this.loadingView=loadingView;

        shadowView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        shadowView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }
}

