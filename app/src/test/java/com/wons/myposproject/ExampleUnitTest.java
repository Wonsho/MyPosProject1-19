package com.wons.myposproject;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.wons.myposproject.pos_value.BasketItem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        BasketItem[] originItems = {new BasketItem("음료수1", null, "1000", "15"), new BasketItem("음료수2", null, "1000", "5"), new BasketItem("음료수3", null, "1000", "10")};
        BasketItem[] needCheckItem = {new BasketItem("음료수1", null, "1000", "15"), new BasketItem("음료수3", null, "1000", "10")};
        ArrayList<BasketItem> originArr = (ArrayList<BasketItem>) Arrays.asList(originItems);
        ArrayList<BasketItem> needCheckArr = (ArrayList<BasketItem>) Arrays.asList(needCheckItem);
        ArrayList<BasketItem> valueArr = checkSameNameItem(originArr, needCheckArr);


        System.out.println("결과Array의 사이즈는 "+valueArr.size() + "입니다");
        for(BasketItem item : valueArr) {
            System.out.println(item.itemName);
            System.out.println(item.unitPrice);
            System.out.println(item.quantity);
        }

    }
    ArrayList<BasketItem> checkSameNameItem(ArrayList<BasketItem> originItems, ArrayList<BasketItem> needCheckItems) {
        Log.e("checkSameNameItem","originItems.size() : "+originItems.size() + " needCheckItems.size : " +  needCheckItems.size());
        ArrayList<BasketItem> originBasketItemArrayList = originItems;
        if(originBasketItemArrayList.size() == 0) {
            Log.e("checkSameNameItem", "if(originBasketItemArrayList.size() == 0)" + originBasketItemArrayList.size());
            return needCheckItems;
        }
        for (int i = 0; i < needCheckItems.size(); i++) {
            for (int j = 0; j < originBasketItemArrayList.size(); j++) {
                if (needCheckItems.get(i).itemName.equals(originBasketItemArrayList.get(j).itemName)) {
                    originBasketItemArrayList.get(j).quantity = String.valueOf(Integer.parseInt(originBasketItemArrayList.get(j).quantity) + Integer.parseInt(needCheckItems.get(i).quantity));
                    Log.e("checkSameNameItem1"," String.valueOf(Integer.parseInt(originBasketItemArrayList.get("+j+").quantity) = " +originBasketItemArrayList.get(j).quantity+"  Integer.parseInt(needCheckItems.get("+i+").quantity)) = "+needCheckItems.get(i).quantity);
                } else {
                    originBasketItemArrayList.add(needCheckItems.get(i));
                    Log.e("checkSameNameItem2"," originBasketItemArrayList.add(needCheckItems.get("+i+")) = " + originItems.get(i).quantity);
                }
            }
        }
        return originBasketItemArrayList;
    }

}