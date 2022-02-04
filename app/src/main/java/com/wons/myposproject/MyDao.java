package com.wons.myposproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.schedule.Schedule;

@Dao
public interface MyDao {

    @Insert
    void insertValueData(Value value);

    @Insert
    void insertSchedule(Schedule schedule);

    @Delete
    void deleteValueData(Value value);
    @Delete
    void deleteSchedule(Schedule schedule);

    @Update
    void upDataValueData(Value value);
    @Update
    void upDataSchedule(Schedule schedule);


    @Query("SELECT * FROM Value WHERE itemCode = :valueCode")
    Value getValueData(String valueCode);

    @Query("SELECT * FROM SCHEDULE WHERE date = :date")
    Schedule[] getSelectedScheduleList(String date);
}
