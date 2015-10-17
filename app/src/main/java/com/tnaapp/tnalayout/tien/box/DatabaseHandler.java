package com.tnaapp.tnalayout.tien.box;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tnaapp.tnalayout.ai.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XTien on 10/17/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "historyManager";

    // Contacts table name
    private static final String HISTORY = "tna_history";

    // Contacts Table Columns names
    private static final String KEY_TITLE = "title";
    private static final String KEY_DES = "description";
    private static final String KEY_SOURCE = "source";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + HISTORY + "("
                + KEY_TITLE + " TEXT PRIMARY KEY," + KEY_DES + " TEXT,"
                + KEY_SOURCE + " TEXT" + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY);
        onCreate(db);
    }

    public void addHistory(Video video) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, video.getTitle());
        values.put(KEY_DES, video.getDescription());
        values.put(KEY_SOURCE, video.getSource());
        db.insert(HISTORY, null, values);
        db.close();
    }
    public List<Video> getAllHistory() {
        List<Video> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Video video = new Video();
                video.setTitle(cursor.getString(0));
                video.setDescription(cursor.getString(1));
                video.setSource(cursor.getString(2));
                list.add(video);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
