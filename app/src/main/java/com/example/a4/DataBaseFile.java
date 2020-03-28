package com.example.a4;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DataBaseFile extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "a4_db.db";
    public static final String TABLE_NAME = "Spending";

    public static final String COL_NAME_DATE = "Date";
    public static final String COL_NAME_PRICE = "Price";
    public static final String COL_NAME_ITEM = "Item";

    public DataBaseFile(Context context){
        super(context, DATABASE_NAME, null, 4);
    }

    public void onCreate(SQLiteDatabase sqlDB){
        sqlDB.execSQL("CREATE TABLE " + TABLE_NAME + " (Count INTEGER PRIMARY KEY AUTOINCREMENT, Item varchar(100), Date varchar(100), Price DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int older, int newer){
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }


    public boolean createHistory(String data, double price, String item){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME_DATE, data);
        contentValues.put(COL_NAME_PRICE, price);
        contentValues.put(COL_NAME_ITEM, item);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor pullData() {
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor res = sqlDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}