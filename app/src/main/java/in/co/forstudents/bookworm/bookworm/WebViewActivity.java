package in.co.forstudents.bookworm.bookworm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {
String bookname;
WebView webView;
ImageView imagview_loading;
AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        allintentextras();
        initiviews();
    }


    private void showwebview() {
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+bookname);
    }
    private void initiviews() {
        webView = (WebView) findViewById(R.id.webviewofdoc);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                imagview_loading.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                imagview_loading.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        showwebview();
    }

    private void allintentextras() {
        Intent frombookdbtoviewbooks = getIntent();
        bookname = frombookdbtoviewbooks.getStringExtra("nameofbook");
        builder= new AlertDialog.Builder(this);
        imagview_loading = (ImageView) findViewById(R.id.imageloding);
}

}
