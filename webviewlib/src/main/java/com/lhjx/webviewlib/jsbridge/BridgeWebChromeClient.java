package com.lhjx.webviewlib.jsbridge;

import android.util.Log;

import com.lhjx.webviewlib.JSBridgeTag;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Author: Created by fangmingdong on 2019/1/25-11:04 AM
 * Description:
 */
public class BridgeWebChromeClient extends WebChromeClient {

    private boolean jsInjexted;

    @Override
    public void onProgressChanged(WebView webView, int i) {
        if (i >= 40 && !jsInjexted) {
            if (BridgeWebView.toLoadJs != null) {
                BridgeUtil.webViewLoadLocalJs(webView, BridgeWebView.toLoadJs);
            }
            jsInjexted = true;
            Log.d(JSBridgeTag.JS_TAG, "onProgressChanged: " + i + "inject js");
        }
        super.onProgressChanged(webView, i);
    }
}
