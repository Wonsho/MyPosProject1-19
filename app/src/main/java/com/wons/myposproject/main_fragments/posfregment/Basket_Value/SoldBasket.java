package com.wons.myposproject.main_fragments.posfregment.Basket_Value;

// TODO: 2022-02-05 외상이냐 아니냐 , 몇시에 팔렸냐 , 날짜가 언제 팔렸냐

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    //todo
    // 솔트 코드 먼저 생성후 바구니의 아이디 값을 가져온다음
    // 그 아이디 값으로 상품을 DB에 저장
    // 그다음 판매 날짜를 넣고
    // 태그 까지 저장 (태그는 외상일때 적어서 나중에 외상 태그로 조회 가능)*/

    public String soldCode;
    public String date;
    public String time;
    public String basketTag;

    SoldBasket(@NonNull String soldCode,@NonNull String date,@NonNull String time,@Nullable String basketTag) {
        this.soldCode = soldCode;
        this.time = time;
        this.date = date;
        this.basketTag = basketTag;
    }

}
