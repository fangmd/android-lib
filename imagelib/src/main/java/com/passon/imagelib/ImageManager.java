package com.passon.imagelib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lhjx.imagelib.R;
import com.passon.imagelib.progress.ProgressInterceptor;
import com.passon.imagelib.progress.ProgressListener;

import java.io.File;


/**
 * 图片加载管理类
 * Context: 如果是activity glide会与其生命周期关联,在onStop()中取消加载图片,如果
 * 想要始终加载图片则需要传入Application实例
 * Created by double on 2018/4/7.
 */
public class ImageManager {

    private static final String TAG = ImageManager.class.getSimpleName();
    /**
     * 加载过程占位图
     */
    public static int PLACE_HOLDER = R.drawable.default_place_holder;
    /**
     * 加载失败显示的图片
     */
    public static int LOAD_ERROR = R.drawable.default_load_error;

    /**
     * 圆形图片加载占位图
     */
    public static int CIRCLE_PLACE_HOLDER = R.drawable.default_place_holder;
    /**
     * 圆形图片加载失败显示的图
     */
    public static int CIRCLE_LOAD_ERROR = R.drawable.default_load_error;

    /**
     * 默认缩略图压缩比例
     */
    public static float DEFAULT_SIZE_MULTIPLER = 0.3f;


    // Simple load -------------------------------------------------------

    /**
     * load drawable
     */
    public static void load(Context context, @DrawableRes int drawable, ImageView iv) {
        GlideApp.with(context)
                .load(drawable)
                .fitCenter()
                .into(iv);
    }

    public static void load(Context context, File file, ImageView iv) {
        load(context, file, iv, CropType.FIT_CROP);
    }

    public static void load(Context context, File file, ImageView iv, @CropType int cropType) {
        GlideRequest<Drawable> request = GlideApp.with(context)
                .load(file);
        switch (cropType) {
            case CropType.CENTER_CROP:
                request.centerCrop();
                break;
            case CropType.FIT_CROP:
                request.fitCenter();
                break;
            case CropType.CENTER_INSIDE:
                request.centerInside();
            case CropType.NONE:
                break;
            default:
                Log.e(TAG, "load: error crop type");
                break;
        }
        request.into(iv);
    }

    /**
     * Simple load
     */
    public static void load(String url, ImageView iv) {
        load(GlideApp.with(iv.getContext()), url, iv);
    }

    public static void load(String url, ImageView iv, @CropType int cropType) {
        load(GlideApp.with(iv.getContext()), url, iv, PLACE_HOLDER, LOAD_ERROR, cropType);
    }

    public static void load(Fragment fragment, String url, ImageView iv) {
        GlideRequests with = GlideApp.with(fragment);
        load(with, url, iv);
    }

    public static void load(Fragment fragment, String url, ImageView iv, @CropType int cropType) {
        GlideRequests with = GlideApp.with(fragment);
        load(with, url, iv, PLACE_HOLDER, LOAD_ERROR, cropType);
    }

    public static void load(Context context, String url, ImageView iv) {
        GlideRequests with = GlideApp.with(context);
        load(with, url, iv);
    }

    public static void load(Context context, String url, ImageView iv, @DrawableRes int placeHolder, @DrawableRes int error) {
        GlideRequests with = GlideApp.with(context);
        load(with, url, iv, placeHolder, error);
    }

    public static void load(Context context, String url, ImageView iv, @CropType int cropType) {
        GlideRequests with = GlideApp.with(context);
        load(with, url, iv, PLACE_HOLDER, LOAD_ERROR, cropType);
    }

    public static void load(Context context, String url, ImageView iv, @DrawableRes int placeHolder, @DrawableRes int error, @CropType int cropType) {
        GlideRequests with = GlideApp.with(context);
        load(with, url, iv, placeHolder, error, cropType);
    }

    private static void load(GlideRequests with, String url, ImageView iv) {
        load(with, url, iv, PLACE_HOLDER, LOAD_ERROR);
    }

    private static void load(GlideRequests with, String url, ImageView iv, @DrawableRes int placeHolder, @DrawableRes int error) {
        load(with, url, iv, placeHolder, error, CropType.FIT_CROP);
    }

    private static void load(GlideRequests with, String url, ImageView iv, @DrawableRes int placeHolder, @DrawableRes int error, @CropType int cropType) {
        GlideRequest<Drawable> request = with
//                .asBitmap()
                .load(url)
                .placeholder(placeHolder)
                .error(error);

        switch (cropType) {
            case CropType.CENTER_CROP:
                request.centerCrop();
                break;
            case CropType.FIT_CROP:
                request.fitCenter();
                break;
            case CropType.CENTER_INSIDE:
                request.centerInside();
            case CropType.NONE:
                break;
            default:
                Log.e(TAG, "load: error crop type");
                break;
        }

        request.into(iv);
    }

    // --------------------- **** ----------------------------------

    public static void loadCircle(Fragment fragment, String url, ImageView iv) {
        GlideRequests with = GlideApp.with(fragment);
        loadCircle(with, url, iv, CIRCLE_PLACE_HOLDER, CIRCLE_LOAD_ERROR);
    }

    public static void loadCircle(Context context, String url, ImageView iv) {
        GlideRequests with = GlideApp.with(context);
        loadCircle(with, url, iv, CIRCLE_PLACE_HOLDER, CIRCLE_LOAD_ERROR);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircle(GlideRequests requests, String url, ImageView iv, @DrawableRes int placeHolder, @DrawableRes int loadError) {
        requests.asBitmap()
                .load(url)
                .circleCrop()
                .placeholder(placeHolder)
                .error(loadError)
                .into(iv);
    }


    /**
     * 加载图片，并获取加载进度
     */
    public static void loadWithProgress(Context context, String url, ImageView iv, ProgressListener progressListener) {
        GlideRequests with = GlideApp.with(context);
        loadWithProgress(with, url, iv, progressListener);
    }

    public static void loadWithProgress(Fragment fragment, String url, ImageView iv, ProgressListener progressListener) {
        GlideRequests with = GlideApp.with(fragment);
        loadWithProgress(with, url, iv, progressListener);
    }

    private static void loadWithProgress(GlideRequests requests, final String url, ImageView iv, ProgressListener progressListener) {
        ProgressInterceptor.addListener(url, progressListener);

        requests.asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(url);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(url);
                        return false;
                    }
                })
                .into(iv);
    }

    /**
     * 加载全屏图片
     * 更具屏幕大小 压缩图片
     */
    public static void loadFullScreenImg(Context context, String url, ImageView iv) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        Log.d("ImageManager", "loadFullScreenImg: " + widthPixels + "x" + heightPixels);
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .override(widthPixels, heightPixels)
                .into(iv);
    }

    /**
     * 加载缩略图
     *
     * @param context context
     * @param width   width
     * @param height  height
     * @param url     url
     * @param iv      ImageView
     */
    public static void loadThumbnail(Context context, int width, int height, String url, ImageView iv) {
        int measuredHeight = iv.getMeasuredHeight();
        int measuredWidth = iv.getMeasuredWidth();
        Log.d(TAG, "loadThumbnail: " + measuredWidth + "x" + measuredHeight);
        GlideApp.with(context)
                .load(url)
                .override(width, height)
                .centerCrop()
                .into(iv);
    }

    /**
     * 下载图片,耗时操作不能放在主线程中进行
     * 下载的图片地址在：文件名：{@link MyGlideModule#CACHE_FILE_NAME}
     *
     * @param context               context
     * @param url                   url
     * @param imageDownloadListener Listener
     */
    public static void downloadImage(Context context, String url, final IImageDownloadListener imageDownloadListener) {
        try {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean
                        isFirstResource) {
                    imageDownloadListener.downloadFail();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource
                        dataSource, boolean isFirstResource) {
                    imageDownloadListener.downloadSuccess(resource);
                    return false;
                }
            }).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除缓存
     */
    public static void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearDiskCache(context);
            }
        }).start();
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundedCornersImg(Context context, String url, ImageView target, int radius, @DrawableRes int placeHolder, @DrawableRes int loadError) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(loadError)
                .transform(new RoundedCorners(radius))
                .into(target);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundedCornersImg(Context context, String url, ImageView target, int radius) {
        loadRoundedCornersImg(context, url, target, radius, PLACE_HOLDER, LOAD_ERROR);
    }

    // ************************ test *************************************

    @SuppressLint("CheckResult")
    public static void load(Context context, String url, ImageView iv, final IImageLoadListener listener) {
        RequestBuilder requestBuilder = GlideApp.with(context).load(url);
        //添加加载的监听
        requestBuilder.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean
                    isFirstResource) {
                listener.loadFail();
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                listener.loadSuccess();
                return false;
            }
        });
        //缩略图
//        requestBuilder.thumbnail(0.5f);
        requestBuilder.load(url);
        requestBuilder.into(iv);
    }


//    public static RequestOptions getCircleRequestOptions() {
//        RequestOptions options = new RequestOptions();
//options.format(DecodeFormat.PREFER_ARGB_8888)
    //options.centerCrop()//图片显示类型
    //options.placeholder(loadingRes)//加载中图片
    //options.error(errorRes)//加载错误的图片
    //options.error(new ColorDrawable(Color.RED))//或者是个颜色值
    //options.priority(Priority.HIGH)//设置请求优先级
    //options.diskCacheStrategy(DiskCacheStrategy.ALL);
    //options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)//仅缓存原图片
    //options.diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
    //options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)缓存缩略过的
    //options.onlyRetrieveFromCache(true)//仅从缓存加载
    //options.skipMemoryCache(true)//禁用缓存,包括内存和磁盘
    //options.diskCacheStrategy(DiskCacheStrategy.NONE)仅跳过磁盘缓存
    //options.override(200,200)加载固定大小的图片
    //options.dontTransform()不做渐入渐出的转换
    //options.transition(new DrawableTransitionOptions().dontTransition())//同上
//        options.circleCrop();//设置成圆形头像<这个是V4.0新增的>
    //options.transform(new RoundedCorners(10))设置成圆角头像<这个是V4.0新增的>
//        return options;
//    }


}
