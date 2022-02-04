package com.wons.myposproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;
import androidx.room.Room;

import com.wons.myposproject.schedule.Schedule;

import java.util.HashMap;

// TODO: 2022-02-04 날씨(라이브데이터), 데이터베이스 쿼리, 외상조회
public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private static MainDataBase mainDataBase;
    private static MyDao myDao;

    public static MainDataBase getMainDataBase(Context context) {
        if(mainDataBase == null) {
            Log.d(TAG, "getMainDataBase = database is null");
            mainDataBase = Room.databaseBuilder(context, MainDataBase.class, "mainDataBase").allowMainThreadQueries().build();
            Log.d(TAG, "database build");
        }
        return mainDataBase;
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
}
