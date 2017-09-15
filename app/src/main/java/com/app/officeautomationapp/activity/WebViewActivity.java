package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;

/**
 * Created by CS-711701-00027 on 2017/9/15.
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;
    ProgressDialog progressDialog;
    private String title;
    private String url;
    private TextView tv_back;
    private ImageView iv_approval_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        title=getIntent().getStringExtra("title");
        url=getIntent().getStringExtra("url");
        tv_back=(TextView)findViewById(R.id.tv_back);
        tv_back.setText(title);
        iv_approval_back=(ImageView)findViewById(R.id.iv_approval_back);
        iv_approval_back.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView);
        init();
    }

    private void init(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebViewClient(new WebViewClient() {
               public boolean shouldOverrideUrlLoading(WebView view, String url)
               { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                   view.loadUrl(url);
                   return true;
               }
           });
        //WebView加载web资源
        webView.loadUrl(url);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_approval_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
