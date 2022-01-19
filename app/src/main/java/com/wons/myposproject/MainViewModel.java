package com.wons.myposproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

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
