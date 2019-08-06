package com.passon.versionupdate;

import android.os.Environment;
import android.text.TextUtils;

/**
 * Author: Created by fangmingdong on 2019/3/27-4:04 PM
 * Description:
 */
public class UpdateHelper {

    UpdateHelper() {

    }

    static boolean checkDownloadBuilder(DownloadBuilder downloadBuilder) throws Exception {
        if (downloadBuilder == null) {
            throw new Exception("downloadBuilder is null");
        } else if (TextUtils.isEmpty(downloadBuilder.getDownloadUrl())) {
            throw new Exception("downloadUrl is empty");
        } else if (TextUtils.isEmpty(downloadBuilder.getDownloadFileName())) {
            throw new Exception("downloadFileName is empty");
        } else if (TextUtils.isEmpty(downloadBuilder.getAppName())) {
            throw new Exception("appName is empty");
        } else if (downloadBuilder.getAppLogoResource() == 0) {
            throw new Exception("appLogoResource is empty");
        } else if (downloadBuilder.getContext() == null) {
            throw new Exception("context is null");
        } else if (downloadBuilder.getVersionCode() == 0) {
            throw new Exception("versionCode is empty");
        } else if (downloadBuilder.getDownloadListener() == null) {
            throw new Exception("downloadListener is null");
        } else {
            return true;
        }
    }

    public static boolean getSdcardState() {
        String storageState = Environment.getExternalStorageState();
        return storageState.equals("mounted");
    }
}
