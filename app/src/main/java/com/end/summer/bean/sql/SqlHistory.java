package com.end.summer.bean.sql;

import android.support.annotation.NonNull;

import org.litepal.crud.LitePalSupport;

import java.util.Comparator;

public class SqlHistory extends LitePalSupport {
    private  int id,carId;
    private String money,user,time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SqlHistory() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SqlHistory(int id, int carId, String money, String user, String time) {
        this.id = id;
        this.carId = carId;
        this.money = money;
        this.user = user;
        this.time = time;
    }
    public static  class timeDesc implements Comparator<SqlHistory>{
        @Override
        public int compare(SqlHistory o1, SqlHistory o2) {
            return -(o1.getTime().compareTo(o2.getTime()));
        }
    }
    public static  class timeAsc implements Comparator<SqlHistory>{
        @Override
        public int compare(SqlHistory o1, SqlHistory o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    }
}
