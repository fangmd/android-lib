package com.passon.versionupdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.passon.versionupdate.utils.ApkUtils;

import java.io.File;

/**
 * Author: Created by fangmingdong on 2019/3/27-4:03 PM
 * Description:
 */
public class UpdateManager {
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
                String downloadPath = this.mContext.getExternalCacheDir().getAbsolutePath() + "/update/";
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

                    ApkUtils.installApk(mContext, this.mDownloadPath);
                } else {
                    this.startDownLoadService();
                }
            } else {
                Toast.makeText(this.mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.mContext, "sd卡暂不可用", Toast.LENGTH_SHORT).show();
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

        ApkUtils.installApk(mContext, this.mDownloadPath);
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
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onDownloadFail(errorMessage);
                    }
                    break;
                case DOWNLOAD_PROGRESS:
                    int progress = resultData.getInt("progress");
                    if (UpdateManager.this.mDownloadBuilder.getDownloadListener() != null) {
                        UpdateManager.this.mDownloadBuilder.getDownloadListener().onProgressChange(progress);
                    }
                    break;
                case DOWNLOAD_CANCEL:
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
