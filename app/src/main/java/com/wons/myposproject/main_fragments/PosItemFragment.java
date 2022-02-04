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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosItemFragment extends Fragment {

    final int SHOW_BARCODE = 0;
    final int SHOW_ITEM = 1;

    FragmentPosItemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =  FragmentPosItemBinding.inflate(inflater, container, false);
       setExpandableList();

       binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               doChangeView(SHOW_BARCODE);
           }
       });

       return binding.getRoot();
    }


    private void setExpandableList() {
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        ExpandableListView expandableListView =binding.lvExpandable;
        expandableListView.setAdapter(adapter);
        ArrayList<Group> groupArrayList = new ArrayList<>(Arrays.asList(Group.values()));
        adapter.putGroupList(groupArrayList);
        adapter.notifyDataSetChanged();
        for(int i=0; i < adapter.getGroupCount() ; i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                doChangeView(SHOW_ITEM);
                return false;
            }
        });
    }

    private void setVerticalValueList() {

    }

    private void setHorizontalValueList() {

    }

    private void doChangeView(int i) {
        if(SHOW_BARCODE == i) {
            binding.layoutItem.setVisibility(View.GONE);
            binding.layoutBarcode.setVisibility(View.VISIBLE);
        }
        if(SHOW_ITEM == i) {
            binding.layoutItem.setVisibility(View.VISIBLE);
            binding.layoutBarcode.setVisibility(View.GONE);
        }
    }


}