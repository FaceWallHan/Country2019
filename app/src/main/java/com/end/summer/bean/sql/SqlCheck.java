package com.end.summer.bean.sql;

import org.litepal.crud.LitePalSupport;

public class SqlCheck extends LitePalSupport {
    private String time;
    private boolean isCheck;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SqlCheck(String time, boolean isCheck, int id) {
        this.time = time;
        this.isCheck = isCheck;
        this.id = id;
    }

    public SqlCheck() {
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
