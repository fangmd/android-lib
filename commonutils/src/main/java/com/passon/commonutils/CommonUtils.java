package com.passon.commonutils;

import android.app.Application;

/**
 * Author: Created by fangmingdong on 2019-08-07-15:40
 * Description:
 */
public class CommonUtils {

    public static Application app;

    public static void init(Application app) {
        CommonUtils.app = app;
    }
}
