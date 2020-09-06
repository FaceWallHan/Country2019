package com.end.summer.bean;

public class Dataitems {
    private int image;
    private String name;

    public Dataitems() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dataitems(int image, String name) {
        this.image = image;
        this.name = name;
    }
}
