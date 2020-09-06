package com.end.summer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class UserAdapter extends ArrayAdapter<SqlUser> {
    private List<SqlUser>list;
    public UserAdapter( @NonNull Context context,List<SqlUser>list) {
        super(context, 0);
        this.list=list;
    }
    private OnChangeSqlListener onChangeSqlListener;

    public void setOnChangeSqlListener(OnChangeSqlListener onChangeSqlListener) {
        this.onChangeSqlListener = onChangeSqlListener;
    }

    public interface OnChangeSqlListener{
        void changeCollection(int position);
        void changeLook(int position);
        void imageClick(int position);
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
        SqlUser sqlUser=list.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate( R.layout.user_center_item_layout,parent,false);
            holder=new ViewHolder();
            holder.collection=convertView.findViewById(R.id.collection);
            holder.sideLayout=convertView.findViewById(R.id.sideLayout);
            holder.sex=convertView.findViewById(R.id.sex);
            holder.username_item=convertView.findViewById(R.id.username_item);
            holder.name_item=convertView.findViewById(R.id.name_item);
            holder.tel_item=convertView.findViewById(R.id.tel_item);
            holder.root=convertView.findViewById(R.id.root);
            holder.look=convertView.findViewById(R.id.look);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (sqlUser.getSex().equals("男")){
            holder.sex.setBackgroundResource(R.drawable.touxiang_2);
        }else {
            holder.sex.setBackgroundResource(R.drawable.touxiang_1);
        }
        Log.i("cccccccccccccccccc", "getView: "+sqlUser.getUserName());
        holder.username_item.setText(sqlUser.getUserName());
        holder.name_item.setText(sqlUser.getName());
        holder.tel_item.setText(sqlUser.getTel());
        holder.root.setText("一般管理员");
        holder.sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeSqlListener.imageClick(position);
            }
        });
        holder.look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeSqlListener.changeLook(position);
            }
        });
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeSqlListener.changeCollection(position);
            }
        });
        holder.sideLayout.setOnSlideChangeListenr(new SideLayout.OnSlideChangeListener() {
            @Override
            public void onMenuOpen(SideLayout slideLayout) {
                holder.sideLayout=slideLayout;
            }

            @Override
            public void onMenuClose(SideLayout slideLayout) {
//                if (holder.sideLayout != null) {
//                    holder.sideLayout = null;
//                }

            }

            @Override
            public void onClick(SideLayout slideLayout) {
                if (holder.sideLayout != null ) {
                    holder.sideLayout.closeMenu();
                }
            }
        });
        return convertView;
    }
    private class  ViewHolder{
        SideLayout sideLayout;
        ImageView sex;
        TextView username_item,name_item,tel_item,root,look,collection;
    }
}
