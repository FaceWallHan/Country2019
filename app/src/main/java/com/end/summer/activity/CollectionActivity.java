package com.end.summer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.end.summer.R;
import com.end.summer.adapter.CollectionAdapter;
import com.end.summer.bean.sql.SqlUser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private ListView collectionList;
    private ArrayList<SqlUser>list;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);
        setText("用户收藏");
        inView();
        addData();
        setListener();
    }
    private void setListener(){
        adapter.setOnCollectionListener(new CollectionAdapter.OnCollectionListener() {
            @Override
            public void collectionClick(int position, SqlUser sqlUser) {
                sqlUser.setCollection(false);
                sqlUser.save();
                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void topClick(SqlUser sqlUser) {
                for (int i = 0; i < list.size(); i++) {
                    SqlUser user=list.get(i);
                    if (user.getId()!=sqlUser.getId()){
                        user.setTop(false);
                        list.set(i,user);
                    }
                }
                sqlUser.setTop(true);
                list.remove(sqlUser);
                list.add(0,sqlUser);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void cancelTop(int position, SqlUser sqlUser) {
                addData();
            }
        });
    }
    private void addData(){
        list.clear();
        List<SqlUser>sqlUsers= LitePal.findAll(SqlUser.class);
        for (int i = 0; i < sqlUsers.size(); i++) {
            SqlUser user=sqlUsers.get(i);
            if (user.isCollection()){
                user.setIndex(i);
                list.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }
    private void inView(){
        collectionList=findViewById(R.id.collectionList);
        list=new ArrayList<>();
        adapter=new CollectionAdapter(CollectionActivity.this,list);
        collectionList.setAdapter(adapter);
    }
}
