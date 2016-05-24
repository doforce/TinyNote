package com.example.vampire.tinynote.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vampire on 2016/4/9 0009.
 * 存储记事本的数据的数据库类
 */
public class NoteDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME="notes";
    public static final String CONTENT="add_content";
    public static final String TITLE="title";
    public static final String TIME="time";
    public static final String ID="_id";
    public static final String CURRENT_TIME ="current_time";//标记时间差，用来排序。因为数据库排序不了。

    public NoteDB(Context context) {
        super(context, NoteDB.TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table "+ TABLE_NAME+"("+
                    ID+" integer primary key autoincrement,"+
                    CONTENT+" text,"+
                    TITLE+" text,"+
                    TIME+" text,"+
                    CURRENT_TIME +" INTEGER"
            );
        }catch (SQLiteException e){
            e.getStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("drop table if exists "+TABLE_NAME);
           onCreate(db);
    }
}
