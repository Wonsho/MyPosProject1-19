package com.wons.myposproject.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.wons.myposproject.R;
import com.wons.myposproject.adapter.MyExpandableAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Item;

import java.util.List;

public class PosItemFragment extends Fragment {

    FragmentPosItemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =  FragmentPosItemBinding.inflate(inflater, container, false);
       setExpandableList();

       binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int viewId = R.layout.in_pos_barcode;
               doInflate(viewId);
           }
       });

       return binding.getRoot();
    }


    private void setExpandableList() {
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        ExpandableListView expandableListView =binding.lvExpandable;
        expandableListView.setAdapter(adapter);
        Group[] groups = Group.values();
        Item[] items = Item.values();

        // TODO: 2022-02-04 칠드 터치리스너에 인플레이트 달기
    }

    private void doInflate(int viewId) {
        binding.framelayoutInpositem.removeAllViews();
        this.getLayoutInflater().inflate(viewId, binding.framelayoutInpositem, true);
    }
}