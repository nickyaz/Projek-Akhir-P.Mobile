package com.example.projekakhir191103624nickyazriel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "project.db";

    public DBHelper(Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table pemain(nomor TEXT primary key, nama TEXT, jeniskelamin TEXT, posisi TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists pemain");
    }

    public Boolean inserData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == 1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean insertDataPMN (String nomor, String nama, String jeniskelamin, String posisi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nomor",nomor);
        values.put("nama",nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("posisi", posisi);
        long result = db.insert("pemain", null,values);
        if (result ==1) return false;
        else
            return true;
    }
    public Cursor tampilDataPMN(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from pemain", null);
        return cursor;

    }

    public boolean hapusDataPMN(String nomor){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from pemain where nomor=?", new String[]{nomor});
        if (cursor.getCount()>0) {
            long result = db.delete("pemain", "nomor=?", new String[]{nomor});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Boolean editDataPMN(String nomor, String nama, String jeniskelamin, String posisi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama",nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("posisi", posisi);

        Cursor cursor = db.rawQuery("Select * from pemain where nomor=?", new String[]{nomor});
        if (cursor.getCount()>0) {
            long result = db.update("pemain", values, "nomor=?", new String[]{nomor});
            if (result == 1) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public Boolean cekno(String nomor){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from pemain where nomor=?", new String[] {nomor});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }
}