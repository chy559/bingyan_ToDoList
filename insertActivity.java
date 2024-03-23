package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class insertActivity extends AppCompatActivity {

    private EditText et;
    private Button deleteButton;
    private boolean tagChange = false;
    private String old_title;
    private String old_time;
    private int old_Tag=1;
    private long TaskId = 0;
    private int openMode=0;
    private int tag = 1;
    public Intent intent = new Intent();//发送消息

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteButton = findViewById(R.id.delete_button);
        setContentView(R.layout.activity_insert);
        et=findViewById(R.id.et_task);
        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode",0);//默认值为0
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(insertActivity.this)
//                        .setMessage("删除吗")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(openMode == 4){
//                                    intent.putExtra("mode",-1);
//                                    setResult(RESULT_OK,intent);
//                                }
//                                else{
//                                    intent.putExtra("mode",2);
//                                    intent.putExtra("taskid",TaskId);
//                                    setResult(RESULT_OK,intent);
//                                }
//                                finish();
//                            }
//                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).create().show();
//            }
//        });



        if(openMode == 3){//打开已经存在的taskItem
            TaskId = getIntent.getLongExtra("taskid",0);//存贮id,定位该修改的task
            old_title = getIntent.getStringExtra("title");
            old_time = getIntent.getStringExtra("time");
            old_Tag = getIntent.getIntExtra("tag",1);
            et.setText(old_title);
            et.setSelection(old_title.length());//光标的位置
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_BACK){
            autoSetMessage();
            setResult(Activity.RESULT_OK,intent);//设置result
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    private void autoSetMessage() {
        if(openMode == 4){//新建笔记
            if(et.getText().toString().length()==0){
                intent.putExtra("mode",-1);
            }
            else{
                intent.putExtra("mode",0);//new one
                intent.putExtra("title",et.getText().toString());
                intent.putExtra("time",dateToStr());
                intent.putExtra("tag",tag);
            }
        }
        else{//已经存在的笔记
            if(et.getText().toString().equals(old_title) && !tagChange){
                intent.putExtra("mode",-1);//没有修改
            }
            else{
                intent.putExtra("title",et.getText().toString());
                intent.putExtra("time",dateToStr());
                intent.putExtra("mode",1);//edited
                intent.putExtra("taskid",TaskId);
                intent.putExtra("tag",tag);
            }
        }
    }

    private String dateToStr() {
        //将时间格式化为想要的形式
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public void delete(View view) {
        if (openMode == 4) {
            Toast.makeText(insertActivity.this, "新建中，无法删除", Toast.LENGTH_LONG).show();
        } else {
            new AlertDialog.Builder(insertActivity.this)
                    .setMessage("删除吗")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (openMode == 4) {
                                intent.putExtra("mode", -1);
                                setResult(RESULT_OK, intent);
                            } else {
                                intent.putExtra("mode", 2);
                                intent.putExtra("taskid", TaskId);
                                setResult(RESULT_OK, intent);
                            }
                            finish();
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    }
}