package com.lhjx.webviewlib;

import com.tencent.smtt.sdk.WebView;

/**
 * Author: Created by fangmingdong on 2018/4/24-下午2:11
 * Description:
 */
public interface MyWebViewListener {

    void showErrorPage();

    void hideErrorPage();

    void showPageLoadFinish();

    /**
     * 是否拦截 url
     * @return false: 不拦截，true: 拦截
     */
    boolean shouldOverrideUrlLoading2(WebView view, String url);
}
