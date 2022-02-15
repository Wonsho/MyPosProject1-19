package com.wons.myposproject.main_fragments.posfregment.itemvalues;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectedItem {
    private ArrayList<Value> selectedItem;
    public ArrayList<String> verticalValue;
    private ArrayList<String> horizontalValue;
    public HashMap<String, HashMap<String, String>> item;

    @RequiresApi(api = Build.VERSION_CODES.N)
    SelectedItem(ArrayList<Value> selectedItem) {
        this.selectedItem = selectedItem;
        verticalValue = new ArrayList<>();
        horizontalValue = new ArrayList<>();

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
                    for (int j = 0; j < horizontalValue.size(); j++) {
                        if (!priceValue.get(j).isEmpty()) {
                            priceMap.put(horizontalValue.get(j), priceValue.get(j));
                        }
                    }
                    item.put(vertical, priceMap);
                }
            }
        }
    }
}
