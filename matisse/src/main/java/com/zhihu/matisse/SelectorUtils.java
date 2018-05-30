package com.zhihu.matisse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.zrx.CaptureVideoListener;

import java.io.File;
import java.util.List;

/**
 * Author: Created by fangmingdong on 2018/4/11-下午4:25
 * Description:
 */
public class SelectorUtils {

    public static final String IMAGE_FILE_DIR = "ZRXImages";
    public static CaptureVideoListener mListener;

    /**
     * 设置拍摄视频接口
     *
     * @param listener 接口
     */
    public static void setCaptureVideoListener(CaptureVideoListener listener) {
        mListener = listener;
    }

    /**
     * 获取一张图片
     */
    public static void getImg(Activity activity, String authority, int requestCode) {
        getImgs(activity, 1, authority, requestCode);
    }

    /**
     * 获取多张图片 max=9
     */
    public static void getImgs(Activity activity, String authority, int requestCode) {
        getImgs(activity, 9, authority, requestCode);
    }

    /**
     * 获取多张图片 max=9
     *
     * @param imgCnt 图片数量
     */
    public static void getImgs(Activity activity, int imgCnt, String authority, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.ofImage(), false)
                .theme(R.style.Matisse_ZRX)
                .capture(true)
                .showSingleMediaType(true)
                .captureStrategy(new CaptureStrategy(true, authority))
                .maxSelectable(imgCnt)
                .gridExpectedSize(
                        activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(requestCode);
    }


    /**
     * 获取一个视频
     */
    public static void getVideo(Activity activity, String authority, int requestCode) {
        getVideos(activity, 1, authority, requestCode);
    }

    /**
     * 获取多视频
     *
     * @param videoCnt 图片数量
     */
    public static void getVideos(Activity activity, int videoCnt, String authority, int requestCode) {
        Matisse.from(activity)
                .choose(MimeType.ofVideo(), false)
                .theme(R.style.Matisse_ZRX)
                .capture(true)
                .showSingleMediaType(true)
                .captureStrategy(new CaptureStrategy(true, authority))
                .maxSelectable(videoCnt)
                .gridExpectedSize(
                        activity.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(requestCode);
    }

    /**
     * 获取选择结果
     */
    public static List<Uri> obtainResult(Intent data) {
        return Matisse.obtainResult(data);
    }

    /**
     * 获取选择结果
     */
    public static List<String> obtainPathResult(Intent data) {
        return Matisse.obtainPathResult(data);
    }

    public static void getImgFromAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void getVideoFromAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        activity.startActivityForResult(intent, requestCode);
    }

    public static boolean isFileSizeOk(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.length() > 50 * 1024 * 1024) {
            return false;
        }
        return true;
    }


}
