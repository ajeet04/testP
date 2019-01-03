package com.dao.ajeet.sqlitetest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import static android.provider.BaseColumns._ID;

import static android.provider.BaseColumns._ID;

/**
 * Created by delaroy on 9/8/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "image.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    public final static String COLUMN_NAME = "imagename";
    public final static String COLUMN_NAME_1= "name";

    public final static String TABLE_NAME = "imagetable";




    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " BLOB NOT NULL, "+
                COLUMN_NAME_1+ " STRING  "  + " );";

        db.execSQL(SQL_CREATE_IMAGE_TABLE);

        Log.d(TAG, "Database Created Successfully" );

    }

    public void addToDb(byte[] image,String name){
        ContentValues cv = new  ContentValues();
        cv.put(COLUMN_NAME,   image);
        cv.put(COLUMN_NAME_1,   name);
        db.insert( TABLE_NAME, null, cv );
        Log.d(TAG, "Database added Successfully" );
      //  Toast.makeText(context, "added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}