package com.end.summer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.adapter.InfoAdapter;
import com.end.summer.adapter.WeatherAdapter;
import com.end.summer.bean.DataWeather;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WarnWeatherActivity extends BaseActivity {
    private TextView today_date,today_index,today_type_main;
    private ImageView today_resource;
    private RecyclerView weather_set;
    private AppClient app;
    private WeatherAdapter weatherAdapter;
    private LineChart weather_chart;
    private GridView info_set;
    private InfoAdapter infoAdapter;
    private List<Integer>max,min;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            infoAdapter.notifyDataSetChanged();
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warn_weather_layout);
        setText("天气预报");
        inView();
        setAppText();
        addData();
        setChart();
        infoAdapterChange();
    }
    private LineDataSet getDateSet(List<Integer> integerList,int lineColor){
        List<Entry>list=new ArrayList<>();
        for (int i = 0; i < integerList.size(); i++) {
            list.add(new Entry(i,integerList.get(i)));
        }
        LineDataSet dataSet=new LineDataSet(list,"");
        list.add(new Entry());
        dataSet.setCircleColor(lineColor);
        dataSet.setColor(lineColor);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (int)value+"°";
            }
        });
        return dataSet;
    }
    private void infoAdapterChange(){
        new Thread(){
            @Override
            public void run() {
                while (AppClient.loop){
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void setChart(){
        for (int i = 0; i < app.getWeatherList().size(); i++) {
            DataWeather dataWeather=app.getWeatherList().get(i);
            max.add(dataWeather.getMaxIndex());
            min.add(dataWeather.getMinIndex());
        }
        LineData data=new LineData(getDateSet(max, Color.parseColor("#F4C977")));
        data.addDataSet(getDateSet(min,Color.parseColor("#9FC8E3")));
        weather_chart.setData(data);
        weather_chart.getLegend().setEnabled(false);
        weather_chart.getXAxis().setEnabled(false);
        weather_chart.getAxisLeft().setEnabled(false);
        weather_chart.getAxisRight().setEnabled(false);
        weather_chart.setDescription(null);
        weather_chart.setTouchEnabled(false);
    }
    private void addData(){
        LinearLayoutManager manager=new LinearLayoutManager(WarnWeatherActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        weather_set.setAdapter(weatherAdapter);
        weather_set.setLayoutManager(manager);
    }
    private void setAppText(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINESE);
        Date date=new Date(System.currentTimeMillis());
        today_date.setText(format.format(date));
        if (app.getWeatherList().size()!=0){
            today_index.setText(app.getWeatherList().get(0).getIndex()+"度");
            today_type_main.setText(app.getWeatherList().get(0).getType()+"");
            today_resource.setBackgroundResource(app.getWeatherList().get(0).getImageResource());
        }
    }
    private void inView(){
        today_date=findViewById(R.id.today_date);
        today_index=findViewById(R.id.today_index);
        today_type_main=findViewById(R.id.today_type_main);
        today_resource=findViewById(R.id.today_resource);
        weather_set=findViewById(R.id.weather_set);
        app= (AppClient) getApplication();
        weatherAdapter=new WeatherAdapter(app.getWeatherList());
        info_set=findViewById(R.id.info_set);
        weather_chart=findViewById(R.id.weather_chart);
        infoAdapter=new InfoAdapter(WarnWeatherActivity.this,app.getIndexList());
        info_set.setAdapter(infoAdapter);
        max=new ArrayList<>();
        min=new ArrayList<>();
    }
}
