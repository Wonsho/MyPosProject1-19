package com.wons.myposproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;
import com.wons.myposproject.pos_value.SoldBasket;
import com.wons.myposproject.schedule.Schedule;


@Database(entities = {Value.class, Schedule.class, BasketItem.class, SoldBasket.class, BarCodeItem.class}, version = 1)
abstract class MainDataBase extends RoomDatabase {
     abstract MyDao getDao();
}
