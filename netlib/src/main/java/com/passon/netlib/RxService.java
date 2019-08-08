package com.passon.netlib;


import com.passon.netlib.interceptor.CacheInterceptor;
import com.passon.netlib.interceptor.NetWorkInterceptor;
import com.passon.netlib.interceptor.RequestInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 构建 Retrofit
 */
public class RxService {

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static RequestInterceptor requestInterceptor = new RequestInterceptor();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static NetWorkInterceptor sNetWorkInterceptor = new NetWorkInterceptor();

    /** retrofit service缓存 */
    private static Map<String, Object> retrofitServices = new HashMap<>();

    /**
     * 普通请求用 OkHttpClient
     *
     * @param clean false: 表示需要添加请求拦截器，true: 表示不添加请求拦截器,请求不加请求头等自定义信息
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient(boolean clean) {
        //setup cache
        File httpCacheDirectory = new File(NetManager.sContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor);

        if (NetManager.sDEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        if (!clean) {
            builder.addInterceptor(requestInterceptor);
        }

        return builder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)//time out
                .addInterceptor(sNetWorkInterceptor)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .cache(cache)
                .proxySelector(new MyProxySelector())
                .retryOnConnectionFailure(true).build();//失败重连
    }

    /**
     * 下载用 OkHttpClient
     *
     * @param clean false: 表示需要添加请求拦截器，true: 表示请求不加请求头等自定义信息
     * @return OkHttpClient
     */
    private static OkHttpClient getDownloadOkHttpClient(boolean clean) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (NetManager.sDEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(loggingInterceptor);//日志拦截器
        }

        if (!clean) {
            builder.addInterceptor(requestInterceptor);
        }

        return builder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .proxySelector(new MyProxySelector())
                .retryOnConnectionFailure(true).build();//失败重连
    }

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, NetManager.getBaseUrl());
    }

    public synchronized static <T> T createApi(Class<T> clazz, String url) {
        T retrofitService;
        Object serviceObj = retrofitServices.get(clazz.getName() + url);
        if (serviceObj != null) {
            retrofitService = (T) serviceObj;
            return retrofitService;
        }
        retrofitService = getRetrofit(url, false).create(clazz);
        retrofitServices.put(clazz.getName() + url, retrofitService);
        return retrofitService;
    }

    /**
     * 获取 Retrofit
     *
     * @param url base url
     * @return Retrofit
     */
    public static Retrofit getRetrofit(String url, boolean clean) {
        OkHttpClient okHttpClient = getOkHttpClient(clean);

        if (NetManager.sDEBUG) {

        }

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 获取 下载 用的 Retrofit
     *
     * @param clean false: 表示需要添加请求拦截器，true: 表示请求不加请求头等自定义信息
     * @return Retrofit
     */
    public static Retrofit getDownloadRetrofit(boolean clean) {
        return new Retrofit.Builder()
                .baseUrl("http://black")
                .client(getDownloadOkHttpClient(clean))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}

