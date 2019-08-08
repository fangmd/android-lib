package com.passon.netlib.exception;

/**
 * Author: Created by fangmingdong on 2018/11/27-11:33 AM
 * Description:
 */
public class NoNetworkException extends RuntimeException {

    @Override
    public String getMessage() {
        return "no net work";
    }
}
