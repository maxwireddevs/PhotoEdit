package com.matchadevs.styleworks.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class itemHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "attendancelist_db";

    public itemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(item.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + item.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertItem(String name,Bitmap image) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(item.COLUMN_ITEM_NAME, name);
        byte[] data = getBitmapAsByteArray(image); // this is a function
        values.put(item.COLUMN_ITEM_IMAGEBLOB, data);

        db.insert(item.TABLE_NAME, null, values);

        // close db connection
        db.close();

    }

    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + item.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(item.TABLE_NAME, item.COLUMN_ITEM_NAME + " = ?",
                new String[]{String.valueOf(name)});
        db.close();
    }

    public void deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ item.TABLE_NAME);
        db.close();
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+item.TABLE_NAME,null);
        return res;
    }

    public Bitmap getImage(String name) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(item.TABLE_NAME,
                new String[]{item.COLUMN_ITEM_NAME,item.COLUMN_ITEM_IMAGEBLOB},
                item.COLUMN_ITEM_NAME + "=?",
                new String[]{name}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            byte[] imgByte = cursor.getBlob(1);
            // close the db connection
            cursor.close();
            db.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        else{
            return null;
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
