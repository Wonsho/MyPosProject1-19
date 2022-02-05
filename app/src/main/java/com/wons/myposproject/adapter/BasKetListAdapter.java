package com.wons.myposproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;
import com.wons.myposproject.pos_value.BasketItem;

import java.util.ArrayList;

public class BasKetListAdapter extends BaseAdapter {
    private ArrayList<BasketItem> items;

    public BasKetListAdapter() {
        items = new ArrayList<>();
    }
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
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_in_layout_basket, parent, false);
        }
        TextView tv_name, tv_standard, tv_unitPrice, tv_quantity;
        BasketItem basketItem = items.get(position);
        tv_name = convertView.findViewById(R.id.tv_itemName);
        tv_name.setText(basketItem.itemName);

        tv_standard = convertView.findViewById(R.id.tv_itemStandard);
        tv_standard.setText(basketItem.itemStandard);

        tv_quantity = convertView.findViewById(R.id.tv_itemQuantity);
        tv_quantity.setText(basketItem.quantity);

        tv_unitPrice = convertView.findViewById(R.id.tv_itemUnitPrice);
        tv_unitPrice.setText(basketItem.unitPrice);
        return convertView;
    }

    public ArrayList<BasketItem> getItems() {
        return items;
    }

    public void addItem(BasketItem basketItem) {
        items.add(basketItem);
    }

    public void deleteItem(int i) {
        items.remove(i);
    }

    public void clearItemList() {
        this.items = new ArrayList<>();
    }

    public int checkSameItem(String name){
        int i = 0;
        for(BasketItem item : items) {
            Log.e("Adapter", "checkSameItem: " + item.itemName + name);
            if(item.itemName.equals(name)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void setItems(ArrayList<BasketItem> items) {
        this.items = items;
    }
}
