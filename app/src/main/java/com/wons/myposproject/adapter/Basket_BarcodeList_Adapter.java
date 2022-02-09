package com.wons.myposproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Basket_BarcodeList_Adapter extends BaseAdapter {
    private ArrayList<BasketItem> items;

    public Basket_BarcodeList_Adapter() {
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

        DecimalFormat df = new DecimalFormat("###,###,###");
        tv_standard = convertView.findViewById(R.id.tv_itemStandard);
        tv_standard.setText(basketItem.itemStandard);

        tv_quantity = convertView.findViewById(R.id.tv_itemQuantity);
        tv_quantity.setText(df.format(Integer.parseInt(basketItem.quantity.trim())));

        tv_unitPrice = convertView.findViewById(R.id.tv_itemUnitPrice);
        tv_unitPrice.setText(df.format(Integer.parseInt(basketItem.unitPrice.trim())));
        return convertView;
    }

    public ArrayList<BasketItem> getItems() {
        return items;
    }

    public void deleteItem(int i) {
        items.remove(i);
    }

    public void setItems(ArrayList<BasketItem> items) {
        this.items = items;
    }
}
