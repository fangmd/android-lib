package com.lhjx.versionupdate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * download Service
 */
public class DownloadService extends IntentService {
    public static boolean mStopDownload;

    public DownloadService() {
        super("DownloadService");
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String downloadUrl = intent.getStringExtra(UpdateManager.DOWNLOAD_URL);
            String downloadPath = intent.getStringExtra(UpdateManager.DOWNLOAD_PATH);
            ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra(UpdateManager.DOWNLOAD_RECEIVER);
            Bundle resultData = new Bundle();
            if (!TextUtils.isEmpty(downloadUrl) && !TextUtils.isEmpty(downloadPath)) {
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                FileOutputStream fos = null;

                try {
                    conn = (HttpURLConnection) (new URL(downloadUrl)).openConnection();
                    conn.connect();
                    resultData.putInt("progress", 0);
                    if (receiver != null) {
                        receiver.send(2, resultData);
                    }

                    inputStream = conn.getInputStream();
                    fos = new FileOutputStream(downloadPath);
                    int totalSize = conn.getContentLength();
                    byte[] buffer = new byte[4096];
                    long progress = 0L;
                    long progressUpdateTime = 0L;

                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        if (mStopDownload) {
                            mStopDownload = false;
                            if (receiver != null) {
                                receiver.send(3, resultData);
                            }

                            return;
                        }

                        fos.write(buffer, 0, length);
                        progress += (long) length;
                        long nowTime = System.currentTimeMillis();
                        if (nowTime - progressUpdateTime > 200L) {
                            progressUpdateTime = nowTime;
                            resultData.putInt("progress", (int) (progress * 100L / (long) totalSize));
                            if (receiver != null) {
                                receiver.send(2, resultData);
                            }
                        }
                    }

                    fos.flush();
                    if (receiver != null) {
                        receiver.send(4, resultData);
                    }

                    if (!mStopDownload) {
                        return;
                    }

                    mStopDownload = false;
                    if (receiver != null) {
                        receiver.send(3, resultData);
                    }
                } catch (IOException var44) {
                    var44.printStackTrace();
                    resultData.putString("error", "下载失败,请检查网络配置");
                    if (receiver != null) {
                        receiver.send(1, resultData);
                    }

                    return;
                } catch (Exception var45) {
                    var45.printStackTrace();
                    resultData.putString("error", "下载失败,请检查网络配置");
                    if (receiver != null) {
                        receiver.send(1, resultData);
                    }

                    return;
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException var43) {

                    }

                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception var42) {

                    }

                    try {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (Exception var41) {

                    }

                }

            } else {
                if (receiver != null) {
                    resultData.putString("error", "下载初始化失败");
                    receiver.send(1, resultData);
                }

            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public static void stopDownload() {
        mStopDownload = true;
    }
}
