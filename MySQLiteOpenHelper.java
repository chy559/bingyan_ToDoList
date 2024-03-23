package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="mySQLite.db";//数据库名
    public static final String TABLE_NAME_TASK="task";//数据库表名
    public static final String CREATE_TABLE_SQL= "create table "+
            TABLE_NAME_TASK+
            "(taskid integer primary key autoincrement, title text, time text, mode integer default 1);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }
    public MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
