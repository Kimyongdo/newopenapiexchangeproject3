package exchange.example.newopenapiexchangeproject3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newopenapiexchangeproject3.R;

import im.delight.android.webview.AdvancedWebView;

public class newsWebview extends AppCompatActivity implements AdvancedWebView.Listener{

   // WebView webView;
    private AdvancedWebView mWebView;
    boolean preventCaching = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_newswebview);

        Intent intent = getIntent();
        String link = intent.getStringExtra("Link");

        //얻어온 링크주소를 웹뷰에 보여주기
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setThirdPartyCookiesEnabled(false);
        mWebView.setCookiesEnabled(false);
        //크롬사용 - 빠르긴하나 두번 꺼야하는 불편함?
        if (AdvancedWebView.Browsers.hasAlternative(this)) {
            AdvancedWebView.Browsers.openUrl(this, link);
        }
        //쿠키 저장불가

        //캐싱막기
//        mWebView.loadUrl(link, preventCaching);

//        webView= findViewById(R.id.webview);
//        //웹페이지에서 사용하는 JavaScript를 동작하도록
//        webView.getSettings().setJavaScriptEnabled(true); //자바스크립트가 기본으로 동작해서 true로 바꾸어주어야함. - 설정1
//
//        //웹뷰에 페이지를 load하면 안드로이드에서 자동으로 새로운 웹브라우저를 열어버림
//        //그걸 안하고 내 WebView페이지를 보이도록
//        webView.setWebViewClient(new WebViewClient()); //이걸 써야 새창으로 열리지 않음.  - 설정2
//
//        //웹 페이지안에 웹다이얼로그를 보여주는 등의 작업이 있다면 그걸 동작하도록..
//        webView.setWebChromeClient(new WebChromeClient());
//
//        webView.loadUrl(link);


    }


    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
