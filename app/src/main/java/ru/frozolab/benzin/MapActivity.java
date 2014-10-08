package ru.frozolab.benzin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.common.base.Strings;


public class MapActivity extends Activity {

    WebView webView;
    String companyFullName;
    private final String url = "http://map.frozolab.ru/?what=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        webView = (WebView) findViewById(R.id.map);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        companyFullName = Strings.nullToEmpty(getIntent().getStringExtra(ViewActivity.QUERY));

        webView.loadUrl(url + companyFullName);
        Log.d("MAP", url + companyFullName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.slide_right_in, R.animator.slide_right_out);
    }
}
