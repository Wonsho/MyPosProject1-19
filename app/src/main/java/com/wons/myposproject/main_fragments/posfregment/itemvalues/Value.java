package com.wons.myposproject.main_fragments.posfregment.itemvalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Value {

    @PrimaryKey
    @NonNull
    public String idCode;

    public String itemCode;
    public String koreanName;
    public String Update;
    public String X0;
    public String X1;
    public String X2;
    public String X3;
    public String X4;
    public String X5;
    public String X6;
    public String X7;
    public String X8;
    public String X9;
    public String X10;
    public String X11;
    public String X12;
    public String X13;
    public String X14;
    public String X15;
    public String X16;
    public String X17;
    public String X18;
    public String X19;
    public String X20;
    public String X21;
    public String X22;
    public String X23;
    public String X24;
    public String X25;
    public String X26;
    public String X27;
    public String X28;
    public String X29;
    public String X30;
    public String X31;
    public String X32;
    public String X33;
    public String X34;
    public String X35;
    public String X36;
    public String X37;
    public String X38;
    public String X39;
    public String X40;
    public String X41;
    public String X42;
    public String X43;
    public String X44;
    public String X45;
    public String X46;
    public String X47;
    public String X48;
    public String X49;
    public String X50;
    public String X51;
    public String X52;
    public String X53;
    public String X54;
    public String X55;
    public String X56;
    public String X57;
    public String X58;
    public String X59;
    public String X60;
    public String X61;

    public ArrayList<String> valueList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(
                this.X0, this.X1, this.X2, this.X3, this.X4, this.X5, this.X6, this.X7, this.X8, this.X9, this.X10,
                this.X11, this.X12, this.X13, this.X14, this.X15, this.X16, this.X17, this.X18, this.X19, this.X20,
                this.X21, this.X22, this.X23, this.X24, this.X25, this.X26, this.X27, this.X28, this.X29, this.X30,
                this.X31, this.X32, this.X33, this.X34, this.X35, this.X36, this.X37, this.X38, this.X39, this.X40,
                this.X41, this.X42, this.X43, this.X44, this.X45, this.X46, this.X47, this.X48, this.X49, this.X50,
                this.X51, this.X52, this.X53, this.X54, this.X55, this.X56, this.X57, this.X58, this.X59, this.X60,
                this.X61));
        return arrayList;
    }


}
