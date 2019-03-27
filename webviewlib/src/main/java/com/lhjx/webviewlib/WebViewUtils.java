package com.lhjx.webviewlib;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.lhjx.webviewlib.jsbridge.BridgeUtil;
import com.lhjx.webviewlib.jsbridge.BridgeWebView;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

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
        init(application);
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

    public static void init(Application application, boolean isDEBUG) {
        init(application);
        sIsDEBUG = isDEBUG;
    }

    /**
     * 初始化 X5WebView 内核
     * 在 Application 中调用
     */
    public static void init(Application application) {
        optimizationInit();

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
     * 解决首次启动卡顿问题
     */
    private static void optimizationInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            // 在调用TBS初始化、创建WebView之前进行如下配置，以开启优化方案
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
            QbSdk.initTbsSettings(map);

            // 在调用TBS初始化、创建WebView之前进行如下配置，以开启优化方案
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            // 配置不使用多进程策略，即该方案仅在Android 5.1+系统上生效。
            map2.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, false);
            QbSdk.initTbsSettings(map2);
        }
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
