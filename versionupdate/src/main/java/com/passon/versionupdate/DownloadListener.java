package com.passon.versionupdate;

/**
 * Author: Created by fangmingdong on 2019/3/27-4:06 PM
 * Description:
 */
public interface DownloadListener {
    void onDownloadStart();

    void onDownloadFail(String var1);

    void onProgressChange(int var1);

    void onDownloadCancel();

    void onDownloadFinish();
}
