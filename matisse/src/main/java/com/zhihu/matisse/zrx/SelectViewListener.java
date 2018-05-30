package com.zhihu.matisse.zrx;

import android.net.Uri;

/**
 * Author: Created by fangmingdong on 2018/4/13-下午6:25
 * Description: ImageSelectView 点击接口
 */
public interface SelectViewListener {

    void cancelUpload(String filePath);

    void reUpload(String filePath);

    void selectFile();

    void cancelSelected(String filePath);

    void selectedFileClick(String filePath, Uri uri, String fileUrl);
}
