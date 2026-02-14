package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbClass extends SQLiteOpenHelper {

    public static final String DbName = "notes_db";
    public static final int DbVersion = 1;
    public static final String TableName = "notes";
    public static final String tableId = "id";
    public static final String tableTitle = "noteTitle";
    public static final String tableDesc = "noteDesc";
    public static DbClass instance;

    // Prevent leaks and crashes
    public static synchronized DbClass getInstance(Context context) {
        if(instance == null){
            instance = new DbClass(context.getApplicationContext());
        }
        return instance;
    }
    private DbClass(Context context) {
        super(context, DbName, null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE "+TableName+" ("+
                tableId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                tableTitle+" TEXT, "+
                tableDesc+" TEXT"+")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
    public boolean insertData(String title, String Desc){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(tableTitle, title);
        cv.put(tableDesc, Desc);
        long insert = db.insert(TableName, null, cv);
        return insert != -1;
    }
    public boolean updateData(int id, String title, String desc){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(tableTitle, title);
        cv.put(tableDesc, desc);
        long update = db.update(TableName, cv, tableId+"=?", new String[]{String.valueOf(id)});
        return update > 0;
    }
    public boolean deleteData(int id){
        SQLiteDatabase db = getWritableDatabase();
        long delete = db.delete(TableName, tableId+"=?", new String[]{String.valueOf(id)});
        return delete > 0;
    }
    public ArrayList<ModelClass> getAllData(){
        ArrayList<ModelClass> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TableName, null);

        if(cursor.moveToFirst()){
            do{
                ModelClass model = new ModelClass(
                        cursor.getInt(cursor.getColumnIndexOrThrow(tableId)),
                        cursor.getString(cursor.getColumnIndexOrThrow(tableTitle)),
                        cursor.getString(cursor.getColumnIndexOrThrow(tableDesc))
                );
                list.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public ArrayList<ModelClass> searchData(String query){
        ArrayList<ModelClass> searchList = new ArrayList<>();
        if (query.trim().isEmpty()) {
            return getAllData(); // Return all if query empty
        }
        SQLiteDatabase searchDb = getReadableDatabase();
        // Search for matches in title or description
        String sql = "SELECT * FROM "+TableName+
                " WHERE "+tableTitle+" LIKE ? OR " +tableDesc+" LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%"};
        Cursor cursor = searchDb.rawQuery(sql, selectionArgs);
        if(cursor.moveToFirst()){
            do{
                ModelClass searchModel = new ModelClass(
                        cursor.getInt(cursor.getColumnIndexOrThrow(tableId)),
                        cursor.getString(cursor.getColumnIndexOrThrow(tableTitle)),
                        cursor.getString(cursor.getColumnIndexOrThrow(tableDesc))
                );
                searchList.add(searchModel);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return searchList;
    }
}
