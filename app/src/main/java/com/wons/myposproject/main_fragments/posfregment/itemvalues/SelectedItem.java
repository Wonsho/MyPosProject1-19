package com.wons.myposproject.main_fragments.posfregment.itemvalues;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
final public class SelectedItem {
    private static ArrayList<Value> selectedItemList;
    public static ArrayList<String> verticalValue;
    private static ArrayList<String> horizontalValue;
    public static HashMap<String, ArrayList<String>> valueOfKey;
    public static HashMap<String, HashMap<String, String>> item;
    private static SelectedItem selectedItem;

    private SelectedItem() {
        selectedItem = new SelectedItem();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void selectedItemSet(ArrayList<Value> selectedItem) {
        selectedItem = selectedItem;
        verticalValue = new ArrayList<>();
        horizontalValue = new ArrayList<>();
        valueOfKey = new HashMap<>();
        item = new HashMap<>();
        for (Value value : selectedItem) {
            if (value.X0.equals("standard")) {
                horizontalValue = value.valueList();
                horizontalValue.removeIf(String::isEmpty);
            } else {
                verticalValue.add(value.X0);
            }
        }
        //todo vertical 값을 하나 가져와서 그에 맞는 value값을 가져와 자리 체크 후 각각 HashMap으로 저장*/
        for (String vertical : verticalValue) {
            for (Value value : selectedItem) {
                if (value.X0.equals(vertical)) {
                    ArrayList<String> priceValue = value.valueList();
                    HashMap<String, String> priceMap = new HashMap<>();
                    ArrayList<String> horizontal = new ArrayList<>();
                    for (int j = 0; j < horizontalValue.size(); j++) {
                        if (!priceValue.get(j).isEmpty()) {
                            priceMap.put(horizontalValue.get(j), priceValue.get(j));
                            horizontal.add(horizontalValue.get(j));
                        }
                    }
                    item.put(vertical, priceMap);
                    valueOfKey.put(vertical, horizontal);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static SelectedItem getSelectedItem(ArrayList<Value> selectedItemList) {
        selectedItemSet(selectedItemList);
        return selectedItem;
    }
}
