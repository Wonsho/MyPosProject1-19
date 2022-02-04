package com.wons.myposproject.schedule;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Schedule {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String date;
    public String schedule_of_date;

}
