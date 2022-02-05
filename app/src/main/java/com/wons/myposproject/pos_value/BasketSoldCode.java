package com.wons.myposproject.pos_value;

public enum BasketSoldCode {
    CREDIT("C"),
    NOT_CREDIT("N");
    public String soldCode;
    BasketSoldCode(String code) {
        this.soldCode = code;
    }
}
