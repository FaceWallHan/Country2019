package com.end.summer.bean.sql;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SqlUser extends LitePalSupport implements Serializable {
    private int id;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SqlUser(int id, int index, String sex, String name, String userName, String tel, boolean isCollection, boolean isTop) {
        this.id = id;
        this.index = index;
        this.sex = sex;
        this.name = name;
        this.userName = userName;
        this.tel = tel;
        this.isCollection = isCollection;
        this.isTop = isTop;
    }

    private int index;
    private String sex;
    private String name;
    private String userName;

    public SqlUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    private String tel;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    private boolean isCollection;

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }


    private boolean isTop;
}
