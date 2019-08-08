package com.passon.commonutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by double on 15/2/4.
 */
public class ToastUtils {

    private static Toast toast;
    private static Toast sIconToast;

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

        //解决Toast多次点击弹出多次 update by zs
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }

        toast.show();

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

    public static void shortShow(CharSequence text) {
        show(CommonUtils.getApp(), text, Toast.LENGTH_SHORT);
    }

    public static void longShow(CharSequence text) {
        show(CommonUtils.getApp(), text, Toast.LENGTH_LONG);
    }

}
