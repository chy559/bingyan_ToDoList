package com.example.todolist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.widget.Toolbar;

import com.example.todolist.CRUD;
import com.example.todolist.MainActivity;
import com.example.todolist.MySQLiteOpenHelper;
import com.example.todolist.R;
import com.example.todolist.insertActivity;
import com.example.todolist.tasks.TaskAdapter;
import com.example.todolist.tasks.TaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TaskListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private String TAG = "diaoshi";
    private ListView lv;
    private TaskAdapter adapter;
    private SimpleAdapter simpleAdapter;
    private List<TaskItem> taskItemList = new ArrayList<>();
    private Toolbar toolbar;
    private Button addBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tasklist,container,false);
        //Fragment中的简单listview的调试
//        lv=view.findViewById(R.id.lv);
//        List<Map<String,String>> names = new ArrayList<>();
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("name","强哥");
//        names.add(map);
//        map = new HashMap<String,String>();
//        map.put("name","信春哥");
//        names.add(map);
//        map = new HashMap<String,String>();
//        map.put("name","大禹");
//        names.add(map);
//        simpleAdapter=new SimpleAdapter(getActivity(),names, android.R.layout.simple_list_item_1,new String[]{"name"},new int[]{android.R.id.text1});
//        lv.setAdapter(simpleAdapter);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_2);

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                //处理菜单的点击事件
//                int getId=item.getItemId();
//                return false;
//            }
//        });

        addBtn = view.findViewById(R.id.add_icon);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(view);
            }
        });
        lv = view.findViewById(R.id.lv);
        adapter = new TaskAdapter(getActivity().getApplicationContext(),taskItemList);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);
        refreshListView();//刷新页面
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    //刷新页面
    public void refreshListView() {
        CRUD op = new CRUD(getActivity().getApplicationContext());
        op.open();
        //set adapter
        if(taskItemList.size()>0)taskItemList.clear();
        taskItemList.addAll(op.getAllTasks());
        op.close();
        adapter.notifyDataSetChanged();
    }

    public void addData(View view) {
        //意图,目的
        Intent intent = new Intent(getActivity(), insertActivity.class);
        intent.putExtra("mode",4);//新建笔记设置为4
        startActivityForResult(intent,0);
    }
    //接受insertActivity的信号
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        int returnMode;
        long TaskId;
        returnMode = data.getExtras().getInt("mode",-1);
        TaskId = data.getExtras().getLong("taskid",0);

        if(returnMode == 1) {//update current task

            String title = data.getStringExtra("title");
            String time = data.getStringExtra("time");
            int tag = data.getExtras().getInt("tag",1);
            TaskItem newTaskItem = new TaskItem(title,time,tag);
            newTaskItem.setTaskId(TaskId);
            CRUD op = new CRUD(getActivity().getApplicationContext());
            op.open();
            op.updateTask(newTaskItem);
            op.close();
        }else if(returnMode == 0){//create new
            String title = data.getStringExtra("title");
            String time = data.getStringExtra("time");
            int tag = data.getExtras().getInt("tag",1);
            TaskItem newTaskItem = new TaskItem(title,time,tag);
            CRUD op = new CRUD(getActivity().getApplicationContext());
            op.open();
            op.addTask(newTaskItem);
            op.close();
        }else if(returnMode == 2){
            TaskItem curTask = new TaskItem();
            curTask.setTaskId(TaskId);
            CRUD op = new CRUD(getActivity().getApplicationContext());
            op.open();
            op.removeTask(curTask);
            op.close();
        }
        else{

        }
        refreshListView();
        super.onActivityResult(requestCode,resultCode,data);
    }
    //设计点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int ParentID=parent.getId();
        if(ParentID == R.id.lv) {
            TaskItem curTask = (TaskItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), insertActivity.class);

            intent.putExtra("title", curTask.getTitle());
            intent.putExtra("taskid",curTask.getTaskId());
            intent.putExtra("time", curTask.getTime());
            intent.putExtra("mode", 3);//mode为了区分是否是已经存在的页面，修改设置为3
            intent.putExtra("tag", curTask.getTag());

            startActivityForResult(intent, 1);//collect Data from editText
            Log.d(TAG, "onItemClick:" + position);
        }
    }
}
