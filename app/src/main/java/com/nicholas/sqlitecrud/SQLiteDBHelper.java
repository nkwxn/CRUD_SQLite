package com.nicholas.sqlitecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_info";
    public static final String TABLE_NAME = "tabel_info";

    public static final String row_id = "_id";
    public static final String row_nomor = "NumberPPL";
    public static final String row_nama = "NamePPL";
    public static final String row_jk = "SexPPL";
    public static final String row_tempatLahir = "POBPPL";
    public static final String row_tglLahir = "DOBPPL";
    public static final String row_alamat = "AddressPPL";

    private SQLiteDatabase db;

    // Constructor class SQLiteDBHelper
    public SQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, 2);
        db = getWritableDatabase();
    }

    // Untuk create database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                row_nomor + " TEXT, " +
                row_nama + " TEXT, " +
                row_jk + " TEXT, " +
                row_tempatLahir + " TEXT, " +
                row_tglLahir + " TEXT, " +
                row_alamat + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    // Ketika upgrade
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Cursor allData() {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

    public Cursor oneData(Long id) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + row_id + " = " + id, null);
        return c;
    }

    public void insertData(ContentValues cv) {
        db.insert(TABLE_NAME, null, cv);
    }

    public void updateData(ContentValues cv, long id) {
        db.update(TABLE_NAME, cv, row_id + " = " + id, null);
    }

    public void deleteData(long id) {
        db.delete(TABLE_NAME, row_id + " = " + id, null);
    }
}
