package xyz.zpayh.mygank.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import xyz.zpayh.mygank.BaseActivity;
import xyz.zpayh.mygank.R;

public class WebViewActivity extends BaseActivity {

    private final static String URL = "url";
    private final static String TITLE = "title";

    private WebView mWebView;

    private ProgressBar mProgressBar;

    @Override
    protected int getLayoutResID() {
        return R.layout.act_webview;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProgressBar = findView(R.id.progress);
        mProgressBar.setMax(100);
        mWebView = findView(R.id.webView);

        if (Build.VERSION.SDK_INT >= 19){
            /*对系统API在19以上的版本作了兼容。
            因为4.4以上系统在onPageFinished时再恢复图片加载时,
            如果存在多张图片引用的是相同的src时，会只有一个image
            标签得到加载，因而对于这样的系统我们就先直接加载。*/
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }else{
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }

        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!view.getSettings().getLoadsImagesAutomatically()){
                    view.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100){
                    mProgressBar.setVisibility(View.GONE);
                }else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view,newProgress);
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存方式
        settings.setDomStorageEnabled(true);//开启DOM storage API功能
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setUseWideViewPort(false);//将图片调整到适合webview的大小
        settings.setSupportZoom(false);//不支持缩放
        settings.supportMultipleWindows();//多窗口
        settings.setAllowFileAccess(true);
        settings.setNeedInitialFocus(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null){
            finish();
            return;
        }
        final String url = intent.getStringExtra(URL);
        if (TextUtils.isEmpty(url)){
            finish();
            return;
        }
        mWebView.loadUrl(url);
        final String title = intent.getStringExtra(TITLE);
        setTitle(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        finish();
        return true;
    }

    public static void start(@NonNull Context context, @NonNull String url, @Nullable String title){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(URL,url);
        intent.putExtra(TITLE,title);
        context.startActivity(intent);
    }
}
