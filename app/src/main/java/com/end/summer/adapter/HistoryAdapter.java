package com.end.summer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.end.summer.R;
import com.end.summer.bean.sql.SqlHistory;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<SqlHistory> {
    private List<SqlHistory>list;

    public HistoryAdapter(@NonNull Context context, List<SqlHistory> list) {
        super(context, 0);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public SqlHistory getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout,parent,false);
        SqlHistory history=list.get(position);
        TextView id_item=convertView.findViewById(R.id.id_item);
        TextView car_id_item=convertView.findViewById(R.id.car_id_item);
        TextView money_item=convertView.findViewById(R.id.money_item);
        TextView user_item=convertView.findViewById(R.id.user_item);
        TextView time_item=convertView.findViewById(R.id.time_item);
        id_item.setText(history.getId()+"");
        car_id_item.setText(history.getCarId()+"");
        money_item.setText(history.getMoney());
        user_item.setText(history.getUser());
        time_item.setText(history.getTime());
        return convertView;
    }
}
