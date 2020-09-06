package com.end.summer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.ImageAdapter;
import com.end.summer.adapter.ViewPagerAdapter;
import com.end.summer.bean.Dataitems;
import com.end.summer.fragment.Fragment_Head;
import com.end.summer.fragment.Fragment_New;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private ListView left_items;
    private List<Dataitems>list;
    private ImageAdapter adapter;
    private ImageView change,head;
    private AppClient app;
    private TextView name;
    private List<Fragment>fragmentList;
    private BottomNavigationBar bottom_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        inView();
        addData();
        inListener();
    }

    private void setName(){
        if (!app.isLogin()){
            name.setText("联想智能交通系统\n点击头像登录");
        }else {
            name.setText("联想智能交通系统\n你好，"+app.getName());
        }
    }
    private void addData(){
        setName();
        list.add(new Dataitems(R.drawable.btn_l_skype,"天气预报"));
        list.add(new Dataitems(R.drawable.btn_l_star,"交通资讯"));
        list.add(new Dataitems(R.drawable.btn_l_speech_3,"个人"));
        list.add(new Dataitems(R.drawable.setting,"设置"));
        fragmentList.add(new Fragment_Head());
        fragmentList.add(new Fragment_New());
        replace(fragmentList.get(0));
        adapter.notifyDataSetInvalidated();
        bottom_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        /***/
        bottom_bar.addItem(new BottomNavigationItem(R.drawable.main,"主页面").setActiveColor(R.color.colorPrimary))
                  .addItem(new BottomNavigationItem(R.drawable.creative,"创意设计").setActiveColor(R.color.colorPrimary))
                    .setFirstSelectedPosition(0)
                    .initialise();

    }
    private void inListener(){
        bottom_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                /***/
                replace(fragmentList.get(position));
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        left_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,WarnWeatherActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,UserCenterActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                }
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!app.isLogin()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
    }
    private void inView(){
        drawer=findViewById(R.id.draw);
        left_items=findViewById(R.id.left_items);
        change=findViewById(R.id.change);
        head=findViewById(R.id.head);
        name=findViewById(R.id.name);
        bottom_bar=findViewById(R.id.bottom_bar);
        /***/
        list=new ArrayList<>();
        adapter=new ImageAdapter(MainActivity.this,list,true);
        left_items.setAdapter(adapter);
        app= (AppClient) getApplication();
        fragmentList=new ArrayList<>();
    }
    private void replace(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setName();
    }
}
