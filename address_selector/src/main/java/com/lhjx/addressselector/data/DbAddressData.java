//package com.smarttop.library.data;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.smarttop.library.bean.City;
//import com.smarttop.library.bean.Province;
//import com.smarttop.library.db.AssetsDatabaseManager;
//import com.smarttop.library.db.TableField;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.smarttop.library.db.TableField.ADDRESS_DICT_FIELD_CODE;
//import static com.smarttop.library.db.TableField.ADDRESS_DICT_FIELD_ID;
//import static com.smarttop.library.db.TableField.ADDRESS_DICT_FIELD_PARENTID;
//
///**
// * <pre>
// *     author : cfp
// *     e-mail : chengfangpeng@foxmail.com
// *     time   : 2017/09/19
// *     desc   :
// *     version: 1.0
// * </pre>
// */
//public class DbAddressData implements AssetsAddressData {
//    private SQLiteDatabase db;
//
//    @Override
//    public void init(Context context) {
//        // 初始化，只需要调用一次
//        AssetsDatabaseManager.initManager(context);
//        // 获取管理对象，因为数据库需要通过管理对象才能够获取
//        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
//        // 通过管理对象获取数据库
//        db = mg.getDatabase("address.db");
//    }
//
//    @Override
//    public List<Province> getProvinceList() {
//        List<Province> provinceList = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TableField.TABLE_ADDRESS_DICT+" where "+ ADDRESS_DICT_FIELD_PARENTID+"=?", new String[]{String.valueOf(0)});
//        while (cursor.moveToNext()){
//            Province province =  new Province();
//            province.id = String.valueOf(cursor.getInt(cursor.getColumnIndex(ADDRESS_DICT_FIELD_ID)));
//            province.code = cursor.getString(cursor.getColumnIndex(ADDRESS_DICT_FIELD_CODE));
//            province.name = cursor.getString(cursor.getColumnIndex(TableField.ADDRESS_DICT_FIELD_NAME));
//            provinceList.add(province);
//        }
//        cursor.close();
//
//        return provinceList;
//    }
//
//    @Override
//    public Province getProvince(int provinceId) {
//        Cursor cursor = db.rawQuery("select * from " + TableField.TABLE_ADDRESS_DICT+" where "+ ADDRESS_DICT_FIELD_CODE+"=?", new String[]{String.valueOf(provinceId)});
//        if(cursor!=null && cursor.moveToFirst()){
//            Province province =  new Province();
//            province.id = String.valueOf(cursor.getInt(cursor.getColumnIndex(ADDRESS_DICT_FIELD_ID)));
//            province.code = cursor.getString(cursor.getColumnIndex(ADDRESS_DICT_FIELD_CODE));
//            province.name = cursor.getString(cursor.getColumnIndex(TableField.ADDRESS_DICT_FIELD_NAME));
//            cursor.close();
//            return province;
//        }else{
//            return null;
//        }
//    }
//
//    @Override
//    public List<City> getCityList(int provinceId) {
//        List<City> cityList = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TableField.TABLE_ADDRESS_DICT+" where "+ ADDRESS_DICT_FIELD_PARENTID+"=?", new String[]{String.valueOf(provinceId)});
//        while (cursor.moveToNext()){
//            City city =  new City();
//            city.id = cursor.getInt(cursor.getColumnIndex(ADDRESS_DICT_FIELD_ID));
//            city.code = cursor.getString(cursor.getColumnIndex(ADDRESS_DICT_FIELD_CODE));
//            city.name = cursor.getString(cursor.getColumnIndex(TableField.ADDRESS_DICT_FIELD_NAME));
//            cityList.add(city);
//        }
//        cursor.close();
//        return cityList;
//    }
//
//    @Override
//    public City getCity(String cityCode) {
//        Cursor cursor = db.rawQuery("select * from " + TableField.TABLE_ADDRESS_DICT+" where "+ ADDRESS_DICT_FIELD_CODE+"=?", new String[]{cityCode});
//        if(cursor!=null && cursor.moveToFirst()){
//            City city =  new City();
//            city.id = cursor.getInt(cursor.getColumnIndex(ADDRESS_DICT_FIELD_ID));
//            city.code = cursor.getString(cursor.getColumnIndex(ADDRESS_DICT_FIELD_CODE));
//            city.name = cursor.getString(cursor.getColumnIndex(TableField.ADDRESS_DICT_FIELD_NAME));
//            cursor.close();
//            return city;
//        }else{
//            return null;
//        }
//    }
//}
