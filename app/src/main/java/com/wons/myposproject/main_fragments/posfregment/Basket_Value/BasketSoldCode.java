package com.wons.myposproject.main_fragments.posfregment.Basket_Value;

public enum BasketSoldCode {
    CREDIT("C"),
    NOT_CREDIT("N");
    public String soldCode;
    BasketSoldCode(String code) {
        this.soldCode = code;
    }
}
