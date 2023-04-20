package com.example.sqlitedemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitedemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chitieu.db";
    private static  int DATABASE_VERSION = 1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "category TEXT,"+
                "price TEXT," +
                "date TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String oder = "date DESC";
        Cursor rs = st.query("items",null,null,null,null,null,oder);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            Item item = new Item(id,title,category,price,date);
            list.add(item);
        }
        rs.close();
        return list;
    }
    public long addItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title",item.getTitle());
        values.put("category",item.getCategory());
        values.put("price",item.getPrice());
        values.put("date",item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("items",null,values);
    }

    public List<Item> getListItemByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date LIKE ?";
        String[] wherearg = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,wherearg,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String d = rs.getString(4);
            Item item = new Item(id,title,category,price,d);
            list.add(item);
        }
        rs.close();
        return list;
    }
    public int update(Item item){
        ContentValues values = new ContentValues();
        values.put("title",item.getTitle());
        values.put("category",item.getCategory());
        values.put("price",item.getPrice());
        values.put("date",item.getDate());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(item.getId())};
        return db.update("items",values,whereClause,wherearg);
    }
    public int delete(int id){
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("items",whereClause,wherearg);
    }

    public List<Item> getListItemBytitle(String title) {
        List<Item> list = new ArrayList<>();
        String whereClause = "title LIKE ?";
        String[] wherearg = {"%" + title + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String t = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String d = rs.getString(4);
            Item item = new Item(id, t, category, price, d);
            list.add(item);
        }
        rs.close();
        return list;
    }
    public List<Item> getListItemByCategory(String category){
        List<Item> list = new ArrayList<>();
        String whereClause = "category LIKE ?";
        String[] wherearg = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,wherearg,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String price = rs.getString(3);
            String d = rs.getString(4);
            Item item = new Item(id,title,c,price,d);
            list.add(item);
        }
        rs.close();
        return list;
    }

    public List<Item> getListItemByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] wherearg = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String d = rs.getString(4);
            Item item = new Item(id, title, category, price, d);
            list.add(item);
        }
        rs.close();
        return list;
    }
}
