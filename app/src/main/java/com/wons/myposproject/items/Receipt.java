package com.wons.myposproject.items;

import java.util.ArrayList;

public class Receipt {

    private String date;
    private ArrayList<Item> items;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

}
