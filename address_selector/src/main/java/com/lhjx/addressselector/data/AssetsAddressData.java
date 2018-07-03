package com.lhjx.addressselector.data;

import android.content.Context;

import com.lhjx.addressselector.bean.City;
import com.lhjx.addressselector.bean.Province;

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
public interface AssetsAddressData {


    /**
     * 初始化
     */
    void init(Context context);


    /**
     * 获取省列表
     *
     * @return
     */
    List<Province> getProvinceList();


    /**
     * 获取省
     *
     * @param provinceId
     * @return
     */
    Province getProvince(String provinceId);


    /**
     * 获取省对应的城市列表
     *
     * @param provinceId
     * @return
     */
    List<City> getCityList(String provinceId);


    /**
     * 获取城市
     *
     * @param cityCode
     * @return
     */
    City getCity(String cityCode);

    City getCity(String provinceName, String cityName);

    List<String> getAreaList(String province, String cityName);

    String getArea(String province, String cityName, String area);
}
