package com.wons.myposproject.main_fragments.posfregment.itemvalues;

import java.util.ArrayList;
import java.util.List;

public enum Group {
    HEX_BOLT("육각볼트", GroupCode.HEX_BOLT.groupCode),
    NUT("너트", GroupCode.NUT.groupCode),
    WASHER("와샤", GroupCode.WASHER.groupCode),
    WRENCH("렌치볼트", GroupCode.WRENCH.groupCode),
    BUTTON_WRENCH("둥근렌치", GroupCode.BUTTON_WRENCH.groupCode),
    PLAT_WRENCH("접시렌치", GroupCode.PLAT_WRENCH.groupCode),
    NO_HEAD_WRENCH("무두렌치", GroupCode.NO_HEAD_WRENCH.groupCode),
    FULL_THREADED_BOLT("전산볼트", GroupCode.FULL_THREADED_BOLT.groupCode),
    LAG_SCREW("래그스크류", GroupCode.LAG_SCREW.groupCode),
    I_BOLT_AND_NUT("아이볼트너트", GroupCode.I_BOLT_AND_NUT.groupCode),
    KYUNGCHUP_BOLT("경첩볼트", GroupCode.KYUNGCHUP_BOLT.groupCode),
    GUNGAK_BOLT("근각볼트", GroupCode.GUNGAK_BOLT.groupCode),
    NASA("철 일반용,철판용 나사", GroupCode.NASA.groupCode),
    STEIN_SCREW("스텐피스", GroupCode.STEIN_SCREW.groupCode),
    U_BOLT("유볼트", GroupCode.U_BOLT.groupCode),
    ANCHOR("앙카", GroupCode.ANCHOR.groupCode),
    SAMS_BOLT("셈스볼트", GroupCode.SAMS_BOLT.groupCode),
    FRENCH_BOLT("후렌치 볼트",GroupCode.FRENCH_BOLT.groupCode),
    OTHER_THINGS("기타류", GroupCode.OTHER_THINGS.groupCode);

    public String koreanName;
    public String groupCode;
    public List<Item> itemList;

    Group(String koreanName, String groupItemCode) {
        this.koreanName = koreanName;
        this.groupCode = groupItemCode;
        this.itemList = addItemList();
    }
    private List<Item> addItemList() {
        Item[] itemList = Item.values();
        List<Item> itemLists = new ArrayList<>();
        for (Item itemList1 : itemList) {
            if (itemList1.groupCode.equals(this.groupCode)) {
                itemLists.add(itemList1);
            }
        }
        return itemLists;
    }
}


    /*
육각볼트--
너트--
와샤--
렌치볼트--
둥근렌치--
접시렌치--
무두렌치--
전산볼트--
래그스쿠류--
아이볼트,너트--
경첩볼트--
근각볼트--
기타류--
철 일반용,철판용 나사--
스텐피스류--
유볼트--
앙카류--
*/

