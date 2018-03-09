package com.example.myapplication.todolistapp.model;

/**
 * Created by Yara on 04-Mar-18.
 */

public class Task {
    int id;
    String task,date,time,status;

    public Task() {
    }

    public Task(String task,String date, String time, String status) {
        this.date = date;
        this.status = status;
        this.task = task;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
