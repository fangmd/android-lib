package com.lhjx.addressselector.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.lhjx.addressselector.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/09/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetJsonDataUtil {


    /**
     * 从assets中获取json数据
     *
     * @param context  context
     * @param fileName fileName
     * @return String
     */
    public static String getJson(Context context, String fileName) {

        StringBuilder sb = new StringBuilder();

        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 读取文件中 String 数据
     *
     * @param file 文件
     * @return String
     */
    public static String getJson(File file) {
        if (!file.exists()) {
            Log.e(Constants.TAG, "getJson: JSON File is not exist");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
