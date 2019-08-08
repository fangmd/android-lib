package com.passon.netlib;

import android.app.Application;
import android.text.TextUtils;

import java.util.Map;

/**
 * Author: Created by fangmingdong on 2018/6/1-上午10:36
 * Description: 网络管理类
 * 1. 需要在 Application 中初始化，设置 BaseUrl
 * 2. 可以设置请求公共 Header
 * 3. 可以给 Get/Post 添加公共参数
 */
public class NetManager {

    public static Application sContext;

    private static String BASE_URL;

    public static boolean sDEBUG;

    /**
     * 所有请求添加，请求头
     */
    private static Map<String, String> sCommonHeader;
    /**
     * Get 请求需要添加的额外参数, url 方式拼接
     */
    private static Map<String, String> sGetParams;

    public static void init(Application context, String baseUrl, boolean debug) {
        NetManager.sContext = context;
        NetManager.BASE_URL = baseUrl;
        NetManager.sDEBUG = debug;
    }

    public static String getBaseUrl() {
        if (TextUtils.isEmpty(BASE_URL)) {
            throw new RuntimeException(NetLibC.ERROR_NOT_INIT);
        }
        return BASE_URL;
    }


    public static void setCommonHeader(Map<String, String> commonHeader) {
        NetManager.sCommonHeader = commonHeader;
    }

    public static Map<String, String> getCommonHeader() {
        return sCommonHeader;
    }

    public static void setGetParams(Map<String, String> getParams) {
        NetManager.sGetParams = getParams;
    }

    public static Map<String, String> getGetParams() {
        return sGetParams;
    }

}
