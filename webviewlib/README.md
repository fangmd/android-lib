

#v3.0

WebView + JSBridge


# 使用


在 'Application' 中初始化：

```
WebViewUtils.init(this);
```

Activity:

```
    private void addWebView() {
        mWebView = new MyWebViewJsBridge(this);
        mWebView.setMyWebViewListener(this);
        mFLWeb.addView(mWebView);
        mFLWeb.post(() -> {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getLayoutParams();
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mWebView.setLayoutParams(lp);
        });
    }

    @Override
    protected void onDestroy() {
        WebViewUtils.destroyWebView(mWebView);
        super.onDestroy();
    }
```

## 自定义 UA

```
mWebView.appendUA("versioncode=" + BuildConfig.VERSION_CODE + "/bycAndroid/" + BuildConfig.VERSION_NAME);
```



# proguard

```
-keep class com.njfae.webviewlib.** {*;}

# WebView
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

-keep class org.json.** { *; }

```