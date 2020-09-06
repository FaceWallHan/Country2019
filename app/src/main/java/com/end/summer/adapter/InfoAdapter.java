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
import com.end.summer.bean.DataInfo;

import java.util.List;

public class InfoAdapter extends ArrayAdapter<DataInfo> {
    private List<DataInfo>list;
    public InfoAdapter( @NonNull Context context, List<DataInfo>list) {
        super(context, 0);
        this.list=list;
    }
    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        ViewHolder holder;
        DataInfo info=list.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate( R.layout.info_item_layout,parent,false);
            holder=new ViewHolder();
            holder.info_image=convertView.findViewById(R.id.info_image);
            holder.info_name=convertView.findViewById(R.id.info_name);
            holder.info_number=convertView.findViewById(R.id.info_number);
            holder.info_type=convertView.findViewById(R.id.info_type);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.info_image.setBackgroundResource(info.getImageResource());
        holder.info_name.setText(info.getName());
        holder.info_type.setText(info.getType());
        holder.info_type.setTextColor(info.getColor());
        if (position==5){
            holder.info_number.setVisibility(View.GONE);
        }else {
            holder.info_number.setText(info.getIndexNumber()+"");
        }
        return convertView;
    }
    private class ViewHolder{
        TextView info_name,info_number,info_type;
        ImageView info_image;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Nullable
    @Override
    public DataInfo getItem(int position) {
        return list.get(position);
    }
}
