package com.example.phuong201200281_taxi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Sqlite_MaDe extends SQLiteOpenHelper {
    //tên bảng
     public static  final String TableName = "Taxi_Phuong";

    public  static final String Id = "Id";
    public  static final String Ten = "Ten";
    public  static final String  Quangduong= "Quangduong";
    public  static final String Khuyenmai = "Khuyenmai";
     public  static final String Dongia = "Dongia";

    public Sqlite_MaDe(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "Create table if not exists " + TableName + "(" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ten + " Text, "
                + Quangduong + " Text, "
                 + Khuyenmai + " Text, "
                + Dongia + " Text)";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }
    public ArrayList<Taxi> GetAllContact(){
        ArrayList<Taxi> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Taxi contact = new Taxi(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4));
                list.add(contact);
            }
        }
        return list;
    }
    public void addContact(Taxi contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Ten,contact.getSoXe());
        values.put(Quangduong,contact.getQuangDuong());
        values.put(Dongia,contact.getDonGia());
        values.put(Khuyenmai,contact.getKhuyenMai());
        db.insert(TableName,null,values);
        db.close();
    }
    public void UpdateContact(int id, Taxi contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
         values.put(Id,contact.getId());
       values.put(Ten,contact.getSoXe());
        values.put(Quangduong,contact.getQuangDuong());
        values.put(Dongia,contact.getDonGia());
        values.put(Khuyenmai,contact.getKhuyenMai());
        db.update(TableName,values, Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from " + TableName + " Where ID=" + id;
        db.execSQL(sql);
        db.close();
    }
}
