package com.passon.netlib.interceptor;


import com.passon.commonutils.NetUtils;
import com.passon.netlib.exception.NoNetworkException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 无网络状态下，直接抛出异常 NoNetworkException，减少等待时间
 */
public class NetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetUtils.isNetworkConnected()) {
            return chain.proceed(chain.request());
        } else {
            throw new NoNetworkException();
        }
    }
}
