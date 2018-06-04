package com.lhjx.crashcollection;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Author: Created by fangmingdong on 2018/5/30-下午1:58
 * Description:
 */
public class CrashUtils {


    /**
     * 初始化 错误日志库
     *
     * @param context context
     */
    public static void init(Context context) {
        Fabric.with(context, new Crashlytics());
    }

    /**
     * 设置用户信息
     *
     * @param userId    user id
     * @param userEmail user email
     * @param userName  user name
     */
    public static void logUser(String userId, String userEmail, String userName) {
        Crashlytics.setUserIdentifier(userId);
        Crashlytics.setUserEmail(userEmail);
        Crashlytics.setUserName(userName);
    }

}
