package com.wons.myposproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;
import com.wons.myposproject.main_fragments.posfregment.BarCodeItem;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.SoldBasket;
import com.wons.myposproject.schedule.Schedule;


@Database(entities = {Value.class, Schedule.class, BasketTypeItem.class, SoldBasket.class, BarCodeItem.class}, version = 1)
abstract class MainDataBase extends RoomDatabase {
     abstract MyDao getDao();
}
