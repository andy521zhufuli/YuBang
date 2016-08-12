package com.tec.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tec.android.utils.L;

/**
 * Created by andy on 15/8/4.
 */
public class SQLiteHelper extends SQLiteOpenHelper{


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                        "shoppingcar"+ "("+
                        "goodsid"+ " integer primary key,"+
                        "goodsdetail"+ " varchar,"+
                        "goodscount"+ " integer"+
                        ")"
        );
        L.i("Database" ,"onCreate" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
