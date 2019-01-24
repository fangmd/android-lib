package com.lhjx.androidlibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by yabusai on 15/2/4.
 */
public class ToastUtils {

    private static Toast sShortToast;
    private static Toast sLongToast;

    private static Handler mHandler;

    static {
        mHandler = new Handler(Looper.getMainLooper());
    }

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (context == null) {
            return;
        }

        if (duration == Toast.LENGTH_SHORT) {
            if (sShortToast == null) {
                sShortToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
                sShortToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                sShortToast.setText(text);
            }

            mHandler.post(() -> sShortToast.show());
        } else {
            if (sLongToast == null) {
                sLongToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
                sLongToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                sLongToast.setText(text);
            }

            mHandler.post(() -> sLongToast.show());
        }
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }

    public static void shortShow(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }
}
