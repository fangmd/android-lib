package com.lhjx.pushlibrary.hw;

import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;

/**
 * Author: Created by fangmingdong on 2018/5/25-下午4:39
 * Description:
 */
public class MyReceiver extends PushReceiver {

    @Override
    public void onEvent(Context context, Event arg1, Bundle arg2) {
        super.onEvent(context, arg1, arg2);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] arg1, Bundle arg2) {
        return super.onPushMsg(context, arg1, arg2);
    }

    @Override
    public void onPushMsg(Context context, byte[] arg1, String arg2) {
        super.onPushMsg(context, arg1, arg2);
    }

    @Override
    public void onPushState(Context context, boolean arg1) {
        super.onPushState(context, arg1);
    }

    @Override
    public void onToken(Context context, String arg1, Bundle arg2) {
        super.onToken(context, arg1, arg2);
    }

    @Override
    public void onToken(Context context, String arg1) {
        super.onToken(context, arg1);
    }

}