package com.wons.myposproject.pos_value;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BarCodeItem {
    @PrimaryKey
    @NonNull
    public String barCode;

    public String name;
    public String unitPrice;

    public BarCodeItem(String barCode, String name, String unitPrice) {
        this.barCode = barCode;
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
