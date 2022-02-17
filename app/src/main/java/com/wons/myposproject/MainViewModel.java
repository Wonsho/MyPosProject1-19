package com.wons.myposproject;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;
import com.wons.myposproject.schedule.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// TODO: 2022-02-04 날씨(라이브데이터), 데이터베이스 쿼리, 외상조회
public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private static MainDataBase mainDataBase;
    private static MyDao myDao;

    private static MutableLiveData<Integer> basketKey;
    private static MutableLiveData<HashMap<Integer, ArrayList<BasketTypeItem>>> basketData;

    public  static void setBasketKey(int keyId) {
        basketKey.setValue(keyId);
    }

    public static int getBasketKey() {
        if (basketKey == null) {
            basketKey = new MutableLiveData<>();
        }
        if (basketKey.getValue() == null) {
            basketKey.setValue(R.id.tv_basket_1);
        }
        return basketKey.getValue();
    }


    public static ArrayList<BasketTypeItem> getLiveDataBasketList() {
        if(basketData == null) {
            basketData = new MutableLiveData<>();
        }
        if(basketData.getValue() == null) {
            basketData.setValue(new HashMap<>());
        }
        if(basketData.getValue().get(getBasketKey()) == null) {
          HashMap itemMap = basketData.getValue();
          itemMap.put(getBasketKey(), new ArrayList<>());
          basketData.setValue(itemMap);
        }
       return basketData.getValue().get(getBasketKey());
    }

    public static void setLiveDataBasketList(ArrayList<BasketTypeItem> itemArrayList) {
        getLiveDataBasketList();
        HashMap itemMap = basketData.getValue();
        itemMap.put(getBasketKey(), itemArrayList);
        basketData.setValue(itemMap);
    }
//    public static ArrayList<BasketTypeItem> getLiveDataBasketList() {
//        if (liveDataBasketList == null) {
//            liveDataBasketList = new MutableLiveData<>();
//            liveDataBasketList.setValue(new ArrayList<BasketTypeItem>());
//        }
//        return liveDataBasketList.getValue();
//    }


//    public static void setLiveDataBasketList(ArrayList<BasketTypeItem> itemArrayList) {
//        liveDataBasketList.setValue(itemArrayList);
//    }


    public static MainDataBase getMainDataBase(Context context) {
        if (mainDataBase == null) {
            Log.d(TAG, "getMainDataBase = database is null");
            mainDataBase = Room.databaseBuilder(context, MainDataBase.class, "mainDataBase").allowMainThreadQueries().build();
            Log.d(TAG, "database build");
        }
        return mainDataBase;
    }

    public static BarCodeItem getBarcodeItem(Context context, String barcode) {
        myDao = getMainDataBase(context).getDao();
        return myDao.getBarcodeItem(barcode);
    }

    public static void insertValue(Context context, Value value) {
        myDao = getMainDataBase(context).getDao();
        myDao.insertValueData(value);
    }

    public static void deleteSchedule(Context context, Schedule schedule) {
        myDao = getMainDataBase(context).getDao();
        myDao.deleteSchedule(schedule);
    }

    public static ArrayList<BarCodeItem> getAllBarcodeItemsInDB(Context context) {
        myDao = getMainDataBase(context).getDao();
        return new ArrayList<>(Arrays.asList(myDao.getBarcodeItemList()));
    }

    public static void insertSchedule(Context context, Schedule schedule) {
        myDao = getMainDataBase(context).getDao();
        myDao.insertSchedule(schedule);
    }

    public static Schedule[] getSchedule(Context context, String date) {
        myDao = getMainDataBase(context).getDao();
        return myDao.getSelectedScheduleList(date);
    }

    public static void upDateBarcodeItem(Context context, BarCodeItem item) {
        myDao = getMainDataBase(context).getDao();
        myDao.upDataBarcodeItem(item);
    }

    public static void deleteBarcodeItem(Context context, BarCodeItem item) {
        myDao = getMainDataBase(context).getDao();
        myDao.deleteBarcodeItem(item);
    }

    public static void insertBarcodeItem(Context context, String barcode, String itemName, String unitPrice) {
        myDao = getMainDataBase(context).getDao();
        myDao.insertBarcodeItem(new BarCodeItem(barcode, itemName, unitPrice));
    }

    public static ArrayList<Value> getSelectedValue(Context context, String itemCode) {
        myDao = getMainDataBase(context).getDao();
        return new ArrayList<Value>(Arrays.asList(myDao.getValueData(itemCode)));
    }
}
