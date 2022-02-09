package com.wons.myposproject.main_fragments.posfregment.Basket_Value;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// TODO: 2022-02-05 어떤 품목이 팔렸었냐
@Entity
public class BasketTypeItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int itemId;

    public String itemName;
    public String itemStandard;
    public String unitPrice;
    public String quantity;
    public String basketCode;

    public BasketTypeItem(String itemName, String itemStandard, String unitPrice, String quantity) {
        this.itemName = itemName;
        this.itemStandard = itemStandard;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
