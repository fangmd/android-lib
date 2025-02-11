package com.lhjx.webviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.lhjx.webviewlib.jsbridge.BridgeWebView;
import com.lhjx.webviewlib.jsbridge.BridgeWebViewClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Author: Created by fangmingdong on 2018/4/24-上午9:04
 * Description: 集成 JSBridge 的 X5WebView
 */
public class MyWebViewJsBridge extends BridgeWebView {

    public static final String TAG = MyWebViewJsBridge.class.getSimpleName();

    private MyWebViewListener mMyWebViewListener;
    private ProgressBar mProgressBar;
    /**
     * 是否显示进度条
     */
    private boolean mShowProgressBar;

    public MyWebViewJsBridge(Context context) {
        super(context);
        initView();
    }

    public MyWebViewJsBridge(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public MyWebViewJsBridge(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        initWebViewSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && WebViewUtils.sIsDEBUG) {
            setWebContentsDebuggingEnabled(true);
        } else {
            setWebContentsDebuggingEnabled(false);
        }

        setBackgroundColor(getResources().getColor(android.R.color.white));
        setWebViewClient(getCustomWebViewClient());
        setWebChromeClient(getChromeClient());
        setClickable(true);
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccess(false);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setGeolocationEnabled(true);

        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setTextZoom(100); // 防止系统字体设置对 WebView 的影响

//        webSetting.setCacheMode(WebSettings.LOAD_NORMAL);
//        getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension

        // 隐藏滚动条
        setHorizontalScrollBarEnabled(false);//水平不显示
        setVerticalScrollBarEnabled(false); //垂直不显示
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //滚动条在WebView外侧显示
    }

    private WebViewClient getCustomWebViewClient() {
        return new MyWebViewClient(this);
    }

    private WebChromeClient getChromeClient() {
        return mChromeClient;
    }

    /**
     * 设置 UA
     */
    public void setUA(String ua) {
        WebSettings webSetting = getSettings();
        webSetting.setUserAgentString(ua);
    }

    /**
     * user agent 添加
     */
    public void appendUA(String ua) {
        WebSettings webSetting = getSettings();
        String userAgentString = webSetting.getUserAgentString();
        String newUA = userAgentString + ua;
        webSetting.setUserAgentString(newUA);
    }

    public class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean jsBridgeHandle = super.shouldOverrideUrlLoading(view, url);
            if (jsBridgeHandle) {
                // js bridge 处理了
                return true;
            }
            if (UrlFilter.dealUrl(view.getContext(), url)) {
                // 拦截url
                return true;
            }
            if (shouldOverrideUrlLoading2(view, url)) {
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        /**
         * 网页加载错误
         */
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            showErrorPage(webView, s1);
            super.onReceivedError(webView, i, s, s1);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            showPageLoadFinish();
        }

    }


    private WebChromeClient mChromeClient = new WebChromeClient() {

        /**
         * 网页加载进度监听
         */
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (!mShowProgressBar || mProgressBar == null) {
                return;
            }
            if (progress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(progress);
            }

            super.onProgressChanged(webView, progress);
        }

    };


    private void showPageLoadFinish() {
        if (mMyWebViewListener != null) {
            mMyWebViewListener.showPageLoadFinish();
        }
    }

    private boolean shouldOverrideUrlLoading2(WebView view, String url) {
        if (mMyWebViewListener != null) {
            return mMyWebViewListener.shouldOverrideUrlLoading2(view, url);
        }
        return false;
    }

    private void showErrorPage(WebView webView, String url) {
        if (mMyWebViewListener != null) {
            mMyWebViewListener.showErrorPage(webView, url);
        }
    }

    public MyWebViewListener getMyWebViewListener() {
        return mMyWebViewListener;
    }

    public void setMyWebViewListener(MyWebViewListener myWebViewListener) {
        mMyWebViewListener = myWebViewListener;
    }

    // ------------- progress --------------
    public void setShowProgressBar(boolean showProgressBar) {
        setShowProgressBar(showProgressBar, R.drawable.bg_pb_web_loading);
    }

    public void setShowProgressBar(boolean showProgressBar, @DrawableRes int drawable) {
        mShowProgressBar = showProgressBar;
        if (mShowProgressBar) {
            addProgressBar(drawable);
        }
    }

    /**
     * 添加进度条
     *
     * @param drawable 进度条颜色
     */
    private void addProgressBar(@DrawableRes int drawable) {
        mProgressBar = new ProgressBar(getContext(), null,
                android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5));
        mProgressBar.setProgressDrawable(getContext().getResources().getDrawable(drawable));

        addView(mProgressBar);
    }
    // ------------- progress --------------


}
