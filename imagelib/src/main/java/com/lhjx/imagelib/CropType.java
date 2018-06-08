package com.lhjx.imagelib;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lhjx.imagelib.CropType.CENTER_CROP;
import static com.lhjx.imagelib.CropType.FIT_CROP;
import static com.lhjx.imagelib.CropType.NONE;
import static com.lhjx.imagelib.CropType.CENTER_INSIDE;


/**
 * Glide 图片裁剪方式
 * Created by double on 2018/4/7.
 */

@IntDef({CENTER_CROP, FIT_CROP, CENTER_INSIDE,NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface CropType {

    /**
     * 缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView
     */
    public static final int FIT_CROP = 0x00000001;


    /**
     * 缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分。ImageView 会被完全填充，但图像可能不会完整显示
     */
    public static final int CENTER_CROP = 0x00000002;

    public static final int CENTER_INSIDE = 0x00000003;

    /**
     * 不设置
     */
    public static final int NONE = 0x00000004;



}