package com.lhjx.sharelib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Author: Created by fangmingdong on 2018/6/20-下午1:27
 * Description:
 */
public class ShareUtils {

    /**
     * String 转base64
     *
     * @param input 输入的String
     * @return 返回bitmap
     */
    public static Bitmap decodeBase64(String input) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(input, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
