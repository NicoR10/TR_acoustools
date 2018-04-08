package com.example.android.tracoustools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {

        BaseDeDatos.execSQL("create table superficies(material varchar(255) primary key, area double(5,2))");
        BaseDeDatos.execSQL("create table materiales(material varchar(255) primary key, oct125 double(1,2), oct250 double(1,2), oct500 double(1,2), oct1k double(1,2), oct2k double(1,2), oct4k double(1,2), descripcion varchar(255))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
