package com.wons.myposproject.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.widget.TintableCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.wons.myposproject.main_fragments.posfregment.itemvalues.BarCodeItem;

import java.util.ArrayList;

public class BarcodeListAdapter extends RecyclerView.Adapter {
    BarcodeListAdapter() {
        this.items = new ArrayList<>();
    }
    ArrayList<BarCodeItem> items;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public ArrayList<BarCodeItem> getItems() {
        return this.items;
    }
    public void deleteItem(int index) {
        this.items.remove(index);
    }

    public void setItems(ArrayList<BarCodeItem> items){
        this.items = items;
    }
    public void clearItem() {
        this.items = new ArrayList<>();
    }
}
