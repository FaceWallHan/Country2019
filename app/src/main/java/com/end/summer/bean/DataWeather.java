package com.end.summer.bean;

public class DataWeather {
    private String today,todayDate,type;
    private int imageResource,index,maxIndex;

    public DataWeather(String today, String todayDate, String type, int imageResource, int maxIndex, int minIndex, int index) {
        this.today = today;
        this.todayDate = todayDate;
        this.type = type;
        this.imageResource = imageResource;
        this.maxIndex = maxIndex;
        this.minIndex = minIndex;
        this.index = index;
    }

    private int minIndex;

    public int getIndex() {
        return index;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public DataWeather() {
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
