package com.lhjx.analysislib;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

/**
 * Author: Created by fangmingdong on 2018/5/31-下午2:16
 * Description:
 */
public class AnalysisUtils {

    /**
     * 初始化友盟统计
     *
     * @param context Context
     * @param debug   是否是 debug 模式
     * @param appKey  app key
     * @param channel 渠道
     */
    public static void initUMeng(Context context, boolean debug, String appKey, String channel) {
        UMConfigure.setLogEnabled(debug);
        // 参数2：appKey, 参数3：channel, 参数4：设备类型, 参数5：Push推送业务的secret
        UMConfigure.init(context, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
    }


}
