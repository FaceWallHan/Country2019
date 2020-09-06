package com.end.summer.bean;

public class DataCheck {
    private String task,complete;

    public DataCheck(String task, String complete) {
        this.task = task;
        this.complete = complete;
    }

    public DataCheck() {
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

}
