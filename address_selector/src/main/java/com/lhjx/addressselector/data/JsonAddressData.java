package com.lhjx.addressselector.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lhjx.addressselector.Constants;
import com.lhjx.addressselector.bean.City;
import com.lhjx.addressselector.bean.Province;
import com.lhjx.addressselector.utils.GetJsonDataUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/09/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class JsonAddressData implements AssetsAddressData {

    private List<Province> mProvinces;

    @Override
    public void init(Context context) {
        File file = new File(context.getFilesDir(), Constants.JSON_FILE_NAME);
        if (file.exists()) {
            Log.d(Constants.TAG, "init: get address data from new file");
            mProvinces = parseJson(GetJsonDataUtil.getJson(file));
        }else {
            Log.d(Constants.TAG, "init: get address data from assets");
            mProvinces = parseJson(GetJsonDataUtil.getJson(context, "address.json"));
        }
    }

    @Override
    public List<Province> getProvinceList() {
        return mProvinces;
    }

    @Override
    public Province getProvince(String provinceName) {
        if (TextUtils.isEmpty(provinceName)) {
            return null;
        }
        for (int i = 0; i < mProvinces.size(); i++) {
            Province province = mProvinces.get(i);
            if (provinceName.equalsIgnoreCase(province.name)) {
                return province;
            }
        }
        return null;
    }

    @Override
    public List<City> getCityList(String provinceName) {
        if (TextUtils.isEmpty(provinceName)) {
            return null;
        }
        for (int i = 0; i < mProvinces.size(); i++) {
            Province province = mProvinces.get(i);
            if (provinceName.equalsIgnoreCase(province.name)) {
                return province.city;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public City getCity(String cityCode) {
        return null;
    }

    @Override
    public City getCity(String provinceName, String cityName) {
        if (TextUtils.isEmpty(provinceName)) {
            return null;
        }
        for (int i = 0; i < mProvinces.size(); i++) {
            Province province = mProvinces.get(i);
            if (provinceName.equalsIgnoreCase(province.name)) {
                for (City city : province.city) {
                    if (city.name.equalsIgnoreCase(cityName)) {
                        return city;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getAreaList(String provinceName, String cityName) {
        if (TextUtils.isEmpty(provinceName) || TextUtils.isEmpty(cityName)) {
            return null;
        }
        for (int i = 0; i < mProvinces.size(); i++) {
            Province province = mProvinces.get(i);
            if (provinceName.equalsIgnoreCase(province.name)) {
                for (City city : province.city) {
                    if (city.name.equalsIgnoreCase(cityName)) {
                        return city.area;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getArea(String provinceName, String cityName, String area) {
        if (TextUtils.isEmpty(provinceName) || TextUtils.isEmpty(cityName)) {
            return null;
        }
        for (int i = 0; i < mProvinces.size(); i++) {
            Province province = mProvinces.get(i);
            if (provinceName.equalsIgnoreCase(province.name)) {
                for (City city : province.city) {
                    if (city.name.equalsIgnoreCase(cityName)) {
                        for (String s : city.area) {
                            if (s.equalsIgnoreCase(area)) {
                                return s;
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * 解析parseJson
     *
     * @param json json String
     * @return list
     */
    private List<Province> parseJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }

        List<Province> provinces;
        Type type = new TypeToken<List<Province>>() {
        }.getType();
        Gson gson = new Gson();
        provinces = gson.fromJson(json, type);
        return provinces;
    }

}
