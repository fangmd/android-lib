package com.lhjx.addressselector.widget;


import com.lhjx.addressselector.bean.City;
import com.lhjx.addressselector.bean.Province;
import com.lhjx.addressselector.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, String county, Street street);
}
