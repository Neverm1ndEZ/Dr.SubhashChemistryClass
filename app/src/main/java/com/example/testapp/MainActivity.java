package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RelativeLayout internetLayout, noInternetLayout;
    WebView webView;
    ProgressBar pgBar;
    Button tryAgainButton;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        internetLayout = findViewById(R.id.InternetLayout);
        noInternetLayout = findViewById(R.id.noInternetLayout);
        webView = findViewById(R.id.webView);
        pgBar = findViewById(R.id.pgBar);
        tryAgainButton = findViewById(R.id.tryAgainButton);

        webView.loadUrl("https://subhashchemistryclasses.online");
        webView.getSettings().setJavaScriptEnabled(true);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawLayout();
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pgBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pgBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                noInternetLayout.setVisibility(View.VISIBLE);
                internetLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public void drawLayout(){
        if(isNetworkAvailable()){
            internetLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.GONE);
        }
    }
}