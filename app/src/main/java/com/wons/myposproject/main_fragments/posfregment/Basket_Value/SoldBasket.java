package com.wons.myposproject.main_fragments.posfregment.Basket_Value;

// TODO: 2022-02-05 외상이냐 아니냐 , 몇시에 팔렸냐 , 날짜가 언제 팔렸냐

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*이클래스를 먼저 올려주고
* 바스켓에서 아이템을 담을때
* 키값을 가져와서
* 바스켓 아이템을 동일 키값으로 저장*/
@Entity
public class SoldBasket {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int soldId;

    public String soldCode;
    public String date;
    public String time;

    SoldBasket(String soldCode, String date, String time) {
        this.soldCode = soldCode;
        this.time = time;
        this.date = date;
    }

}
