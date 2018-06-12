
X5WebView + JSBridge 库

JSBridge 版本：2018/6/1

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
        mWebView = new MyWebViewJsBridge(this);
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

## 自定义 UA

继承 MyWebView 或 MyWebViewJsBridge 实现 `setUA()` 或 `appendUA()`。

## 设置是否显示 LoadingProgress 和 loading 的颜色

调用 'setShowProgressBar()' 方法设置是否显示 LoadingProgress

设置颜色：继承然后重写 `getLoadingDrawable()` 方法


## MyWebViewListener

网页加载成功失败的结果回调

## 自定义 WebViewClient 和 WebChromeClient

继承后重写 `getCustomWebViewClient()`, `getChromeClient()` 方法。


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