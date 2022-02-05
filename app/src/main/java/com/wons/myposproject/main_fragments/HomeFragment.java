package com.wons.myposproject.main_fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;


import com.wons.myposproject.MainActivity;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.adapter.ScheduleAdapter;
import com.wons.myposproject.databinding.FragmentHomeBinding;

import com.wons.myposproject.schedule.Schedule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    private final int INSERT = 0;
    private final int DELETE = 1;
    private final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showDidLog("onCreateView");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        getCalenderViewDate();
        getTodayData();

        binding.layoutAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDidLog("layoutAddSchedule + onClick");
                DialogCallback callback = new DialogCallback() {
                    @Override
                    public void onResult(Schedule value) {
                        changeData(value, INSERT);
                        setListView(value.date);
                    }
                };
                AlertDialog alertDialog = DialogUtils.getAlertDialogWhenAddedSchedule(getActivity(), callback, binding);
                alertDialog.show();
            }
        });

        binding.lvSchedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule = (Schedule) parent.getAdapter().getItem(position);
                DialogCallback callback = new DialogCallback() {
                    @Override
                    public void onResult(Schedule value) {
                        changeData(value, DELETE);
                        setListView(value.date);
                    }
                };
                AlertDialog alertDialog = DialogUtils.getDeleteDialog(getActivity(), callback , schedule);
                alertDialog.show();
                return false;
            }
        });
        return binding.getRoot();
    }


    private void getCalenderViewDate() {
        showDidLog("getCalenderViewDate");
        binding.calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                showDidLog("getCalenderViewDate : onSelectedDayChange");
                String date = String.valueOf(year) + " / " + (month + 1) + " / " + dayOfMonth;
                binding.tvScheduleValue.setText(date);
                setListView(date);
            }
        });
    }

    private void getTodayData() {
        showDidLog("getTodayData");
        Format format = new SimpleDateFormat("yyyy / M / d");
        String date = format.format(binding.calenderView.getDate());
        binding.tvScheduleValue.setText(date);
        setListView(date);
    }

    private Schedule[] getData(String date) {
        showDidLog("getData");
        return MainViewModel.getSchedule(getContext(), date);
    }

    private void changeData(Schedule schedule, int code) {
        showDidLog("changeData");
        switch (code) {
            case DELETE: {
                showDidLog("changeData+DELETE");
                MainViewModel.deleteSchedule(getContext(), schedule);
                break;
            }
            case INSERT: {
                showDidLog("changeData+INSERT");
                MainViewModel.insertSchedule(getContext(), schedule);
                break;
            }
        }
        getData(schedule.date);
    }

    private void setListView(String date) {
        showDidLog("setListView");
        if(binding.lvSchedule.getAdapter() == null) {
            ScheduleAdapter adapter = new ScheduleAdapter();
            binding.lvSchedule.setAdapter(adapter);
        }
        ((ScheduleAdapter)binding.lvSchedule.getAdapter()).setSchedules(new ArrayList<>(Arrays.asList(getData(date))));
        ((ScheduleAdapter)binding.lvSchedule.getAdapter()).notifyDataSetChanged();

    }

//    private AlertDialog getDeleteDialog(Context context, Schedule schedule) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                changeData(schedule, DELETE);
//                setListView(schedule.date);
//            }
//        });
//        builder.setMessage("선택한 스케쥴을 삭제하시겠습니까?");
//        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        return builder.create();
//    }

    private void showDidLog(String msg) {
        Log.d(MainActivity.TAG_DID,TAG +" : "+ msg);
    }
}