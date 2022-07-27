package com.arfideveloper.novelnest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arfideveloper.novelnest.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    Context context;

    RelativeLayout layout_backBtn;
    ProgressBar progressBar;
    RelativeLayout layoutLoader;
    WebView webView;

    String pageUrl = "http://dev.arfideveloper.com/novel/public/api/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        context = PrivacyPolicyActivity.this;

        layout_backBtn = findViewById(R.id.layout_backBtn);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        layoutLoader = findViewById(R.id.layoutLoader);


        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(false);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setProgressAnimate(progressBar, progress);
                if (progress == 100) {
                    setProgressAnimate(progressBar, 100);
                    new Handler(Looper.myLooper()).postDelayed(() -> layoutLoader.setVisibility(View.GONE), 700);
                }
            }
        });
        webView.loadUrl(pageUrl);


        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }
    private void setProgressAnimate(ProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}