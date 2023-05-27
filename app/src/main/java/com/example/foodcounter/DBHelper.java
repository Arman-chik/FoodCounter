package com.example.foodcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String MY_TABLE = "Products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT = "Name";
    public static final String COLUMN_KALL = "Calories";
    public static final String COLUMN_UGL = "Uglevod";
    public static final String COLUMN_ZHIRI = "Zhiri";
    public static final String COLUMN_BELKI = "Belki";


    public DBHelper(@Nullable Context context) {
        super(context, "example.db", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCT + " TEXT, " + COLUMN_KALL + " DOUBLE, " + COLUMN_UGL + " DOUBLE," +
                " " + COLUMN_ZHIRI + " DOUBLE, " + COLUMN_BELKI + " DOUBLE );");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void DeletedAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MY_TABLE, null, null);
        db.close();
    }

    public void AddOne(Products products){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCT, products.name);
        cv.put(COLUMN_KALL, products.calories);
        cv.put(COLUMN_UGL, products.uglevod);
        cv.put(COLUMN_ZHIRI, products.zhiri);
        cv.put(COLUMN_BELKI, products.belki);

        db.insert(MY_TABLE, null, cv);
        db.close();
    }


    public LinkedList<Products> GetAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        LinkedList<Products> list = new LinkedList<>();

        Cursor cursor = db.query(MY_TABLE, null, null, null, null, null, null);

        if(cursor.moveToFirst())
            do{
                int id_id = cursor.getColumnIndex(COLUMN_ID);
                int id_n = cursor.getColumnIndex(COLUMN_PRODUCT);
                int id_k = cursor.getColumnIndex(COLUMN_KALL);
                int id_ug = cursor.getColumnIndex(COLUMN_UGL);
                int id_zh = cursor.getColumnIndex(COLUMN_ZHIRI);
                int id_b = cursor.getColumnIndex(COLUMN_BELKI);

                Products products = new Products(cursor.getLong(id_id), cursor.getString(id_n), cursor.getDouble(id_k), cursor.getDouble(id_ug), cursor.getDouble(id_zh), cursor.getDouble(id_b));
                list.add(products);

            }while (cursor.moveToNext());

        db.close();
        return list;
    }


}
