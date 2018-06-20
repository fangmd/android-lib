package com.lhjx.sharelib;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Author: Created by fangmingdong on 2018/6/13-下午4:19
 * Description:
 */
public class ShareManager {

    /**
     * 设置微信账号
     */
    public static void setWeiXin(String appKey, String appSecret) {
        PlatformConfig.setWeixin(appKey, appSecret);
    }

    /**
     * 设置新浪微博账号
     */
    public static void setSinaWeiBo(String appKey, String appSecret, String url) {
        PlatformConfig.setSinaWeibo(appKey, appSecret, url);
    }

    /**
     * 设置 qq 空间
     */
    public static void setQQZone(String appKey, String appSecret) {
        PlatformConfig.setQQZone(appKey, appSecret);
    }

    /**
     * 分享网页连接
     */
    public static void shareWeb(Activity activity, String url, String thumbUrl, String title, String desc, UMShareListener umShareListener) {
        UMImage thumb = null;
        if (TextUtils.isEmpty(thumbUrl)) {
//            thumb = new UMImage(activity, R.mipmap.ic_launcher);
        } else {
            thumb = new UMImage(activity, thumbUrl);
        }

        UMWeb web = new UMWeb(url);
        web.setTitle(title); //标题
        web.setThumb(thumb);
        web.setDescription(desc);//描述

        new ShareAction(activity)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ)
                .setCallback(umShareListener)
                .open();
    }

    /**
     * 分享图片
     */
    public static void shareImg(Activity activity, String imgBase64, UMShareListener umShareListener) {
        UMImage umImage = new UMImage(activity, ShareUtils.decodeBase64(imgBase64));

        new ShareAction(activity)
                .withMedia(umImage)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ)
                .setCallback(umShareListener)
                .open();
    }

    /**
     *
     //豆瓣RENREN平台目前只能在服务器端配置
     PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
     PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
     PlatformConfig.setAlipay("2015111700822536");
     PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
     PlatformConfig.setPinterest("1439206");
     PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
     PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
     PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
     PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
     PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
     */

}
