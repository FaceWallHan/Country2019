package com.end.summer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.end.summer.R;
import com.end.summer.bean.DataWeather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
    private List<DataWeather>list;

    public WeatherAdapter(List<DataWeather> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }
    private String getTodayFormat(String date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        SimpleDateFormat sdw = new SimpleDateFormat("M月dd日", Locale.CHINESE);
        Date d = null;
        try {
            d =  sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataWeather weather=list.get(i);
        viewHolder.moment.setText(weather.getToday());
        if (i==0){
            viewHolder.week.setText("(今日"+weather.getTodayDate()+")");
        }else {
            viewHolder.week.setText(weather.getTodayDate());
        }
        viewHolder.type.setText(weather.getType());
        viewHolder.weather_resource.setBackgroundResource(weather.getImageResource());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView moment,week,type;
        ImageView weather_resource;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            moment=itemView.findViewById(R.id.moment);
            week=itemView.findViewById(R.id.week);
            type=itemView.findViewById(R.id.type);
            weather_resource=itemView.findViewById(R.id.weather_resource);
        }
    }
}
