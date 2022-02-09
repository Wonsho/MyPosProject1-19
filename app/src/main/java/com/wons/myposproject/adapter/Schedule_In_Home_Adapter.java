package com.wons.myposproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;
import com.wons.myposproject.schedule.Schedule;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Schedule_In_Home_Adapter extends BaseAdapter {

    private ArrayList<Schedule> schedules;

    public Schedule_In_Home_Adapter() {
        schedules = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Schedule getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.schedule_list_view, parent, false);
        }
        TextView tv_schedule = convertView.findViewById(R.id.tv_schedule_in_scheduleList);

        Schedule schedule = schedules.get(position);
        tv_schedule.setText(schedule.schedule_of_date);
        return convertView;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

}
