package com.passon.commonutils;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: Created by fangmingdong on 2019-08-08-15:34
 * Description:
 */
public class ContextUtils {

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }

}
