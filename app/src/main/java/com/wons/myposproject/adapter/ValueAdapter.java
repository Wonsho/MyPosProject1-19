package com.wons.myposproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;

import java.util.ArrayList;

public class ValueAdapter extends BaseAdapter {
    private ArrayList<String> value;
    public ValueAdapter() {
        value = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return value.size();
    }

    @Override
    public Object getItem(int position) {
        return value.get(position);
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
        ((TextView)convertView.findViewById(R.id.tv_value_in_list)).setText(value.get(position));
        return convertView;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
}
