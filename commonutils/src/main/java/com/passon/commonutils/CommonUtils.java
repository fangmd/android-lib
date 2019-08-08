package com.passon.commonutils;

import android.app.Application;

/**
 * Author: Created by fangmingdong on 2019-08-07-15:40
 * Description:
 */
public class CommonUtils {

    private static Application app;

    public static void init(Application app) {
        CommonUtils.app = app;
    }

    public static Application getApp() {
        return app;
    }

}
