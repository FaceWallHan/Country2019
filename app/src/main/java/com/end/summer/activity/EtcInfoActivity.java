package com.end.summer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.HistoryAdapter;
import com.end.summer.bean.sql.SqlHistory;
import com.end.summer.net.VolleyLo;
import com.end.summer.net.VolleyTo;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EtcInfoActivity extends BaseActivity {
    private Button query,queryInfo,recharge;
    private ListView history;
    private RadioGroup group;
    private Spinner carId,carIdInfo,money;
    private TextView balance,queryResult,rechargeResult;
    private VolleyTo queryVolleyTo;
    private List<SqlHistory>list;
    private HistoryAdapter adapter;
    private boolean isShow=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc_info_layout);
        setText("ETC账户");
        inView();
        setListener();
    }
    private void setQueryVolleyTo(){
        queryVolleyTo=new VolleyTo();
        queryVolleyTo.setUrl("get_balance_b").setJsonObject("number",carIdInfo.getSelectedItem().toString()).setDialog(EtcInfoActivity.this).setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        balance.setText(jsonObject.getString("balance")+"元");
                        queryResult.setText("查询完成");
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
    private void addData(int reCarId,String reMoney){
        List<SqlHistory>list=LitePal.findAll(SqlHistory.class);
        SimpleDateFormat format=new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINESE);
        Date date=new Date(System.currentTimeMillis());
        SqlHistory history=new SqlHistory();
        history.setUser(AppClient.getUser());
        history.setTime(format.format(date));
        history.setMoney(reMoney);
        history.setId(list.size());
        history.setCarId(reCarId);
        history.save();
        if (isShow){
            changeData();
        }
    }
    private void changeData(){
        list.clear();
        List<SqlHistory> sqlHistories= LitePal.findAll(SqlHistory.class);
        if (sqlHistories.size()>0){
            int id=Integer.parseInt(carId.getSelectedItem().toString());
            RadioButton button=findViewById(group.getCheckedRadioButtonId());
            String time=button.getText().toString();
            for (int i = 0; i < sqlHistories.size(); i++) {
                SqlHistory sqlHistory=sqlHistories.get(i);
                if (sqlHistory.getCarId()==id){
                    sqlHistory.setId(i+1);
                    list.add(sqlHistory);
                }
            }
            switch (time){
                case "时间降序":
                    Collections.sort(list,new SqlHistory.timeDesc());
                    break;
                case "时间升序":
                    Collections.sort(list,new SqlHistory.timeAsc());
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void setListener(){
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData();
                isShow=true;
            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**充值金额*/
                final int carStr=Integer.parseInt(carIdInfo.getSelectedItem().toString());
                final String moneyStr=money.getSelectedItem().toString();
                VolleyTo volleyTo=new VolleyTo();
                volleyTo.setUrl("set_balance").setJsonObject("plate","鲁A1000"+carStr).setJsonObject("balance",moneyStr).setDialog(EtcInfoActivity.this).setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                rechargeResult.setText("充值成功");
                                setQueryVolleyTo();
                                addData(carStr,moneyStr);

                            }else {
                                rechargeResult.setText("充值失败");
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
        });
        queryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**查询余额*/
                setQueryVolleyTo();
            }
        });
    }
    private void inView(){
        query=findViewById(R.id.query);
        history=findViewById(R.id.history);
        group=findViewById(R.id.group);
        carId=findViewById(R.id.carId);
        queryInfo=findViewById(R.id.queryInfo);
        recharge=findViewById(R.id.recharge);
        carIdInfo=findViewById(R.id.carIdInfo);
        money=findViewById(R.id.money);
        balance=findViewById(R.id.balance);
        queryResult=findViewById(R.id.queryResult);
        rechargeResult=findViewById(R.id.rechargeResult);
        list=new ArrayList<>();
        adapter=new HistoryAdapter(EtcInfoActivity.this,list);
        history.setAdapter(adapter);
    }
}
