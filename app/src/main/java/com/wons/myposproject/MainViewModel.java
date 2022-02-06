package com.wons.myposproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;
import androidx.room.Room;

import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;
import com.wons.myposproject.schedule.Schedule;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: 2022-02-04 날씨(라이브데이터), 데이터베이스 쿼리, 외상조회
public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private static MainDataBase mainDataBase;
    private static MyDao myDao;

    private static MutableLiveData<ArrayList<BasketItem>> liveDataBasketList;

    public static ArrayList<BasketItem> getLiveDataBasketList() {
        if(liveDataBasketList == null) {
            liveDataBasketList = new MutableLiveData<>();
            liveDataBasketList.setValue(new ArrayList<BasketItem>());
        }
        return liveDataBasketList.getValue();
    }

    public static void setLiveDataBasketList(ArrayList<BasketItem> liveDataBasketList) {
        MainViewModel.liveDataBasketList.setValue(liveDataBasketList);
    }

    public static MainDataBase getMainDataBase(Context context) {
        if(mainDataBase == null) {
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

    public static void deleteSchedule(Context context, Schedule schedule) {
        myDao = getMainDataBase(context).getDao();
        myDao.deleteSchedule(schedule);
    }
    public static void insertSchedule(Context context, Schedule schedule) {
        myDao = getMainDataBase(context).getDao();
        myDao.insertSchedule(schedule);
    }

    public static Schedule[] getSchedule(Context context, String date) {
        myDao = getMainDataBase(context).getDao();
        return myDao.getSelectedScheduleList(date);
    }

    public static void insertBarcodeItem(Context context, String barcode, String itemName, String unitPrice) {
        myDao = getMainDataBase(context).getDao();
        myDao.insertBarcodeItem(new BarCodeItem(barcode, itemName, unitPrice));
    }
}
