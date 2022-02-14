package com.wons.myposproject.main_fragments.posfregment.itemvalues;

public enum Item {

    //todo  다이로그 코드는 수량만 묻기 , 너트와샤평와샤 묻기, 길이 묻기*/
    HEX_BOLT_NORMAL("일반볼트", GroupCode.HEX_BOLT.groupCode, "A1",ItemDialogCode.QUANTITY_NUT_SW_PW),
    HEX_BOLT_NORMAL_INCH("일반볼트 인치",GroupCode.HEX_BOLT.groupCode,"A2",ItemDialogCode.QUANTITY_NUT_SW_PW),
    HEX_BOLT_STS("스텐볼트",GroupCode.HEX_BOLT.groupCode,"A3",ItemDialogCode.QUANTITY_NUT_SW_PW),
    HEX_BOLT_STS_INCH("스텐볼트 인치",GroupCode.HEX_BOLT.groupCode,"A4",ItemDialogCode.QUANTITY_NUT_SW_PW),
    HEX_BOLT_CM_KOREA("콜라볼트(국산)",GroupCode.HEX_BOLT.groupCode,"A5",ItemDialogCode.QUANTITY_NUT_SW_PW),
    HEX_BOLT_CM_KOREA_INCH("콜라볼트 인치(국산)",GroupCode.HEX_BOLT.groupCode,"A6",ItemDialogCode.QUANTITY_NUT_SW_PW),
    WASHER_SPRING("스프링와샤",GroupCode.WASHER.groupCode,"A7",ItemDialogCode.QUANTITY),
    WASHER_PLAT("평와샤", GroupCode.WASHER.groupCode,"A8",ItemDialogCode.QUANTITY),
    WASHER_PLAT_BIG_STS("대와샤 스텐", GroupCode.WASHER.groupCode,"A9",ItemDialogCode.QUANTITY),
    WASHER_PLAT_BIG_NORMAL("대와샤 도금", GroupCode.WASHER.groupCode,"A10",ItemDialogCode.QUANTITY),
    NUT_HEX("육각너트", GroupCode.NUT.groupCode,"A11",ItemDialogCode.QUANTITY),
    NUT_NYLON("나일론너트",GroupCode.NUT.groupCode,"A12",ItemDialogCode.QUANTITY),
    NUT_FRENCH("후렌치너트", GroupCode.NUT.groupCode,"A13",ItemDialogCode.QUANTITY),
    NUT_CAP("캡너트", GroupCode.NUT.groupCode,"A14",ItemDialogCode.QUANTITY),
    NUT_BUTTERFLY("나비너트", GroupCode.NUT.groupCode,"A15",ItemDialogCode.QUANTITY),
    NUT_SINJU("신주너트", GroupCode.NUT.groupCode,"A16",ItemDialogCode.QUANTITY),
    NUT_U("유너트",GroupCode.NUT.groupCode,"A17",ItemDialogCode.QUANTITY),
    NUT_WELDING("웰딩너트", GroupCode.NUT.groupCode,"A18",ItemDialogCode.QUANTITY),
    NUT_CONNECT("연결너트", GroupCode.NUT.groupCode,"A19",ItemDialogCode.QUANTITY),
    WRENCH_SCM("CM렌치", GroupCode.WRENCH.groupCode,"A20",ItemDialogCode.QUANTITY),
    WRENCH_STS("STS렌치", GroupCode.WRENCH.groupCode,"A21",ItemDialogCode.QUANTITY),
    WRENCH_BUTTON_STS("STS둥근렌치", GroupCode.BUTTON_WRENCH.groupCode,"A22",ItemDialogCode.QUANTITY),
    WRENCH_BUTTON_CM("CM둥근렌치", GroupCode.BUTTON_WRENCH.groupCode,"A23",ItemDialogCode.QUANTITY),
    WRENCH_PLAT_CM("CM접시렌치", GroupCode.PLAT_WRENCH.groupCode,"A24",ItemDialogCode.QUANTITY),
    WRENCH_PLAT_STS("STS접시렌치", GroupCode.PLAT_WRENCH.groupCode,"A25",ItemDialogCode.QUANTITY),
    WRENCH_NO_HEAD_STS("STS무두렌치", GroupCode.NO_HEAD_WRENCH.groupCode,"A26",ItemDialogCode.QUANTITY),
    WRENCH_NO_HEAD_CM("CM무두렌치", GroupCode.NO_HEAD_WRENCH.groupCode,"A27",ItemDialogCode.QUANTITY),
    THREADED_BOLT("전산볼트", GroupCode.FULL_THREADED_BOLT.groupCode,"A28",ItemDialogCode.LENGTH),
    THREADED_BOLT_INCH("전산볼트 인치", GroupCode.FULL_THREADED_BOLT.groupCode,"A29",ItemDialogCode.LENGTH),
    GAKSAN_BOLT("각산볼트", GroupCode.FULL_THREADED_BOLT.groupCode,"A30",ItemDialogCode.QUANTITY),
    LAG_SCREW_NORMAL("레그스크류 일반",GroupCode.LAG_SCREW.groupCode,"A31",ItemDialogCode.QUANTITY),
    LAG_SCREW_STS("레그스크류 스텐", GroupCode.LAG_SCREW.groupCode,"A32",ItemDialogCode.QUANTITY),
    I_BOLT("아이볼트", GroupCode.I_BOLT_AND_NUT.groupCode,"A33",ItemDialogCode.QUANTITY),
    I_NUT("아이너트", GroupCode.I_BOLT_AND_NUT.groupCode,"A34",ItemDialogCode.QUANTITY),
    KYUNGCHUP_BOLT_NORMAL("경첩볼트 일반", GroupCode.KYUNGCHUP_BOLT.groupCode,"A35",ItemDialogCode.QUANTITY),
    KYUNGCHUP_BOLT_STS("경첩볼트 스텐", GroupCode.KYUNGCHUP_BOLT.groupCode,"A36",ItemDialogCode.QUANTITY),
    GUNGAK_BOLT_NORMAL("근각볼트 일반", GroupCode.GUNGAK_BOLT.groupCode,"A37",ItemDialogCode.QUANTITY),
    GUNGAK_BOLT_STS("근각볼트 스텐", GroupCode.GUNGAK_BOLT.groupCode,"A38",ItemDialogCode.QUANTITY),
    TURNBUCKLE("턴버클", GroupCode.OTHER_THINGS.groupCode,"A39",ItemDialogCode.QUANTITY),
    CLIP("클립" ,GroupCode.OTHER_THINGS.groupCode,"A40",ItemDialogCode.QUANTITY),
    SHACKLE("샤클",GroupCode.OTHER_THINGS.groupCode,"A41",ItemDialogCode.QUANTITY),
    SHACKLE_KOREA("샤클(국산)",GroupCode.OTHER_THINGS.groupCode,"A42",ItemDialogCode.QUANTITY),
    I_HOOK("아이후크",GroupCode.OTHER_THINGS.groupCode,"A43",ItemDialogCode.QUANTITY),
    CHAIN("체인",GroupCode.OTHER_THINGS.groupCode,"A44",ItemDialogCode.LENGTH),
    BINA("비나",GroupCode.OTHER_THINGS.groupCode,"A45",ItemDialogCode.QUANTITY),
    QUICK_LINK("퀵링크",GroupCode.OTHER_THINGS.groupCode,"A46",ItemDialogCode.QUANTITY),
    PULLEY("도르레",GroupCode.OTHER_THINGS.groupCode,"A47",ItemDialogCode.QUANTITY),
    WIRE("와이어",GroupCode.OTHER_THINGS.groupCode,"A48",ItemDialogCode.LENGTH),
    COSE("코스",GroupCode.OTHER_THINGS.groupCode,"A49",ItemDialogCode.QUANTITY),
    PEROLL("페롤",GroupCode.OTHER_THINGS.groupCode,"A50", ItemDialogCode.QUANTITY),
    SCREW_NORMAL_P_TYPE_G_TYPE("철-P형,G형,트러스,접시 머신", GroupCode.NASA.groupCode,"A51", ItemDialogCode.QUANTITY),
    SCREW_NORMAL_P_TYPE("철-P형,접시 1종", GroupCode.NASA.groupCode,"A52", ItemDialogCode.QUANTITY),
    SCREW_TRUS("철-트러스 1종", GroupCode.NASA.groupCode,"A53", ItemDialogCode.QUANTITY),
    SCREW_P_TYPE2("철-P형 2종커팅", GroupCode.NASA.groupCode,"A54",ItemDialogCode.QUANTITY),
    SCREW_TRUS2("철-트러스 2종커팅", GroupCode.NASA.groupCode,"A55",ItemDialogCode.QUANTITY),
    SCREW_STS_F_P_TYPE("스텐피스 F.P형", GroupCode.STEIN_SCREW.groupCode,"A56",ItemDialogCode.QUANTITY),
    SCREW_STS_T_TYPE("스텐피스 T형", GroupCode.STEIN_SCREW.groupCode,"A57",ItemDialogCode.QUANTITY),
    U_BOLT_NORMAL("유볼트 철", GroupCode.U_BOLT.groupCode,"A58",ItemDialogCode.QUANTITY),
    U_BOLT_NORMAL_INS("유볼트 철 절연", GroupCode.U_BOLT.groupCode,"A59",ItemDialogCode.QUANTITY),
    U_BOLT_STS("유볼트 STS304", GroupCode.U_BOLT.groupCode, "A60",ItemDialogCode.QUANTITY),
    ANCHOR_L("L앙카", GroupCode.ANCHOR.groupCode,"A61", ItemDialogCode.QUANTITY),
    ANCHOR_STRONG("스트롱 앙카", GroupCode.ANCHOR.groupCode,"A62", ItemDialogCode.QUANTITY),
    ANCHOR_STRING_STS("스트롱 앙카 스텐", GroupCode.ANCHOR.groupCode,"A63", ItemDialogCode.QUANTITY),
    ANCHOR_DROP_IN("드롭인 앙카", GroupCode.ANCHOR.groupCode,"A64", ItemDialogCode.QUANTITY),
    ANCHOR_WEDGE_NORMAL("웨지앙카(스터드앙카)", GroupCode.ANCHOR.groupCode,"A65", ItemDialogCode.QUANTITY),
    ANCHOR_SET_NORMAL("세트앙카", GroupCode.ANCHOR.groupCode,"A67", ItemDialogCode.QUANTITY),
    ANCHOR_DOUBLE("떠블앙카", GroupCode.ANCHOR.groupCode,"A70", ItemDialogCode.QUANTITY),
    ANCHOR_TOW("토우앙카", GroupCode.ANCHOR.groupCode,"A71", ItemDialogCode.QUANTITY),
    ANCHOR_SET_LONG_CAP("세트앙카 롱캡", GroupCode.ANCHOR.groupCode,"A72", ItemDialogCode.QUANTITY),
    SAMS_BOLT_NORMAL_P_TYPE("철-p형셈스", GroupCode.SAMS_BOLT.groupCode, "A73", ItemDialogCode.QUANTITY),
    SAMS_BOLT_NORMAL("철-육각셈스", GroupCode.SAMS_BOLT.groupCode, "A74", ItemDialogCode.QUANTITY),
    SAMS_BOLT_STS_P_TYPE("STS-P형셈스", GroupCode.SAMS_BOLT.groupCode, "A75",ItemDialogCode.QUANTITY),
    SAMS_BOLT_STS_HEX_TYPE("STS-육각셈스", GroupCode.SAMS_BOLT.groupCode, "A76", ItemDialogCode.QUANTITY),
    SAMS_BOLT_STS_WRENCH_TYPE("스텐렌지셈스 P/W,S/W", GroupCode.SAMS_BOLT.groupCode, "A77", ItemDialogCode.QUANTITY),
    FRENCH_BOLT_NORMAL("철-후렌지볼트", GroupCode.FRENCH_BOLT.groupCode, "A78", ItemDialogCode.QUANTITY_NUT_SW_PW),
    FRENCH_BOLT_STS("STS후렌지볼트", GroupCode.FRENCH_BOLT.groupCode, "A79", ItemDialogCode.QUANTITY_NUT_SW_PW),
    G_COIL("G코일", GroupCode.OTHER_THINGS.groupCode, "A80", ItemDialogCode.QUANTITY),
    R_PIN("R핀", GroupCode.OTHER_THINGS.groupCode, "A81", ItemDialogCode.QUANTITY),
    PARTITION_PIN("분활핀", GroupCode.OTHER_THINGS.groupCode, "A82", ItemDialogCode.QUANTITY),
    WASHER_SPRING_INCH("스프링와샤 인치", GroupCode.WASHER.groupCode, "A83",ItemDialogCode.QUANTITY),
    WASHER_PLAT_INCH("평와샤 인치", GroupCode.WASHER.groupCode, "A84", ItemDialogCode.QUANTITY),
    HEX_NUT_INCH("육각너트 인치", GroupCode.NUT.groupCode, "A85",ItemDialogCode.QUANTITY);

    public String koreanName;
    public String groupCode;
    public String itemCode;
    public String dialogCode;
    public String showCode;

    Item(String koreanName, String groupCode, String itemCode, String dialogCode, String showCode) {
        this.groupCode = groupCode;
        this.koreanName = koreanName;
        this.itemCode = itemCode;
        this.dialogCode = dialogCode;
        this.showCode = showCode;
    }
}
