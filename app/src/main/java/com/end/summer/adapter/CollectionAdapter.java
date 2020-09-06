package com.end.summer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.end.summer.R;
import com.end.summer.bean.sql.SqlUser;
import com.end.summer.tools.SideLayout;

import java.util.List;

public class CollectionAdapter extends ArrayAdapter<SqlUser> {
    private List<SqlUser>list;
    public CollectionAdapter(@NonNull Context context, List<SqlUser>list) {
        super(context, 0);
        this.list=list;
    }
    private OnCollectionListener onCollectionListener;

    public void setOnCollectionListener(OnCollectionListener onCollectionListener) {
        this.onCollectionListener = onCollectionListener;
    }

    public interface OnCollectionListener{
        void collectionClick(int position,SqlUser sqlUser);
        void topClick(SqlUser sqlUser);
        void cancelTop(int position,SqlUser sqlUser);
    }
    @Nullable
    @Override
    public SqlUser getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        final SqlUser sqlUser=list.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate( R.layout.collection_item_layout,parent,false);
            holder=new ViewHolder();
            holder.collection_cancel=convertView.findViewById(R.id.collection_cancel);
            holder.sideLayout_cancel=convertView.findViewById(R.id.sideLayout_cancel);
            holder.sex_cancel=convertView.findViewById(R.id.sex_cancel);
            holder.username_item_cancel=convertView.findViewById(R.id.username_item_cancel);
            holder.name_item_cancel=convertView.findViewById(R.id.name_item_cancel);
            holder.tel_item_cancel=convertView.findViewById(R.id.tel_item_cancel);
            holder.top_cancel=convertView.findViewById(R.id.top_cancel);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (sqlUser.getSex().equals("男")){
            holder.sex_cancel.setBackgroundResource(R.drawable.touxiang_2);
        }else {
            holder.sex_cancel.setBackgroundResource(R.drawable.touxiang_1);
        }
        if (sqlUser.isTop()){
            holder.top_cancel.setText("取消置顶");
            holder.sideLayout_cancel.setBackgroundResource(R.drawable.back_hui);
        }else {
            holder.top_cancel.setText("置顶");
            holder.sideLayout_cancel.setBackgroundResource(R.drawable.back_white);
        }
        holder.username_item_cancel.setText(sqlUser.getUserName());
        holder.name_item_cancel.setText(sqlUser.getName());
        holder.tel_item_cancel.setText(sqlUser.getTel());
        holder.collection_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectionListener.collectionClick(position,sqlUser);
            }
        });
        holder.top_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.top_cancel.getText().toString().equals("置顶")){
                    onCollectionListener.topClick(sqlUser);
                }else if (holder.top_cancel.getText().toString().equals("取消置顶")){
                    onCollectionListener.cancelTop(position,sqlUser);
                }

            }
        });
        holder.sideLayout_cancel.setOnSlideChangeListenr(new SideLayout.OnSlideChangeListener() {
            @Override
            public void onMenuOpen(SideLayout slideLayout) {
                holder.sideLayout_cancel=slideLayout;
            }

            @Override
            public void onMenuClose(SideLayout slideLayout) {
//                if (holder.sideLayout != null) {
//                    holder.sideLayout = null;
//                }

            }

            @Override
            public void onClick(SideLayout slideLayout) {
                if (holder.sideLayout_cancel != null ) {
                    holder.sideLayout_cancel.closeMenu();
                }
            }
        });
        return convertView;
    }
    private class  ViewHolder{
        SideLayout sideLayout_cancel;
        ImageView sex_cancel;
        TextView username_item_cancel,name_item_cancel,tel_item_cancel,collection_cancel,top_cancel;
    }
}
