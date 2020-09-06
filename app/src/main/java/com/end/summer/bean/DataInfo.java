package com.end.summer.bean;

public class DataInfo {
    private String type;

    public DataInfo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }



    private String name;

    public DataInfo(String type, String name, int color, int indexNumber, int imageResource) {
        this.type = type;
        this.name = name;
        this.color = color;
        this.indexNumber = indexNumber;
        this.imageResource = imageResource;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
    private int indexNumber,imageResource;
}
