package com.wons.myposproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;
import com.wons.myposproject.schedule.Schedule;

@Dao
public interface MyDao {

    @Insert
    void insertValueData(Value value);

    @Insert
    void insertSchedule(Schedule schedule);

    @Insert
    void insertBarcodeItem(BarCodeItem barCodeItem);

    @Delete
    void deleteBarcodeItem(BarCodeItem barCodeItem);
    @Delete
    void deleteValueData(Value value);
    @Delete
    void deleteSchedule(Schedule schedule);

    @Update
    void upDataBarcodeItem(BarCodeItem barCodeItem);
    @Update
    void upDataValueData(Value value);
    @Update
    void upDataSchedule(Schedule schedule);


    @Query("SELECT * FROM Barcodeitem WHERE barCode = :barcode")
    BarCodeItem getBarcodeItem(String barcode);

    @Query("SELECT * FROM Value WHERE itemCode = :itemCode")
    Value[] getValueData(String itemCode);

    @Query("SELECT * FROM SCHEDULE WHERE date = :date")
    Schedule[] getSelectedScheduleList(String date);

    @Query("SELECT * FROM BarCodeItem")
    BarCodeItem[] getBarcodeItemList();
}
