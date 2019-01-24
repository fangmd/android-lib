package com.lhjx.androidlibrary;

import com.lhjx.webviewlib.WebViewUtils;

/**
 * Author: Created by fangmingdong on 2019/1/24-3:23 PM
 * Description:
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        WebViewUtils.init(this, "WebViewJavascriptBridge_origin.js");
//        WebViewUtils.init(this, "test.js");
        WebViewUtils.init(this, "AppJSBridge_android_last.js");//
//        WebViewUtils.init(this, "AppJSBridge_ios.js");
//        WebViewUtils.init(this);

        //        WebViewUtils.init(this, "AppJSBridge_android.js"); // 可用, JS Call Native, Native Call JS, 注入时间问题：需要网页在 200ms 后再调用初始化相关代码

        //todo: 兼容性测试
    }


}
