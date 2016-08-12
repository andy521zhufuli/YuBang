package com.tec.android.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by andy on 15/8/4.
 */
public interface ShoppingCarDao {
    //添加的操作
    public boolean insert(SQLiteDatabase db,ContentValues values);
    //执行更新的操作
    public boolean update(SQLiteDatabase db,ContentValues values,Integer id);
    //根据id删除操作
    public boolean delete(SQLiteDatabase db,Integer id);
    //查询所有
    public List<ShoppingCarInfoBean> findAll(SQLiteDatabase db);
    //条件查询操作
    public List<ShoppingCarInfoBean> findByName(SQLiteDatabase db,String[] selectionArgs);
    //获取当前页的信息
    public List<ShoppingCarInfoBean> getNowPageInfo(SQLiteDatabase db,String[] selectionArgs,String order,String limit);


}
