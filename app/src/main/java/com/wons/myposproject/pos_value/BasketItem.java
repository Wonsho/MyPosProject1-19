package com.wons.myposproject.pos_value;


import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

// TODO: 2022-02-05 어떤 품목이 팔렸었냐
public class BasketItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int itemId;

    public String itemName;
    public String itemStandard;
    public String unitPrice;
    public String quantity;
    public String basketCode;

    public BasketItem(String itemName, String itemStandard, String unitPrice, String quantity) {
        this.itemName = itemName;
        this.itemStandard = itemStandard;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    BasketItem(String itemName, String itemStandard, String unitPrice, String quantity, String basketCode) {
        this.itemName = itemName;
        this.itemStandard = itemStandard;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.basketCode = basketCode;
    }
}
