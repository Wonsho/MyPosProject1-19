package com.wons.myposproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wons.myposproject.itemvalues.Value;

@Dao
public interface MyDao {

    @Insert
    void InsertValueData(Value value);

    @Delete
    void deleteValueData(Value value);

    @Update
    void upDataValueData(Value value);

    @Query("SELECT * FROM Value WHERE itemCode = :valueCode")
    Value getValueData(String valueCode);
}
