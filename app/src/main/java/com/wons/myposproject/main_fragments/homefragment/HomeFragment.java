package com.wons.myposproject.main_fragments.homefragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;


import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.adapter.Schedule_In_Home_Adapter;
import com.wons.myposproject.databinding.FragmentHomeBinding;
import com.wons.myposproject.schedule.Schedule;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        Format format = new SimpleDateFormat("yyyy / M / d");
        String date = format.format(binding.calenderView.getDate());
        SetScheduleListView setScheduleListView = new SetScheduleListView();
        setScheduleListView.setListView(getActivity(), date, binding.lvSchedule);
        setText(date);

        binding.calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + " / " + (month+1) + " / " + dayOfMonth;
                setScheduleListView.setListView(getActivity(), date, binding.lvSchedule);
                setText(date);
            }
        });
        binding.layoutAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = HomeDialogUtils.getAlertDialogWhenAddedSchedule(getActivity(), new HomeDialogCallback() {
                    @Override
                    public void onResult(String msg) {
                        Schedule schedule = new Schedule();
                        schedule.schedule_of_date = msg;
                        schedule.date = binding.tvScheduleValue.getText().toString();
                        setScheduleListView.changeListViewData(getActivity(), schedule, SetScheduleListView.ACTION_CODE_INSERT, binding.lvSchedule);
                        Toast.makeText(getActivity(), "?????? ???????????????", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResultBoolean(boolean yesOrNo) { return;}
                }, binding);
                alertDialog.show();
            }
        });

        binding.lvSchedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule = ((Schedule_In_Home_Adapter)(binding.lvSchedule.getAdapter())).getItem(position);
                AlertDialog alertDialog = HomeDialogUtils.getDeleteDialog(getActivity(), new HomeDialogCallback() {
                    @Override
                    public void onResult(String smg) { return;}
                    @Override
                    public void onResultBoolean(boolean yesOrNo) {
                        if(yesOrNo) {
                            Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                            setScheduleListView.changeListViewData(getContext(), schedule, SetScheduleListView.ACTION_CODE_DELETE, binding.lvSchedule);
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });
        return binding.getRoot();
    }

    private void setText(String date) {
        binding.tvScheduleValue.setText(date);
    }
}




final class SetScheduleListView {
    static final int ACTION_CODE_INSERT = 0;
    static final int ACTION_CODE_DELETE = 1;
    private Context context;
    private ListView lv;
    public void setListView(Context context, String date, ListView lv) {
        this.context = context;
        this.lv = lv;
        getScheduleOnRoomDB(date);
    }
    public void changeListViewData(Context context, Schedule schedule, int actionCode, ListView lv) {
        this.context = context;
        changeScheduleInDB(schedule, actionCode);
    }

    private void changeScheduleInDB(Schedule schedule, int changeCode) {
        switch (changeCode) {
            case ACTION_CODE_DELETE: {
                MainViewModel.deleteSchedule(context, schedule);
                getScheduleOnRoomDB(schedule.date);
                break;
            }
            case ACTION_CODE_INSERT: {
                MainViewModel.insertSchedule(context, schedule);
                getScheduleOnRoomDB(schedule.date);
                break;
            }
        }
    }
    private void setListView(ArrayList<Schedule> scheduleArrayList) {
        if(lv.getAdapter() == null) {
            lv.setAdapter(new Schedule_In_Home_Adapter());
        }
        ((Schedule_In_Home_Adapter) lv.getAdapter()).setSchedules(scheduleArrayList);
        ((Schedule_In_Home_Adapter) lv.getAdapter()).notifyDataSetChanged();
    }
    private void getScheduleOnRoomDB(String date) {
        setListView(new ArrayList<>(Arrays.asList(MainViewModel.getSchedule(context, date))));
    }
}
class SetWeatherView {
    // TODO: 2022-02-07 ????????? ??????!
}
