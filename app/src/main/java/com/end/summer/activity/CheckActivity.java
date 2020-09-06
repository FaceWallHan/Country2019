package com.end.summer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.CheckAdapter;
import com.end.summer.bean.DataCheck;
import com.end.summer.net.VolleyLo;
import com.end.summer.net.VolleyTo;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends BaseActivity {
    private ProgressBar checkBar;
    private Button check;
    private ListView checkList;
    private List<DataCheck>list;
    private CheckAdapter adapter;
    private int index=0;
    private TextView tel;
    private VolleyTo volleyTo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);
        setText("用户签到");
        inView();
        setListener();
        addData();
        startProgressBarThread();
        setVolleyTo();
    }
    private void setVolleyTo(){
        volleyTo=new VolleyTo();
        volleyTo.setUrl("get_root").setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    tel.setText(jsonObject.getString(""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }).start();
    }
    private void startProgressBarThread(){
        new Thread(){
            @Override
            public void run() {
                while (index<checkBar.getMax()){
                    index++;
                    checkBar.setProgress(index);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void setListener(){
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckActivity.this,WebCheckActivity.class));
            }
        });
        adapter.setListener(new CheckAdapter.OnItemCompleteListener() {
            @Override
            public void onItemCompleteClick(int position) {
                if (position==0){
                    startActivity(new Intent(CheckActivity.this,WebCheckActivity.class));
                }
            }
        });
    }
    private void addData(){
        for (int i = 0; i < 6; i++) {
            if (i==0){
                list.add(new DataCheck("任务"+(i+1),"点击此处去完成"));
            }else {
                list.add(new DataCheck("任务"+(i+1),"去完成"));
            }
        }
        adapter.notifyDataSetChanged();
        checkList.setAdapter(adapter);
    }
    private void inView(){
        check=findViewById(R.id.check);
        checkBar=findViewById(R.id.checkBar);
        checkList=findViewById(R.id.checkList);
        list=new ArrayList<>();
        adapter=new CheckAdapter(CheckActivity.this,list);
        TextView user=findViewById(R.id.user);
        user.setText(AppClient.getUser());
        tel=findViewById(R.id.tel);
    }
}
