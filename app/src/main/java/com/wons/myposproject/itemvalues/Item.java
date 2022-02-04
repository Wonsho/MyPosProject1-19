package com.wons.myposproject.itemvalues;

public enum Item {


    STS_HEX_BOLT("SHB","STS볼트", "HEX"),
    STS_HEX_BOLT_INCH("SHBI","STS볼트인치","HEX"),
    NORMAL_HEX_BOLT("NHB","일반볼트","HEX"),
    NORMAL_HEX_BOLT_INCH("NHBI","일반볼트인치","HEX"),
    CM_HEX_BOLT("CHB","CM볼트","HEX"),
    CM_HEX_BOLT_INCH("CHBI","CM볼트인치","HEX"),
    SCM_WRENCH_BOLT("CLB","SCM렌치","LEN"),
    STS_WRENCH_BOLT("SLB","STS렌치","LEN"),
    SCM_BUTTON_WRENCH_BOLT("CBLB","SCM둥근렌치","LNB"),
    STS_BUTTON_WRENCH_BOLT("SBLB","STS둥근렌치","LNB"),
    SCM_PLAT_WRENCH_BOLT("CPLB","SCM접시렌치","LNP"),
    STS_PLAT_WRENCH_BOLT("SPLB","STS접시렌치","LNP"),
    SCM_NOHEAD_WRENCH_BOLT("CNLB","SCM무두렌치","LNH"),
    STS_NOHEAD_WRENCH_BOLT("SNLB","STS무두렌치","LNH");

    public final String koreanName;
    public final  String groupCode;
    public final  String itemCode;
    public final  String verticalCode;
    public final  String horizontalCode;
//    public final  String horizontal_standard;
//    public final  String vertical_standard ;

    Item(String code, String koreanName, String groupCode) {
        this.itemCode = code;
        this.groupCode = groupCode;
        this.koreanName = koreanName;
        this.horizontalCode = code + "HV";
        this.verticalCode = code + "VV";
    }

//    HEX_BOLT_NORMAL("일반볼트", GroupCode.HEX_BOLT.groupCode, "A1"),
//    HEX_BOLT_NORMAL_INCH("일반볼트 인치",GroupCode.HEX_BOLT.groupCode,"A2"),
//    HEX_BOLT_STS("스텐볼트",GroupCode.HEX_BOLT.groupCode,"A3"),
//    HEX_BOLT_STS_INCH("스텐볼트 인치",GroupCode.HEX_BOLT.groupCode,"A4"),
//    HEX_BOLT_CM_KOREA("콜라볼트(국산)",GroupCode.HEX_BOLT.groupCode,"A5"),
//    HEX_BOLT_CM_KOREA_INCH("콜라볼트 인치(국산)",GroupCode.HEX_BOLT.groupCode,"A6"),
//    WASHER_SPRING("스프링와샤",GroupCode.WASHER.groupCode,"A7"),
//    WASHER_PLAT("평와샤", GroupCode.WASHER.groupCode,"A8"),
//    WASHER_PLAT_BIG_STS("대와샤 스텐", GroupCode.WASHER.groupCode,"A9"),
//    WASHER_PLAT_BIG_NORMAL("대와샤 도금", GroupCode.WASHER.groupCode,"A10"),
//    NUT_HEX("육각너트", GroupCode.NUT.groupCode,"A11"),
//    NUT_NYLON("나일론너트",GroupCode.NUT.groupCode,"A12"),
//    NUT_FRENCH("후렌치너트", GroupCode.NUT.groupCode,"A13"),
//    NUT_CAP("캡너트", GroupCode.NUT.groupCode,"A14"),
//    NUT_BUTTERFLY("나비너트", GroupCode.NUT.groupCode,"A15"),
//    NUT_SINJU("신주너트", GroupCode.NUT.groupCode,"A16"),
//    NUT_U("유너트",GroupCode.NUT.groupCode,"A17"),
//    NUT_WELDING("웰딩너트", GroupCode.NUT.groupCode,"A18"),
//    NUT_CONNECT("연결너트", GroupCode.NUT.groupCode,"A19"),
//    WRENCH_SCM("CM렌치", GroupCode.WRENCH.groupCode,"A20"),
//    WRENCH_STS("STS렌치", GroupCode.WRENCH.groupCode,"A21"),
//    WRENCH_BUTTON_STS("STS둥근렌치", GroupCode.BUTTON_WRENCH.groupCode,"A22"),
//    WRENCH_BUTTON_CM("CM둥근렌치", GroupCode.BUTTON_WRENCH.groupCode,"A23"),
//    WRENCH_PLAT_CM("CM접시렌치", GroupCode.PLAT_WRENCH.groupCode,"A24"),
//    WRENCH_PLAT_STS("STS접시렌치", GroupCode.PLAT_WRENCH.groupCode,"A25"),
//    WRENCH_NO_HEAD_STS("STS무두렌치", GroupCode.NO_HEAD_WRENCH.groupCode,"A26"),
//    WRENCH_NO_HEAD_CM("CM무두렌치", GroupCode.NO_HEAD_WRENCH.groupCode,"A27"),
//    THREADED_BOLT("전산볼트", GroupCode.FULL_THREADED_BOLT.groupCode,"A28"),
//    THREADED_BOLT_INCH("전산볼트 인치", GroupCode.FULL_THREADED_BOLT.groupCode,"A29"),
//    GAKSAN_BOLT("각산볼트", GroupCode.FULL_THREADED_BOLT.groupCode,"A30"),
//    LAG_SCREW_NORMAL("레그스크류 일반",GroupCode.LAG_SCREW.groupCode,"A31"),
//    LAG_SCREW_STS("레그스크류 스텐", GroupCode.LAG_SCREW.groupCode,"A32"),
//    I_BOLT("아이볼트", GroupCode.I_BOLT_AND_NUT.groupCode,"A33"),
//    I_NUT("아이너트", GroupCode.I_BOLT_AND_NUT.groupCode,"A34"),
//    KYUNGCHUP_BOLT_NORMAL("경첩볼트 일반", GroupCode.KYUNGCHUP_BOLT.groupCode,"A35"),
//    KYUNGCHUP_BOLT_STS("경첩볼트 스텐", GroupCode.KYUNGCHUP_BOLT.groupCode,"A36"),
//    GUNGAK_BOLT_NORMAL("근각볼트 일반", GroupCode.GUNGAK_BOLT.groupCode,"A37"),
//    GUNGAK_BOLT_STS("근각볼트 스텐", GroupCode.GUNGAK_BOLT.groupCode,"A38"),
//    TURNBUCKLE("턴버클", GroupCode.OTHER_THINGS.groupCode,"A39"),
//    CLIP("클립" ,GroupCode.OTHER_THINGS.groupCode,"A40"),
//    SHACKLE("샤클",GroupCode.OTHER_THINGS.groupCode,"A41"),
//    SHACKLE_KOREA("샤클(국산)",GroupCode.OTHER_THINGS.groupCode,"A42"),
//    I_HOOK("아이후크",GroupCode.OTHER_THINGS.groupCode,"A43"),
//    CHAIN("체인",GroupCode.OTHER_THINGS.groupCode,"A44"),
//    BINA("비나",GroupCode.OTHER_THINGS.groupCode,"A45"),
//    QUICK_LINK("퀵링크",GroupCode.OTHER_THINGS.groupCode,"A46"),
//    PULLEY("도르레",GroupCode.OTHER_THINGS.groupCode,"A47"),
//    WIRE("와이어",GroupCode.OTHER_THINGS.groupCode,"A48"),
//    COSE("코스",GroupCode.OTHER_THINGS.groupCode,"A49"),
//    PEROLL("페롤",GroupCode.OTHER_THINGS.groupCode,"A50"),
//    SCREW_NORMAL_P_TYPE_G_TYPE("철-P형,G형,트러스,접시 머신", GroupCode.NASA.groupCode,"A51"),
//    SCREW_NORMAL_P_TYPE("철-P형,접시 1종", GroupCode.NASA.groupCode,"A52"),
//    SCREW_TRUS("철-트러스 1종", GroupCode.NASA.groupCode,"A53"),
//    SCREW_P_TYPE2("철-P형 2종커팅", GroupCode.NASA.groupCode,"A54"),
//    SCREW_TRUS2("철-트러스 2종커팅", GroupCode.NASA.groupCode,"A55"),
//    SCREW_STS_F_P_TYPE("스텐피스 F.P형", GroupCode.STEIN_SCREW.groupCode,"A56"),
//    SCREW_STS_T_TYPE("스텐피스 T형", GroupCode.STEIN_SCREW.groupCode,"A57"),
//    U_BOLT_NORMAL("유볼트 철", GroupCode.U_BOLT.groupCode,"A58"),
//    U_BOLT_NORMAL_INS("유볼트 철 절연", GroupCode.U_BOLT.groupCode,"A59"),
//    U_BOLT_STS("유볼트 STS304", GroupCode.U_BOLT.groupCode, "A60"),
//    ANCHOR_L("L앙카", GroupCode.ANCHOR.groupCode,"A61"),
//    ANCHOR_STRONG("스트롱 앙카", GroupCode.ANCHOR.groupCode,"A62"),
//    ANCHOR_STRING_STS("스트롱 앙카 스텐", GroupCode.ANCHOR.groupCode,"A63"),
//    ANCHOR_DROP_IN("드롭인 앙카", GroupCode.ANCHOR.groupCode,"A64"),
//    ANCHOR_WEDGE_NORMAL("웨지앙카(스터드앙카) 철", GroupCode.ANCHOR.groupCode,"A65"),
//    ANCHOR_WEDGE_STS("웨지앙카(스터드앙카) SUS 304", GroupCode.ANCHOR.groupCode,"A66"),
//    ANCHOR_SET_NORMAL("세트앙카 철 ", GroupCode.ANCHOR.groupCode,"A67"),
//    ANCHOR_SET_STS_200("세트앙카 SUS200", GroupCode.ANCHOR.groupCode,"A68"),
//    ANCHOR_SET_STS_304("세트앙카 SYS 304", GroupCode.ANCHOR.groupCode,"A69"),
//    ANCHOR_DOUBLE("떠블앙카", GroupCode.ANCHOR.groupCode,"A70"),
//    ANCHOR_TOW("토우앙카", GroupCode.ANCHOR.groupCode,"A71"),
//    ANCHOR_SET_LONG_CAP("세트앙카 롱캡", GroupCode.ANCHOR.groupCode,"A72");

//    public String koreanName;
//    public String groupCode;
//    public String itemCode;
//    public String verticalCode;
//    public String horizontalCode;
//    public String horizontal_standard;
//    public String vertical_standard;
//
//
//    Item(String koreanName, String groupCode, String itemCode) {
//        this.groupCode = groupCode;
//        this.koreanName = koreanName;
//        this.itemCode = itemCode;
//        this.verticalCode = itemCode + "VV";
//        this.horizontalCode = itemCode + "HV";
//    }
}
