package com.lhjx.pushlibrary;

/**
 * Author: Created by fangmingdong on 2018/6/7-上午10:44
 * Description: 推送启动结果回调
 */
public interface PushCallback {

    void onSuccess(Object data, int flag);

    void onFail(Object data, int errCode, String msg);

}
