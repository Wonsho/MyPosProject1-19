package com.wons.myposproject.main_fragments.infofragment;

import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

public interface InfoDialogCallbackForString {
    void callBack(String str);
}
interface InfoDiaLogCallbackForBoolean {
    void callBack(boolean yesOrNo);
}
interface InfoDiaLogCallbackForGetItem {
    void callback(BarCodeItem item);
}
