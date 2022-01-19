package com.wons.myposproject.schedule;

import java.util.ArrayList;

public class Schedule {

    private String date;
    private ArrayList<String> todo;

    public String getDate() {
        return date;
    }

    public ArrayList<String> getTodo() {
        return todo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTodo(ArrayList<String> todo) {
        this.todo = todo;
    }
}
