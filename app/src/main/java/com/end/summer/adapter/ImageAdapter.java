package com.end.summer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.end.summer.R;
import com.end.summer.bean.Dataitems;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Dataitems> {
    private List<Dataitems> list;
    private LayoutInflater inflater;
    private boolean isCenter;

    public ImageAdapter(@NonNull Context context,  List<Dataitems> list,boolean isCenter) {
        super(context, 0);
        this.list = list;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isCenter=isCenter;
    }

    @Nullable
    @Override
    public Dataitems getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        convertView=inflater.inflate(R.layout.image_item_layout,parent,false);
        LinearLayout linearLayout=convertView.findViewById(R.id.layout);
        if (isCenter){
            linearLayout.setGravity(Gravity.CENTER);
        }
        TextView name=convertView.findViewById(R.id.name);
        ImageView image=convertView.findViewById(R.id.image);
        Dataitems dataitems=list.get(position);
        name.setText(dataitems.getName());
        image.setBackgroundResource(dataitems.getImage());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
