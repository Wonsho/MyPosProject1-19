package com.wons.myposproject.items;

public class Item {

    private String itemName;
    private String unitPrice;
    private String quantity;

    public String getItem() {
        return itemName;
    }

    public void setItem(String item) {
        this.itemName = item;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice.trim();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity.trim();
    }

    public String getAllPrice() {
        String allPrice = String.valueOf( Integer.parseInt(getUnitPrice()) * Integer.parseInt(getQuantity()));
        return allPrice;
    }

}
