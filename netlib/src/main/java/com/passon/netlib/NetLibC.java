package com.passon.netlib;

/**
 * Author: Created by fangmingdong on 2019-08-07-15:56
 * Description:
 */
public class NetLibC {

    public static final String ERROR_NOT_INIT = "Net Lib is not init, Call NetManager.init() in Application";

    public static class NetC {

        /** 无网络时，提示信息 */
        public static final String ERROR_NETWORK = "网络不给力,请检查网络配置";

        /** token过期 */
        public static final String SESSION_INVALID = "session失效";

    }
}
