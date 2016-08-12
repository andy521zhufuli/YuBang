package com.tec.android.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by andy on 15/8/4.
 */
public class ShoppingCarDB implements ShoppingCarDao {
    @Override
    public boolean insert(SQLiteDatabase db, ContentValues values) {

        return false;
    }

    @Override
    public boolean update(SQLiteDatabase db, ContentValues values, Integer id) {
        return false;
    }

    @Override
    public boolean delete(SQLiteDatabase db, Integer id) {
        return false;
    }

    @Override
    public List<ShoppingCarInfoBean> findAll(SQLiteDatabase db) {
        return null;
    }

    @Override
    public List<ShoppingCarInfoBean> findByName(SQLiteDatabase db, String[] selectionArgs) {
        return null;
    }

    @Override
    public List<ShoppingCarInfoBean> getNowPageInfo(SQLiteDatabase db, String[] selectionArgs, String order, String limit) {
        return null;
    }
}
