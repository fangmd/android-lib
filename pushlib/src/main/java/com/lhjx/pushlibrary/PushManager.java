package com.lhjx.pushlibrary;

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
public class PushManager {

    private Context context;

    private String miAppId;
    private String miAppKey;
    private String mzAppId;
    private String mzAppKey;
    private String hwAppId;
    private String hwAppKey;

    /**
     * 是否开启第三方推送
     */
    private boolean enableOtherPush;
    private boolean debug;
    /**
     * 华为推送 debug
     */
    private boolean hwDebug;
    /**
     * 是否开启小米推送
     */
    private boolean enableMI;
    /**
     * 是否开启华为推送
     */
    private boolean enableHW;
    /**
     * 是否开启魅族推送
     */
    private boolean enableMZ;

    /**
     * 推送启动回调
     */
    private PushCallback pushCallback;

    /**
     * 用户账户
     */
    private String account;


    PushManager(Builder builder) {
        this.context = builder.context;

        this.miAppId = builder.miAppId;
        this.miAppKey = builder.miAppKey;

        this.mzAppId = builder.mzAppId;
        this.mzAppKey = builder.mzAppKey;

        this.hwAppId = builder.hwAppId;
        this.hwAppKey = builder.hwAppKey;

        this.enableOtherPush = builder.enableOtherPush;
        this.debug = builder.debug;
        this.hwDebug = builder.hwDebug;

        this.enableMI = builder.enableMI;
        this.enableHW = builder.enableHW;
        this.enableMZ = builder.enableMZ;

        this.pushCallback = builder.pushCallback;
        this.account = builder.account;
    }

    public void init() {
        initDebug();

        XGPushConfig.enableOtherPush(context, enableOtherPush);
        if (enableMI) {
            initMi();
        }
        if (enableMZ) {
            initMeiZu();
        }
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

        if (!TextUtils.isEmpty(account)) {
            XGPushManager.bindAccount(context, account);
        }
    }

    private void initDebug() {
        XGPushConfig.enableDebug(context, false);
        if (hwDebug) {
            XGPushConfig.setHuaweiDebug(true);
        }
    }

    private void initMi() {
        XGPushConfig.setMiPushAppId(context, miAppId);
        XGPushConfig.setMiPushAppKey(context, miAppKey);
    }

    private void initMeiZu() {
        XGPushConfig.setMzPushAppId(context, mzAppId);
        XGPushConfig.setMzPushAppKey(context, mzAppKey);
    }


    public static final class Builder {

        Context context;

        String miAppId;
        String miAppKey;
        String mzAppId;
        String mzAppKey;
        String hwAppId;
        String hwAppKey;

        boolean enableOtherPush;
        boolean debug;
        boolean hwDebug;

        PushCallback pushCallback;

        String account;

        boolean enableMI;
        boolean enableHW;
        boolean enableMZ;

        public Builder(Context context) {
            this.context = context;
        }

        public PushManager build() {
            return new PushManager(this);
        }

        public Builder enableDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder enableHWDebug(boolean hwDebug) {
            this.hwDebug = hwDebug;
            return this;
        }

        public Builder enableMi(String miAppId, String miAppKey) {
            if (TextUtils.isEmpty(miAppId)) {
                Log.e(Constants.TAG_PUSH, "enableMi: miAppId is null");
            }
            if (TextUtils.isEmpty(miAppKey)) {
                Log.e(Constants.TAG_PUSH, "enableMi: miAppKey is null");
            }
            this.miAppId = miAppId;
            this.miAppKey = miAppKey;
            enableMI = true;
            enableOtherPush = true;
            return this;
        }

        public Builder enableHW(String hwAppId, String hwAppKey) {
            if (TextUtils.isEmpty(hwAppId)) {
                Log.e(Constants.TAG_PUSH, "enableMi: hwAppId is null");
            }
            if (TextUtils.isEmpty(hwAppKey)) {
                Log.e(Constants.TAG_PUSH, "enableMi: hwAppKey is null");
            }
            this.hwAppId = hwAppId;
            this.hwAppKey = hwAppKey;
            enableHW = true;
            enableOtherPush = true;
            return this;
        }

        public Builder enableMZ(String mzAppId, String mzAppKey) {
            if (TextUtils.isEmpty(mzAppId)) {
                Log.e(Constants.TAG_PUSH, "enableMi: mzAppId is null");
            }
            if (TextUtils.isEmpty(mzAppKey)) {
                Log.e(Constants.TAG_PUSH, "enableMi: mzAppKey is null");
            }
            this.mzAppId = mzAppId;
            this.mzAppKey = mzAppKey;
            enableMZ = true;
            enableOtherPush = true;
            return this;
        }

        public Builder setPushCallback(PushCallback pushCallback) {
            this.pushCallback = pushCallback;
            return this;
        }

        public Builder setAccount(String account) {
            this.account = account;
            return this;
        }
    }

}
