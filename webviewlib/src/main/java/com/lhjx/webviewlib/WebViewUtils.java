package com.lhjx.webviewlib;

import android.app.Application;
import android.util.Log;
import android.view.ViewGroup;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

/**
 * Author: Created by fangmingdong on 2018/4/25-上午11:23
 * Description: WebView 工具类
 */
public class WebViewUtils {

    /**
     * 初始化 X5WebView 内核
     * 在 Application 中调用
     */
    public static void init(Application application) {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.d("WebView", "X5WebView onViewInitFinished init finished:" + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        QbSdk.initX5Environment(application, cb);
    }

    /**
     * 销毁和释放 WebView
     */
    public static void destroyWebView(WebView webView) {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
}
