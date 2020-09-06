package com.end.summer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.ViolationAdapter;
import com.end.summer.bean.DataViolation;
import com.end.summer.bean.sql.SqlUser;
import com.end.summer.net.VolleyLo;
import com.end.summer.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViolationInfoActivity extends BaseActivity {
    private ListView violation;
    private ImageView head_violation;
    private TextView name_violation,sex_violation,tel_violation,empty;
    private VolleyTo volleyTo;
    private AppClient app;
    private List<DataViolation>list;
    private ViolationAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_layout);
        setText("违章详情");
        inView();
        addData();
        setVolleyTo();
        setListener();
    }
    private void addData(){
        SqlUser sqlUser= (SqlUser) getIntent().getSerializableExtra("sql_user");
        if (sqlUser.getSex().equals("男")){
            head_violation.setBackgroundResource(R.drawable.touxiang_2);
        }else if (sqlUser.getSex().equals("女")){
            head_violation.setBackgroundResource(R.drawable.touxiang_1);
        }
        name_violation.setText("姓名："+sqlUser.getName());
        sex_violation.setText("性别："+sqlUser.getSex());
        tel_violation.setText("手机号码：\n"+sqlUser.getTel());
        AppClient.setUser(sqlUser.getUserName());
    }
    private void setListener(){
        adapter.setOnViolationItemListener(new ViolationAdapter.OnViolationItemListener() {
            @Override
            public void onItemClick(int position) {
                DataViolation dataViolation=list.get(position);
                dataViolation.setProcess(true);
                Intent intent=new Intent(ViolationInfoActivity.this,PayActivity.class);
                intent.putExtra("money",dataViolation.getMoney());
                startActivity(intent);
            }
        });
    }
    private void setVolleyTo(){
        volleyTo=new VolleyTo();
        volleyTo.setUrl("get_all_car_peccancy").setDialog(ViolationInfoActivity.this).setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                        if (array.length()>0){
                            for (int i = 0; i < array.length(); i++) {
                                
                            }
                        }else {
                            empty.setText("该用户无车辆信息");
                            violation.setEmptyView(empty);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }).start();
    }
    private void inView(){
        violation=findViewById(R.id.violation);
        head_violation=findViewById(R.id.head_violation);
        sex_violation=findViewById(R.id.sex_violation);
        name_violation=findViewById(R.id.name_violation);
        tel_violation=findViewById(R.id.tel_violation);
        app= (AppClient) getApplication();
        list=new ArrayList<>();
        adapter=new ViolationAdapter(ViolationInfoActivity.this,list);
        violation.setAdapter(adapter);
        empty=findViewById(R.id.empty);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppClient.setUser(app.getReadUser());
    }
}
