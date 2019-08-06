package com.passon.versionupdate.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.passon.versionupdate.VersionUpdateC;

import java.io.File;

/**
 * Author: Created by fangmingdong on 2019-08-06-14:08
 * Description:
 */
public class ApkUtils {

    public static void installApk(Context context, String filePath) {
        File apkFile = new File(filePath);
        if (!apkFile.exists()) {
            Log.w(VersionUpdateC.TAG, "installApk: File is not exists");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, "com.passon.versionupdate.versionProvider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);
    }


}
