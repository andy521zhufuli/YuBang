package com.car.yubangapk.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andy on 16/5/14.
 *
 * 搜索历史记录
 *
 */
public class ShopRecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String name = "shoptemp.db";
    private static Integer version = 1;
    private  int  type = 1;
    public static int SHOP_TYPE = 1;//店铺搜索历史
    public static int PRODUCT_TYPE = 2;//产品包搜索历史
    public ShopRecordSQLiteOpenHelper(Context context, int type) {
        super(context, name, null, version);
        this.type = type;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table shoprecords(id integer primary key autoincrement,name varchar(200))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}