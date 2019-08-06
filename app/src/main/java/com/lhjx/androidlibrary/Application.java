package com.lhjx.androidlibrary;

import com.passon.loglib.LoggerUtils;
import com.passon.webviewlib.WebViewUtils;

/**
 * Author: Created by fangmingdong on 2019/1/24-3:23 PM
 * Description:
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();

        WebViewUtils.init(BuildConfig.DEBUG);
    }

    private void initLogger() {
        LoggerUtils.init(BuildConfig.DEBUG);
    }


}
