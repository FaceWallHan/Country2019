package com.end.summer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.end.summer.bean.DataInfo;
import com.end.summer.bean.DataWeather;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

public class AppClient extends LitePalApplication {
    private static SharedPreferences preferences;
    private static RequestQueue requestQueue;
    private List<DataInfo>indexList;
    private List<DataWeather>weatherList;

    public String getReadUser() {
        return preferences.getString("readUser","");
    }

    public void setReadUser(String readUser) {
        preferences.edit().putString("readUser",readUser).apply();
    }

    public String getName() {
        return preferences.getString("name","");
    }

    public void setName(String name) {
        preferences.edit().putString("name",name).apply();
    }

    public static String getUser() {
        return preferences.getString("user","user1");
    }

    public static void setUser(String user) {
        preferences.edit().putString("user",user).apply();
    }

    public List<DataInfo> getIndexList() {
        return indexList;
    }

    public List<DataWeather> getWeatherList() {
        return weatherList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue= Volley.newRequestQueue(this);
        indexList=new ArrayList<>();
        weatherList=new ArrayList<>();
    }
    public static void setRequestQueue(JsonObjectRequest jsonObjectRequest){
        requestQueue.add(jsonObjectRequest);
    }

    public static String getIpAddress() {
        return preferences.getString("ipAddress","192.168.1.111");
    }

    public static void setIpAddress(String ipAddress) {
        preferences.edit().putString("ipAddress",ipAddress).apply();
    }

    public boolean isRemember() {
        return preferences.getBoolean("remember",false);
    }

    public void setRemember(boolean remember) {
        preferences.edit().putBoolean("remember",remember).apply();
    }

    public String getPassWord() {
        return preferences.getString("passWord","");
    }

    public void setPassWord(String passWord) {
        preferences.edit().putString("passWord",passWord).apply();
    }

    public boolean isLogin() {
        return preferences.getBoolean("login",false);
    }

    public void setLogin(boolean login) {
        preferences.edit().putBoolean("login",login).apply();
    }
    public static boolean loop=true;/**控制跨activity线程*/
}
