package com.wons.myposproject.main_fragments.homefragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Insert;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Toast;


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
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        Format format = new SimpleDateFormat("yyyy / M / d");
        String date = format.format(binding.calenderView.getDate());

        binding.calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + " / " + month + " / " + dayOfMonth;

            }
        });
        binding.layoutAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = HomeDialogUtils.getAlertDialogWhenAddedSchedule(getActivity(), new HomeDialogCallback() {
                    @Override
                    public void onResult(Schedule value) {

                    }
                }, binding);
                alertDialog.show();
            }
        });
        return binding.getRoot();
    }


    private static class SetSchedule {
        static final int CHANGE_CODE_INSERT = 0;
        static final int CHANGE_CODE_DELETE = 1;
        private static SetSchedule setSchedule = new SetSchedule();

        public static void setListView(String date) {

        }
        public static void changeListView(String date, int code) {

        }

        private SetSchedule(Schedule schedule, int changeCode) {
        }


        private void changeScheduleInDB(Schedule schedule, int changeCode) {
            switch (changeCode) {
                case CHANGE_CODE_DELETE: {
                    break;
                }
                case CHANGE_CODE_INSERT: {
                    break;
                }
            }
        }
        private void setListView(ArrayList<Schedule> scheduleArrayList) {
            // TODO: 2022-02-07  리스트 뷰의 택스트 날짜 입력
        }
        private void getScheduleOnRoomDB(String date) {
            setListView(new ArrayList<>());
        }
        private SetSchedule() { }
    }
}

