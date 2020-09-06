package com.end.summer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.end.summer.R;
import com.end.summer.adapter.UserAdapter;
import com.end.summer.bean.sql.SqlUser;

import org.litepal.LitePal;
import java.util.List;

public class UserCenterActivity extends BaseActivity {
    private ListView userInfo;
    private UserAdapter adapter;
    private List<SqlUser>list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_center_layout);
        setText("用户中心");
        inView();
        addData();
        setListener();
    }
    private void addData(){
        list=LitePal.findAll(SqlUser.class);
        adapter=new UserAdapter(UserCenterActivity.this, list);
        userInfo.setAdapter(adapter);
    }
    private void setListener(){
        adapter.setOnChangeSqlListener(new UserAdapter.OnChangeSqlListener() {
            @Override
            public void changeCollection(int position) {
                SqlUser sqlUser=list.get(position);
                if (!sqlUser.isCollection()){
                    sqlUser.setCollection(true);
                    sqlUser.save();
                }
            }

            @Override
            public void changeLook(int position) {
                startActivity(new Intent(UserCenterActivity.this,CollectionActivity.class));/**收藏界面*/
            }

            @Override
            public void imageClick(int position) {
                Intent intent=new Intent(UserCenterActivity.this,ViolationInfoActivity.class);
                intent.putExtra("sql_user",list.get(position));
                startActivity(intent);
            }
        });
    }
    private void inView(){
        userInfo=findViewById(R.id.userInfo);
    }
}
