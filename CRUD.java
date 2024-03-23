package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.tasks.TaskItem;

import java.util.ArrayList;
import java.util.List;

//增删改用的
public class CRUD {
    private static final String[] columns={
            "taskid",
            "title",
            "time",
            "mode"
    };
    SQLiteDatabase db;
    MySQLiteOpenHelper dbHander;
    //构造方法
    public CRUD(Context context){
        dbHander=new MySQLiteOpenHelper(context);//实例化
    }
    public void open(){
        db=dbHander.getWritableDatabase();//使数据库进入可写的状态
    }
    public void close(){
        dbHander.close();
    }
    //增
    public TaskItem addTask(TaskItem taskItem){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",taskItem.getTitle());
        contentValues.put("time",taskItem.getTime());
        contentValues.put("mode",taskItem.getTag());

        long insertId = db.insert("task",null,contentValues);
        taskItem.setTaskId(insertId);//为每个任务设置taskId
        return taskItem;
    }
    //查
    public TaskItem getTask(long taskid){
        //指针，数据库寻找
        Cursor cursor = db.query("task",columns,"taskid= ?",
                new String[]{String.valueOf(taskid)},null,null,null);

        if(cursor!=null) cursor.moveToFirst();
        TaskItem e=new TaskItem(cursor.getString(1),cursor.getString(2),cursor.getInt(3));
        return e;
    }
    //得到所有的任务
    public List<TaskItem> getAllTasks(){
        Cursor cursor=db.query("task", columns,null,null,null,null,null);//访问整个table
        List<TaskItem> taskItems=new ArrayList<>();

        if(cursor.getCount()>0){

            while(cursor.moveToNext()){
                TaskItem taskItem = new TaskItem();
                int columnIndex1 = cursor.getColumnIndex("taskid");
                if (columnIndex1 >= 0) {
                    taskItem.setTaskId(cursor.getLong(columnIndex1));
                }
                int columnIndex2 = cursor.getColumnIndex("title");
                if(columnIndex2 >=0){
                    taskItem.setTitle(cursor.getString(columnIndex2));
                }
                int columnIndex3 = cursor.getColumnIndex("time");
                if(columnIndex3 >=0){
                    taskItem.setTime(cursor.getString(columnIndex3));
                }
                int columnIndex4 = cursor.getColumnIndex("mode");
                if(columnIndex4 >=0){
                    taskItem.setTag(cursor.getInt(columnIndex4));
                }
                taskItems.add(taskItem);
            }
        }

        return taskItems;
    }
    //改
    public int updateTask(TaskItem taskItem){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",taskItem.getTitle());
        contentValues.put("time",taskItem.getTime());
        contentValues.put("mode",taskItem.getTag());

        return db.update(MySQLiteOpenHelper.TABLE_NAME_TASK,contentValues,
                "taskid=?",new String[]{String.valueOf(taskItem.getTaskId())});
    }
    //删
    public void removeTask(TaskItem taskItem){
        db.delete(MySQLiteOpenHelper.TABLE_NAME_TASK,"taskid="+taskItem.getTaskId(),null);
    }
}
