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
import com.end.summer.bean.DataCheck;

import java.util.List;

public class CheckAdapter extends ArrayAdapter<DataCheck> {
    private List<DataCheck> list;
    private OnItemCompleteListener listener;

    public void setListener(OnItemCompleteListener listener) {
        this.listener = listener;
    }

    public CheckAdapter(@NonNull Context context, List<DataCheck> list) {
        super(context, 0);
        this.list=list;
    }
    public interface OnItemCompleteListener{
        void onItemCompleteClick(int position);
    }

    @Nullable
    @Override
    public DataCheck getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_layout,parent,false);
        TextView task=convertView.findViewById(R.id.task);
        TextView complete=convertView.findViewById(R.id.complete);
        DataCheck check=list.get(position);
        task.setText(check.getTask());
        complete.setText(check.getComplete());
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCompleteClick(position);
            }
        });
        return convertView;
    }
}
