
X5WebView + JSBridge 库

# 使用

`app/build.gradle` 中配置：

```
defaultConfig{
    ndk{
        abiFilters "armeabi"
    }
}
```


在 'Application' 中初始化：

```
WebViewUtils.init(this);
```

Activity:

```
    private void addWebView() {
        mWebView = new MyWebView(this);
        mWebView.setMyWebViewListener(this);
        mFLWeb.addView(mWebView);
        mFLWeb.post(() -> {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getLayoutParams();
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mWebView.setLayoutParams(lp);
        });
        mWebView.addJavascriptInterface(new ShareJS(this), "shareAction");
        mWebView.addJavascriptInterface(new AndroidJS(this), "Android");
    }

    @Override
    protected void onDestroy() {
        WebViewUtils.destroyWebView(mWebView);
        super.onDestroy();
    }
```

# proguard

```
-keep class com.njfae.webviewlib.** {*;}
```