package com.end.summer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.ImageAdapter;
import com.end.summer.bean.Dataitems;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity {
    private Button exit;
    private ListView exit_list;
    private List<Dataitems>list;
    private ImageAdapter adapter;
    private AppClient app;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        setText("设置");
        inView();
        addData();
        setListener();
    }
    private void addData(){
        list.add(new Dataitems(R.drawable.etc_info,"查询ETC账户信息"));
        list.add(new Dataitems(R.drawable.user_check_in,"用户签到"));
        list.add(new Dataitems(R.drawable.etc_log,"查询ETC通行日志"));
        list.add(new Dataitems(R.drawable.version,"当前版本号"));
        adapter.notifyDataSetInvalidated();
    }
    private void setListener(){
        exit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        startActivity(new Intent(SettingActivity.this,CheckActivity.class));
                        break;
                    case 0:
                        startActivity(new Intent(SettingActivity.this,EtcInfoActivity.class));
                        break;
                }
            }
        });
        if (app.isLogin()){
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app.setLogin(false);
                    app.setPassWord("");
                    app.setRemember(false);
                    AppClient.setUser("user1");
                    Toast.makeText(SettingActivity.this, "当前账户退出成功", Toast.LENGTH_SHORT).show();
                }
            });
            exit.setBackgroundResource(R.drawable.yuan_green);
        }else {
            exit.setBackgroundResource(R.drawable.yuan_hui);
        }

    }
    private void inView(){
        exit=findViewById(R.id.exit);
        exit_list=findViewById(R.id.exit_list);
        list=new ArrayList<>();
        app= (AppClient) getApplication();
        adapter=new ImageAdapter(SettingActivity.this,list,false);
        exit_list.setAdapter(adapter);
        exit.setEnabled(app.isLogin());
    }
}
