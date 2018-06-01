
X5WebView + JSBridge 库

JSBridge 版本：com.github.lzyzsd:jsbridge:1.0.4

X5WebView 版本：3.6.0.1234_43608


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

```