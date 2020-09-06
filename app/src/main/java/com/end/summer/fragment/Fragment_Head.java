package com.end.summer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.end.summer.AppClient;
import com.end.summer.R;
import com.end.summer.activity.CheckActivity;
import com.end.summer.activity.UserCenterActivity;
import com.end.summer.bean.DataInfo;
import com.end.summer.bean.DataWeather;
import com.end.summer.net.VolleyLo;
import com.end.summer.net.VolleyTo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Fragment_Head extends Fragment implements View.OnClickListener {
    private View view;
    private VolleyTo volleyTo,volleyToWeather;
    private PieChart chart;
    private PieData data;
    private PieDataSet dataSet;
    private List<Integer>color;
    private List<PieEntry>size;
    private TextView tomorrow_type,tomorrow_weather,today_type,today_weather;
    private TextView light_type,sport_type,dress_type,cold_type;
    private ImageView tomorrow_image,today_image;
    private int light=0,sport=0,dress=0,cold=0,air=0,car=0;
    private String reLight="",reSport="",reDress="",reCold="",reAir="",reCar="",tomorrowIndex="";
    private int lightColor=0,sportColor=0,dressColor=0,coldColor=0,airColor=0;
    private Random random;
    private AppClient app;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.fragment_head_layout,container,false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        setVolleyTo();
        setVolleyToWeather();
    }
    private void setVolleyToWeather(){
        volleyToWeather=new VolleyTo();
        volleyToWeather.setUrl("get_weather_info").setDialog(getContext()).setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object=array.getJSONObject(i);
                        if (tomorrowIndex.equals("")){
                            tomorrowIndex=array.getJSONObject(1).getString("interval");
                        }
                        DataWeather weather=new DataWeather();
                        String temperature=object.getString("interval");
                        String[] id=temperature.split("~");
                        weather.setToday(getTomorrow(i,"MM月dd日"));
                        weather.setTodayDate(getTomorrow(i,"EE"));
                        weather.setIndex(getCenterNumber(temperature));
                        weather.setType(object.getString("weather"));
                        weather.setMaxIndex(Integer.parseInt(id[0]));
                        weather.setMinIndex(Integer.parseInt(id[1]));
                        switch (object.getString("weather")){
                            case "雾天":
                                weather.setImageResource(R.drawable.fog);
                                break;
                            case "小雨":
                                weather.setImageResource(R.drawable.rain);
                                break;
                            case "晴":
                                weather.setImageResource(R.drawable.sun);
                                break;
                            case "阴":
                                weather.setImageResource(R.drawable.cloudy);
                                break;
                        }
                        app.getWeatherList().add(weather);
                    }
                    today_weather.setText("今日天气 "+app.getWeatherList().get(0).getIndex()+"℃");
                    today_type.setText(app.getWeatherList().get(0).getType());
                    today_image.setBackgroundResource(app.getWeatherList().get(0).getImageResource());
                    tomorrow_weather.setText("明日天气 "+tomorrowIndex+"℃");
                    tomorrow_type.setText(app.getWeatherList().get(1).getType());
                    tomorrow_image.setBackgroundResource(app.getWeatherList().get(1).getImageResource());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }).start();
    }
    private void compInfo(){
        app.getIndexList().clear();
        if (light>=0&&light<1000){
            reLight="弱";
            lightColor=Color.parseColor("#4472c4");
        }else if (light>=1000&&light<=3000){
            reLight="中等";
            lightColor=Color.parseColor("#00b050");
        }else {
            reLight="强";
            lightColor=Color.parseColor("#ff0000");
        }
        /***/
        if (car>=0&&car<30){
            reCar="不适宜";
        }else if (car>=30&&car<=60){
            reCar="不太适宜";
        }else {
            reCar="适宜";
        }
        /***/
        if (air>=0&&air<35){
            reAir="优";
            airColor=Color.parseColor("#44dc68");
        }else if (air>=35&&air<=75){
            reAir="良";
            airColor=Color.parseColor("#92d050");
        }else if (air>75&&air<115){
            reAir="轻度污染";
            airColor=Color.parseColor("#ffff40");
        } else if (air>=115&&air<150){
            reAir="中度污染";
            airColor=Color.parseColor("#bf9000");
        }else {
            reAir="重度污染";
            airColor=Color.parseColor("#993300");
        }
        /***/
        if (dress>=0&&dress<12){
            reDress="冷";
            dressColor=Color.parseColor("#3462f4");
        }else if (dress>=12&&dress<=21){
            reDress="舒适";
            dressColor=Color.parseColor("#92d050");
        }else if (dress>21&&dress<35){
            reDress="温暖";
            dressColor=Color.parseColor("#44dc68");
        }else {
            reDress="热";
            dressColor=Color.parseColor("#ff0000");
        }
        /***/
        if (cold>=0&&cold<=50){
            reCold="较易发";
            coldColor=Color.parseColor("#ff0000");
        }else {
            reCold="少发";
            coldColor=Color.parseColor("#ffff40");
        }
        /***/
        if (sport>=0&&sport<3000){
            reSport="适宜";
            sportColor=Color.parseColor("#44dc68");
        }else if (sport>=3000&&sport<6000){
            reSport="中";
            sportColor=Color.parseColor("#ffc000");
        }else {
            reSport="较不宜";
            sportColor=Color.parseColor("#8149ac");
        }
        app.getIndexList().add(new DataInfo(reLight,"紫外线指数",lightColor,light,R.drawable.light_index));
        app.getIndexList().add(new DataInfo(reAir,"空气污染指数", airColor,air,R.drawable.air_index));
        app.getIndexList().add(new DataInfo(reSport,"运动指数", sportColor,sport,R.drawable.sport_index));
        app.getIndexList().add(new DataInfo(reDress,"穿衣指数", dressColor,dress,R.drawable.dress_index));
        app.getIndexList().add(new DataInfo(reCold,"感冒指数",coldColor,cold,R.drawable.cold_index));
        app.getIndexList().add(new DataInfo(reCar,"洗车指数",Color.BLACK,car,R.drawable.clear_car_index));
        light_type.setText(reLight);
        dress_type.setText(reDress);
        cold_type.setText(reCold);
        sport_type.setText(reSport);
        light_type.setTextColor(lightColor);
        dress_type.setTextColor(dressColor);
        cold_type.setTextColor(coldColor);
        sport_type.setTextColor(sportColor);
    }
    private void setVolleyTo(){
        volleyTo=new VolleyTo();
        volleyTo.setUrl("get_all_sense").setDialog(getContext()).setLoop(AppClient.loop).setTime(3000).setVolleyLo(new VolleyLo() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        light=jsonObject.getInt("illumination");
                        sport=jsonObject.getInt("co2");
                        dress=jsonObject.getInt("temperature");
                        cold=jsonObject.getInt("humidity");
                        air=jsonObject.getInt("pm25");
                        car=random.nextInt(100);
                        compInfo();
                        changeChart();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }).start();
    }
    private void startAction(Activity activity){
        if (!app.isLogin()){
            Toast.makeText(app, "您未登录，请登录后查看", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(getContext(),activity.getClass()));
        }
    }
    private void changeChart(){
        size.clear();
        color.clear();
        size.add(new PieEntry(air,""));
        size.add(new PieEntry(100,""));
        color.add(Color.parseColor("#983301"));
        color.add(Color.parseColor("#33CB01"));
        chart.setCenterText(reAir);
        dataSet.notifyDataSetChanged();
        data.notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.postInvalidate();
    }
    private void inView(){
        view.findViewById(R.id.subway).setOnClickListener(this);
        view.findViewById(R.id.today).setOnClickListener(this);
        view.findViewById(R.id.tomorrow).setOnClickListener(this);
        view.findViewById(R.id.check_in).setOnClickListener(this);
        view.findViewById(R.id.user_center).setOnClickListener(this);
        view.findViewById(R.id.pay).setOnClickListener(this);
        chart=view.findViewById(R.id.chart);
        tomorrow_type=view.findViewById(R.id.tomorrow_type);
        tomorrow_weather=view.findViewById(R.id.tomorrow_weather);
        today_type=view.findViewById(R.id.today_type);
        today_weather=view.findViewById(R.id.today_weather);
        light_type=view.findViewById(R.id.light_type);
        sport_type=view.findViewById(R.id.sport_type);
        dress_type=view.findViewById(R.id.dress_type);
        cold_type=view.findViewById(R.id.cold_type);
        today_image=view.findViewById(R.id.today_image);
        tomorrow_image=view.findViewById(R.id.tomorrow_image);
        random=new Random();
        app= (AppClient) getActivity().getApplication();
        size=new ArrayList<>();
        color=new ArrayList<>();
        dataSet=new PieDataSet(size,"");
        data=new PieData(dataSet);
        dataSet.setColors(color);
        data.setDrawValues(false);/***/
        chart.setData(data);
        chart.getLegend().setEnabled(false);
        chart.setDescription(null);
        chart.setTouchEnabled(false);
    }
    private int getCenterNumber(String text){
        int index=0;
        String[] id=text.split("~");
        for (int i = 0; i < id.length; i++) {
            index+=Integer.parseInt(id[i]);
        }
        return index/id.length;
    }
    public String getTomorrow(int tomorrow,String type) {
        Date day=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DAY_OF_MONTH, tomorrow);
        SimpleDateFormat df = new SimpleDateFormat(type, Locale.CHINESE);
        return df.format(calendar.getTime());
    }
    private String getWeekday(String date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        SimpleDateFormat sdw = new SimpleDateFormat("E", Locale.CHINESE);
        Date d = null;
        try {
            d =  sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.today:
                startAction(null);/**天气信息*/
                break;
            case R.id.tomorrow:
                startAction(null);/**天气信息*/
                break;
            case R.id.subway:
                startActivity(new Intent());
                break;
            case R.id.check_in:
                startActivity(new Intent(getContext(), CheckActivity.class));
                break;
            case R.id.user_center:
                startAction(null);/**用户中心*/
                break;
            case R.id.pay:
                startActivity(new Intent());
                break;
        }
    }
}
