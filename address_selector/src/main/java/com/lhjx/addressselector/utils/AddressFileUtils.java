package com.lhjx.addressselector.utils;

import android.content.Context;
import android.util.Log;

import com.lhjx.addressselector.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.lhjx.addressselector.Constants.TAG;

/**
 * Author: Created by fangmingdong on 2018/6/11-下午1:22
 * Description:
 */
public class AddressFileUtils {

    /**
     * 保存地址到本地
     *
     * @param json json
     */
    public static void saveJson(Context context, String json) {
        File file = new File(context.getFilesDir(), Constants.JSON_FILE_NAME);
        if (!file.exists()) {
            try {
                boolean create = file.createNewFile();
                Log.e(TAG, "saveJson: create address file:" + create);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "saveJson: create file fail");
                return;
            }
        }

        FileWriter fw = null;
        if (file.exists()) {
            try {
                fw = new FileWriter(file);
                fw.write(json);
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
