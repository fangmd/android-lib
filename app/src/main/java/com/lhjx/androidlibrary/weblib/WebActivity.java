package com.lhjx.androidlibrary.weblib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.lhjx.androidlibrary.R;
import com.lhjx.androidlibrary.utils.SystemUtils;
import com.lhjx.androidlibrary.utils.ToastUtils;
import com.lhjx.androidlibrary.weblib.js.JSUtils;
import com.lhjx.androidlibrary.weblib.js.JsConstants;
import com.passon.loglib.LoggerUtils;
import com.passon.webviewlib.MyWebViewJsBridge;
import com.passon.webviewlib.WebViewUtils;
import com.passon.webviewlib.jsbridge.BridgeWebChromeClient;

public class WebActivity extends AppCompatActivity implements JSUtils.JSCallBack, View.OnClickListener {

    private MyWebViewJsBridge mWebView;

    public static void start(Context context) {
        Intent starter = new Intent(context, WebActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (MyWebViewJsBridge) findViewById(R.id.webview);
        findViewById(R.id.btn_call_js).setOnClickListener(this);

        mWebView.setUA(generateCustomUserAgent(mWebView));

        JSUtils.setUpWebView(mWebView, this);
        mWebView.setWebChromeClient(new BridgeWebChromeClient());

        mWebView.loadUrl("http://192.168.10.83:8000?" + Math.random());
//        mWebView.loadUrl("http://baidu.com/");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtils.destroyWebView(mWebView);
    }

    public String generateCustomUserAgent(WebView webView) {
        return webView.getSettings().getUserAgentString()
                + "versioncode=" + SystemUtils.getVersionCode(webView.getContext())
                + "/wcgAndroid/" + SystemUtils.getVersionName(webView.getContext());

    }

    @Override
    public void setTitle(String title) {
        ToastUtils.show(this, title);
    }

    @Override
    public String getToken() {
        return "MyToken";
    }

    @Override
    public void share(String content) {
        ToastUtils.show(this, content);
    }

    @Override
    public void chat() {
        ToastUtils.show(this, "chat");
    }

    @Override
    public void call(String tel) {
        LoggerUtils.d("JS Call native: tell:" + tel);
    }

    @Override
    public void jump(String jump) {
        ToastUtils.show(this, jump);
    }

    @Override
    public void rightButton(String content) {
        ToastUtils.show(this, content);
        Log.d(WebActivity.class.getSimpleName(), "rightButton: " + content);
    }

    @Override
    public void navLeftType(String data) {
        ToastUtils.show(this, data);
    }

    @Override
    public void goBackFunc(int backIndex) {
        ToastUtils.show(this, backIndex + "");
    }

    @Override
    public void openNewWebView(String openUrl) {
        ToastUtils.show(this, openUrl + "");
        Log.d(WebActivity.class.getSimpleName(), "openNewWebView: ");
    }

    @Override
    public void reload() {
        mWebView.reload();
    }

    @Override
    public void eventAction(String content) {
        ToastUtils.show(this, "content");
    }

    @Override
    public void onClick(View v) {
        mWebView.callHandler(JsConstants.TITLE_RIGHT_CLICK, "", data -> {

        });
    }
}
