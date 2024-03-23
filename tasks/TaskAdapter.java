package com.example.todolist.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.todolist.R;

import java.util.List;
import java.util.prefs.Preferences;

//适配器,将listView和数据进行连接的中介
public class TaskAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<TaskItem> taskItemList;
    private List<TaskItem> backlist;//备份原始的数据
    //private MyFilter mfilter;

    public TaskAdapter(Context mContext,List<TaskItem>taskItemList){
        this.mContext=mContext;
        this.taskItemList=taskItemList;
        backlist=taskItemList;
    }

    @Override
    public int getCount(){
        return taskItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    //核心
    public View getView(int position, View convertView, ViewGroup parent) {
        //将布局文件转换为对应的视图对象
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.Base_Theme_ToDoList);
        View v = LayoutInflater.from(mContext).inflate(R.layout.taskitem_layout,null);
        TextView tv_content=(TextView)v.findViewById(R.id.tv_title);
        TextView tv_time=(TextView)v.findViewById(R.id.tv_time);

        //设置文本
        String allText = taskItemList.get(position).getTitle();
        tv_content.setText(allText);

        tv_time.setText(taskItemList.get(position).getTime());

        v.setTag(taskItemList.get(position).getTaskId());

        return v;
    }


    @Override
    public Filter getFilter() {
        return null;
    }
}
