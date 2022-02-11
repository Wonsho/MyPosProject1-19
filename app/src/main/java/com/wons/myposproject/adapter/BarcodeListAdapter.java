package com.wons.myposproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TintableCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BarcodeListAdapter extends BaseAdapter {
    public BarcodeListAdapter() {
        this.items = new ArrayList<>();
    }

    private ArrayList<BarCodeItem> items;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.in_barcode_searched_item_view, parent, false);
        }
        TextView tv_itemName = convertView.findViewById(R.id.tv_itemName);
        TextView tv_itemUnitPrice = convertView.findViewById(R.id.tv_itemUnitPrice);
        tv_itemName.setText(items.get(position).name);
        DecimalFormat df = new DecimalFormat("###,###,###");
        tv_itemUnitPrice.setText(df.format(Integer.parseInt(items.get(position).unitPrice)));
        return convertView;
    }

    public ArrayList<BarCodeItem> getItems() {
        return this.items;
    }

    public void deleteItem(int index) {
        this.items.remove(index);
    }

    public void setItems(ArrayList<BarCodeItem> items) {
        this.items = items;
    }

    public void clearItem() {
        this.items = new ArrayList<>();
    }

}
