package com.example.todolist;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.todolist.fragment.ConcentrationFragment;
import com.example.todolist.fragment.TaskListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Fragment> list;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        list=new ArrayList<>();
        list.add(new TaskListFragment());
        list.add(new ConcentrationFragment());
        showFragment(list.get(0));
        //底部导航栏的切换事件
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int ID=menuItem.getItemId();
                if(ID==R.id.menu_task){
                    showFragment(list.get(0));
                }
                if(ID==R.id.menu_concentration){
                    showFragment(list.get(1));
                }
                return true;
            }
        });
    }
    private void showFragment(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
    }
}

