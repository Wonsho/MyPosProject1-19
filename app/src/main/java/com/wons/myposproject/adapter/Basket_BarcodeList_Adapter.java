package com.wons.myposproject.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.Basket_Barcode_listView_Code;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Basket_BarcodeList_Adapter extends BaseAdapter {
    private ArrayList<BasketTypeItem> items;

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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_in_layout_basket, parent, false);
        }
        TextView tv_name, tv_standard, tv_unitPrice, tv_quantity;
        BasketTypeItem basketItem = items.get(position);
        tv_name = convertView.findViewById(R.id.tv_itemName);
        tv_name.setText(basketItem.itemName);

        DecimalFormat df = new DecimalFormat("###,###,###.##");
        tv_standard = convertView.findViewById(R.id.tv_itemStandard);
        tv_standard.setText(basketItem.itemStandard);

        tv_quantity = convertView.findViewById(R.id.tv_itemQuantity);
        tv_quantity.setText(df.format(Double.parseDouble(basketItem.quantity.trim())));

        tv_unitPrice = convertView.findViewById(R.id.tv_itemUnitPrice);
        tv_unitPrice.setText(df.format(Double.parseDouble(basketItem.unitPrice.trim())));
        return convertView;
    }

    public ArrayList<BasketTypeItem> getItems() {
        return items;
    }

    public void deleteItem(int i) {
        items.remove(i);
    }

    public void addItem(BasketTypeItem item) {
        sameCheckItem(item);
    }

    public void addItem(ArrayList<BasketTypeItem> items) {
        for (BasketTypeItem item : items) {
            sameCheckItem(item);
        }
    }

    private void sameCheckItem(BasketTypeItem item) {
        if (items.size() == 0) {
            items.add(item);
            Log.e("sameCheckItem","passed");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            Log.e("sameCheckItem-1","passed");
            if (items.get(i).itemName.equals(item.itemName)) {
                Log.e("sameCheckItem2","passed");
                if ((items.get(i).itemStandard == null) && (items.get(i).unitPrice.equals(item.unitPrice))) {
                    setItem(i, item);
                    Log.e("sameCheckItem3","passed");
                    return;
                }
                if(items.get(i).itemStandard == null) {
                    continue;
                }
                if (items.get(i).itemStandard.equals(item.itemStandard)) {
                    setItem(i, item);
                    Log.e("sameCheckItem4","passed");
                    return;
                }
            }
        }
        Log.e("sameCheckItem6","passed");
        items.add(item);
    }

    private void setItem(int i, BasketTypeItem item) {
        BasketTypeItem item1 = items.get(i);
        item1.quantity = String.valueOf(Double.parseDouble(item1.quantity) + (Double.parseDouble(item.quantity)));
        items.set(i, item1);
    }

    public void itemClear() {
        items = new ArrayList<>();
    }
}
