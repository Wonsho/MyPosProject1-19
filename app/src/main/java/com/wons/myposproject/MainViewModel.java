package com.wons.myposproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

// TODO: 2022-02-04 날씨, 데이터베이스 쿼리, 스케쥴, 외상조회 
public class MainViewModel extends ViewModel {
    private static MainViewModel mainViewModel = new MainViewModel();
    private static MutableLiveData<HashMap<String, HashMap<String,HashMap<String, String>>>> boltData;
    private MainViewModel() {
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }
    public MutableLiveData<HashMap<String, HashMap<String,HashMap<String, String>>>> getBoltData() {
        if(boltData == null) {

        }
        return boltData;
    }


}
