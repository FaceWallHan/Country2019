package com.end.summer.bean;

public class DataViolation {
    private int id,image;
    private String carId,address,reason,money,time,fraction,status;
    private boolean process;

    public DataViolation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public DataViolation(int id, int image, String carId, String address, String reason, String money, String time, String fraction, String status, boolean process) {
        this.id = id;
        this.image = image;
        this.carId = carId;
        this.address = address;
        this.reason = reason;
        this.money = money;
        this.time = time;
        this.fraction = fraction;
        this.status = status;
        this.process = process;
    }
}
