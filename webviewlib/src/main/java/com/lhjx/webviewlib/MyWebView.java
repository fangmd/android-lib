package com.lhjx.webviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Author: Created by fangmingdong on 2018/4/24-上午9:04
 * Description: X5WebView
 */
public class MyWebView extends WebView {

    public static final String TAG = MyWebView.class.getSimpleName();

    private MyWebViewListener mMyWebViewListener;
    private ProgressBar mProgressBar;
    /**
     * 是否显示进度条
     */
    private boolean mShowProgressBar = true;

    public MyWebView(Context context) {
        super(context);
        initView();
    }

    public MyWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public MyWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        if (mShowProgressBar) {
            addProgressBar();
        }
        initWebViewSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
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
        webSetting.setAllowFileAccess(true);
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
        return mWebViewClient;
    }

    public WebChromeClient getChromeClient() {
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

    private WebViewClient mWebViewClient = new WebViewClient() {

        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (UrlFilter.dealUrl(view.getContext(), url)) {
                // 拦截url
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
            super.onReceivedError(webView, i, s, s1);
            showErrorPage();
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            showPageLoadFinish();
        }
    };


    private WebChromeClient mChromeClient = new WebChromeClient() {

        /**
         * 网页加载进度监听
         */
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (!mShowProgressBar) {
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

    private void showErrorPage() {
        if (mMyWebViewListener != null) {
            mMyWebViewListener.showErrorPage();
        }
    }

    public MyWebViewListener getMyWebViewListener() {
        return mMyWebViewListener;
    }

    public void setMyWebViewListener(MyWebViewListener myWebViewListener) {
        mMyWebViewListener = myWebViewListener;
    }

    /**
     * 设置 loading 颜色
     *
     * @return drawable
     */
    protected @DrawableRes
    int getLoadingDrawable() {
        return R.drawable.bg_pb_web_loading;
    }

    /**
     * 添加进度条
     */
    public void addProgressBar() {
        mProgressBar = new ProgressBar(getContext(), null,
                android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5));
        mProgressBar.setProgressDrawable(getContext().getResources().getDrawable(getLoadingDrawable()));

        addView(mProgressBar);
    }

    public void setShowProgressBar(boolean showProgressBar) {
        mShowProgressBar = showProgressBar;
    }

}
