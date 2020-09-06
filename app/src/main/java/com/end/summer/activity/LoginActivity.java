package com.end.summer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.bean.sql.SqlCheck;
import com.end.summer.bean.sql.SqlUser;
import com.end.summer.net.VolleyLo;
import com.end.summer.net.VolleyTo;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private AppClient app;
    private EditText username,password;
    private Button login;
    private TextView re_name,re_word,ip_address;
    private CheckBox remember;
    private VolleyTo volleyTo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setText("登录");
        inView();
        inListener();
    }
    private void insertSql(List<SqlUser>list,String sex,String name,String username,String tel){
        SqlUser sqlUser=new SqlUser();
        sqlUser.setCollection(false);
        sqlUser.setId(list.size()+1);
        sqlUser.setSex(sex);
        sqlUser.setName(name);
        sqlUser.setUserName(username);
        sqlUser.setTel(tel);
        sqlUser.save();
    }
    private void setVolleyTo(String word){
        volleyTo=new VolleyTo();
        volleyTo.setUrl("get_root").setJsonObject("PassWord",word).setDialog(LoginActivity.this).setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        app.setLogin(true);
                        app.setName(jsonObject.getString("name"));
                        app.setReadUser(AppClient.getUser());
                        List<SqlUser>list= LitePal.findAll(SqlUser.class);
                        if (list.size()>0){
                            for (int i = 0; i < list.size(); i++) {
                                SqlUser sqlUser=list.get(i);
                                if (!sqlUser.getUserName().equals(jsonObject.getString("username"))){
                                    insertSql(list,jsonObject.getString("sex"),jsonObject.getString("name"),jsonObject.getString("username"),jsonObject.getString("tel"));
                                    break;
                                }
                            }
                        }else {
                            insertSql(list,jsonObject.getString("sex"),jsonObject.getString("name"),jsonObject.getString("username"),jsonObject.getString("tel"));
                        }
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "登录失败try", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "登录失败catch", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this, "登录失败onErrorResponse", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }
    private void inListener(){
        ip_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,IpSettingActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String word=password.getText().toString().trim();
                if (name.equals("")){
                    re_name.setText("用户名不能为空");
                }else {
                    re_name.setText("");
                   AppClient.setUser(name);
                }
                if (word.equals("")){
                    re_word.setText("密码不能为空");
                }else {
                    re_word.setText("");
                }
                if (!name.equals("")&&!word.equals("")){
                    setVolleyTo(word);
                    if (remember.isChecked()){
                        app.setRemember(true);
                        app.setPassWord(word);
                    }else {
                        app.setRemember(false);
                        app.setPassWord("");
                    }
                }
            }
        });
    }
    private void inView(){
        app= (AppClient) getApplication();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        re_name=findViewById(R.id.re_name);
        re_word=findViewById(R.id.re_word);
        ip_address=findViewById(R.id.ip_address);
        remember=findViewById(R.id.remember);
        username.setText(AppClient.getUser());
        remember.setChecked(app.isRemember());
        if (app.isRemember()){
            password.setText(app.getPassWord());
        }
    }
}
