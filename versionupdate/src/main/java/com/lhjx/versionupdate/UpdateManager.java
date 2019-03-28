package com.lhjx.versionupdate;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Author: Created by fangmingdong on 2019/3/27-4:03 PM
 * Description:
 */
public class UpdateManager {
    private static final int NOTIFICATION_FLAG = 8;
    static final int DOWNLOAD_ERROR = 1;
    static final int DOWNLOAD_PROGRESS = 2;
    static final int DOWNLOAD_CANCEL = 3;
    static final int DOWNLOAD_SUCCESS = 4;
    static final String DOWNLOAD_URL = "downloadUrl";
    static final String DOWNLOAD_PATH = "downloadPath";
    static final String DOWNLOAD_RECEIVER = "downloadReceiver";
    private static UpdateManager mInstance;
    private DownloadBuilder mDownloadBuilder;
    private String mDownloadPath = "";
    private String mTempDownloadPath = "";
    //    private Builder mBuilder;
//    private NotificationManager mNotificationManager;
    private Context mContext;

    private UpdateManager() {

    }

    public static UpdateManager getInstance() {
        if (mInstance == null) {
            mInstance = new UpdateManager();
        }

        return mInstance;
    }

    public DownloadBuilder createBuilder() {
        this.mDownloadBuilder = new DownloadBuilder();
        return this.mDownloadBuilder;
    }

    void startDownload() {
        if (this.checkDownloadBuilder()) {
            this.mContext = this.mDownloadBuilder.getContext();
            this.createFile();
        }
    }

    public void stopDownload() {
        DownloadService.stopDownload();
    }

    private void createFile() {
        String apkName = this.mDownloadBuilder.getDownloadFileName() + "_" + this.mDownloadBuilder.getVersionCode() + ".apk";
        String temptApkName = this.mDownloadBuilder.getDownloadFileName() + "_" + this.mDownloadBuilder.getVersionCode() + ".tmp";
        if (UpdateHelper.getSdcardState()) {
            if (this.mContext.getExternalCacheDir() != null) {
                String downloadPath = this.mContext.getExternalCacheDir().getAbsolutePath() + "/" + this.mDownloadBuilder.getDownloadFileName() + "/update/";
                File file = new File(downloadPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                this.mDownloadPath = downloadPath + apkName;
                this.mTempDownloadPath = downloadPath + temptApkName;
                if (this.mDownloadBuilder.getDownloadListener() != null) {
                    this.mDownloadBuilder.getDownloadListener().onDownloadStart();
                }

                File apkFile = new File(this.mDownloadPath);
                if (apkFile.exists()) {
                    if (this.mDownloadBuilder.getDownloadListener() != null) {
                        this.mDownloadBuilder.getDownloadListener().onDownloadFinish();
                    }

                    this.installApk();
                } else {
                    this.initNotification();
                    this.startDownLoadService();
                }
            } else {
                Toast.makeText(this.mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
        }
    }

    private void initNotification() {
//        this.mBuilder = new Builder(this.mDownloadBuilder.getContext());
//        this.mBuilder.setContentTitle(this.mDownloadBuilder.getAppName()).setContentIntent(this.getPendingIntent()).setAutoCancel(true).setContentText("正在下载...").setSmallIcon(this.mDownloadBuilder.getAppLogoResource());
//        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        this.mNotificationManager.notify(8, this.mBuilder.build());
    }

    private PendingIntent getPendingIntent() {
        File apkFile = new File(this.mDownloadPath);
        if (!apkFile.exists()) {
            return null;
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri contentUri = FileProvider.getUriForFile(this.mContext, "com.lhjx.versionupdate.versionProvider", apkFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }

            return PendingIntent.getActivity(this.mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
    }

    private void startDownLoadService() {
        Intent intent = new Intent(this.mContext, DownloadService.class);
        intent.putExtra(DOWNLOAD_URL, this.mDownloadBuilder.getDownloadUrl());
        intent.putExtra(DOWNLOAD_PATH, this.mTempDownloadPath);
        intent.putExtra(DOWNLOAD_RECEIVER, new UpdateManager.UpdateReceiver(new Handler()));
        this.mContext.startService(intent);
    }

    private void downloadFinish() {
        File temFile = new File(this.mTempDownloadPath);
        File apkFile = new File(this.mDownloadPath);
        if (temFile.exists()) {
            temFile.renameTo(apkFile);
        }

//        this.mBuilder.setContentTitle(this.mDownloadBuilder.getAppName());
//        this.mBuilder.setContentText("下载成功");
//        this.mBuilder.setProgress(0, 0, false);
//        this.mBuilder.setContentInfo("100%");
//        this.mBuilder.setContentIntent(this.getPendingIntent());
//        this.mNotificationManager.notify(8, this.mBuilder.build());

        try {
            String[] command = new String[]{"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.installApk();
    }

    private void installApk() {
        File apkFile = new File(this.mDownloadPath);
        if (apkFile.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri contentUri = FileProvider.getUriForFile(this.mContext, "com.lhjx.versionupdate.versionProvider", apkFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }

            this.mContext.startActivity(intent);
        }
    }

    private boolean checkDownloadBuilder() {
        try {
            return UpdateHelper.checkDownloadBuilder(this.mDownloadBuilder);
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    private class UpdateReceiver extends ResultReceiver {
        UpdateReceiver(Handler handler) {
            super(handler);
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            switch (resultCode) {
                case DOWNLOAD_ERROR:
                    String errorMessage = resultData.getString("error");
//                    UpdateManager.this.mBuilder.setContentText("下载失败");
//                    UpdateManager.this.mNotificationManager.notify(8, UpdateManager.this.mBuilder.build());
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onDownloadFail(errorMessage);
                    }
                    break;
                case DOWNLOAD_PROGRESS:
                    int progress = resultData.getInt("progress");
//                    UpdateManager.this.mBuilder.setProgress(100, progress, false);
//                    UpdateManager.this.mBuilder.setContentInfo(progress + "%");
//                    UpdateManager.this.mNotificationManager.notify(8, UpdateManager.this.mBuilder.build());
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onProgressChange(progress);
                    }
                    break;
                case DOWNLOAD_CANCEL:
//                    UpdateManager.this.mNotificationManager.cancel(8);
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onDownloadCancel();
                    }
                    break;
                case DOWNLOAD_SUCCESS:
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onDownloadFinish();
                    }

                    UpdateManager.this.downloadFinish();
            }

        }
    }
}
