package com.lhjx.netlib;

import android.app.Application;

/**
 * Author: Created by fangmingdong on 2018/6/1-上午10:36
 * Description:
 */
public class NetManager {

    public static Application sContext;
    private static String sToken;

    public static void init(Application context) {
        sContext = context;
    }

    public static void setToken(String token) {
        sToken = token;
    }

    public static String getToken() {
        return sToken;
    }
}
