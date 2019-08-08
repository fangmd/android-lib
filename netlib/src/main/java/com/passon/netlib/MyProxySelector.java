package com.passon.netlib;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Author: Created by fangmingdong on 2018/10/31-3:04 PM
 * Description: OkHttpClient 绕过代理，防止抓包
 */
public class MyProxySelector extends ProxySelector {

    @Override
    public List<Proxy> select(URI uri) {
        return Collections.singletonList(Proxy.NO_PROXY);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

    }
}
