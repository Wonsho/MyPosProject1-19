package com.wons.myposproject.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> parents;
    private HashMap<String, ArrayList<String>> children;

    public MyExpandableAdapter() {
        parents = new ArrayList<>();
        children = new HashMap<>();
    }
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void putGroupList(ArrayList<Group> groupLists) {
        for(Group group : groupLists) {
            parents.add(group.getKoreanName());
            ArrayList<String> child = setChildren(group);
            children.put(group.getKoreanName(), child);

        }
    }

    private ArrayList<String> setChildren(Group group) {
        if(children == null) {
            children = new HashMap<>();
        }
        ArrayList<String> child = new ArrayList<>();

        for(int i=0; i<group.getItemList().size(); i++) {
            Item itemList = group.getItemList().get(i);
            child.add(itemList.koreanName);
        }
        return child;
    }
}
