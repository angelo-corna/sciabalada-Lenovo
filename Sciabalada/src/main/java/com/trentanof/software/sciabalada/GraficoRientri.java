package com.trentanof.software.sciabalada;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class GraficoRientri extends Activity {

    public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficorientri);
        WebView webview = (WebView) findViewById(R.id.webView1);

        Intent intent = getIntent();
        String content = intent.getStringExtra(Statistiche.EXTRA_MESSAGE);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );
        //webview.loadUrl("file:///android_asset/index.html"); // Can be used in this way too.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_to_image:
                Intent intent = new Intent(GraficoRientri.this, GoogleImageGraphActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}