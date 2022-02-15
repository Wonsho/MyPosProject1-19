package com.wons.myposproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;

import java.util.ArrayList;

public class ItemValueAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    public ItemValueAdapter() {
        arrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
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
            convertView = inflater.inflate(R.layout.value_view_in_list, parent, false);
        }
        if(arrayList.get(position).contains("_")) {
            ((TextView)convertView.findViewById(R.id.tv_value_in_list)).setText(arrayList.get(position).replace("_","/"));
        } else {
            ((TextView)convertView.findViewById(R.id.tv_value_in_list)).setText(arrayList.get(position));
        }
        return convertView;
    }

    public void setValue(ArrayList<String> str) {
        this.arrayList = str;
    }
}
