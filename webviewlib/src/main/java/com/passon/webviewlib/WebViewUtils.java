package com.passon.webviewlib;

import android.app.Application;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.passon.webviewlib.jsbridge.BridgeUtil;
import com.passon.webviewlib.jsbridge.BridgeWebView;

/**
 * Author: Created by fangmingdong on 2018/4/25-上午11:23
 * Description: WebView 工具类
 */
public class WebViewUtils {

    public static boolean sIsDEBUG = false;

    /**
     * 初始化，设置要注入的 js
     *
     * @param application application
     * @param jsFileName  assets 中的 js 文件名 ex:WebViewJavascriptBridge.js
     */
    public static void init(Application application, String jsFileName) {
        if (checkAssetsFile(application, jsFileName)) {
            BridgeWebView.toLoadJs = jsFileName;
        }
    }

    /**
     * 检查 Assets 中文件是否存在
     *
     * @param application Application
     * @param jsFileName  js file name
     * @return content
     */
    private static boolean checkAssetsFile(Application application, String jsFileName) {
        String s = BridgeUtil.assetFile2Str(application, jsFileName);
        return !TextUtils.isEmpty(s);
    }

    public static void init(boolean isDEBUG) {
        sIsDEBUG = isDEBUG;
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
