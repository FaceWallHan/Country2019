package com.end.summer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.end.summer.bean.DataViolation;

import java.util.List;

public class ViolationAdapter extends ArrayAdapter<DataViolation> {
    private List<DataViolation>list;
    private OnViolationItemListener onViolationItemListener;

    public void setOnViolationItemListener(OnViolationItemListener onViolationItemListener) {
        this.onViolationItemListener = onViolationItemListener;
    }

    public ViolationAdapter(@NonNull Context context, List<DataViolation>list) {
        super(context, 0);
        this.list=list;
    }
    public interface OnViolationItemListener{
        void onItemClick(int position);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public DataViolation getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return convertView;
    }
    private class ViewHolder{

    }
}
