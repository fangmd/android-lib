package com.njfae.pushlibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * Author: Created by fangmingdong on 2018/5/25-下午3:25
 * Description:
 */
public class PushUtils {

    public static void register(Context context) {
        register(context, "");
    }

    public static void register(Context context, String account) {
        // 华为推送渠道 Debug 模式
//        XGPushConfig.setHuaweiDebug(true);

        //打开第三方推送
        XGPushConfig.enableOtherPush(context, true);

        // 厂商渠道配置
        initMi(context);
        initMeiZu(context);

        //TODO: relaese 下设置成 false fangmingdong 2018/5/25-下午3:26
        XGPushConfig.enableDebug(context, false);
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d(Constants.TAG_PUSH, "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d(Constants.TAG_PUSH, "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

        if (TextUtils.isEmpty(account)) {
            account = "*";
        }
        XGPushManager.bindAccount(context, account);
    }

    private static void initMi(Context context) {
        XGPushConfig.setMiPushAppId(context, "2882303761517552059");
        XGPushConfig.setMiPushAppKey(context, "5811755226059");
    }

    private static void initMeiZu(Context context) {
        XGPushConfig.setMzPushAppId(context, "");
        XGPushConfig.setMzPushAppKey(context, "");
    }
}
