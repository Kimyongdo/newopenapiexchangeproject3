package com.example.newopenapiexchangeproject3;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class newsWebview extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z0_newswebview);

        Intent intent = getIntent();
        String link = intent.getStringExtra("Link");

        //얻어온 링크주소를 웹뷰에 보여주기

        webView= findViewById(R.id.webview);
        //웹페이지에서 사용하는 JavaScript를 동작하도록
        webView.getSettings().setJavaScriptEnabled(true); //자바스크립트가 기본으로 동작해서 true로 바꾸어주어야함. - 설정1

        //웹뷰에 페이지를 load하면 안드로이드에서 자동으로 새로운 웹브라우저를 열어버림
        //그걸 안하고 내 WebView페이지를 보이도록
        webView.setWebViewClient(new WebViewClient()); //이걸 써야 새창으로 열리지 않음.  - 설정2

        //웹 페이지안에 웹다이얼로그를 보여주는 등의 작업이 있다면 그걸 동작하도록..
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(link);


    }
}
