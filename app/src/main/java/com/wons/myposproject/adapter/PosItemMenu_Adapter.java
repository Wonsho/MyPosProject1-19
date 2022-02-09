package com.wons.myposproject.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.wons.myposproject.R;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Group;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class PosItemMenu_Adapter extends BaseExpandableListAdapter {

    private ArrayList<String> parents;
    private HashMap<String, ArrayList<String>> children;

    public PosItemMenu_Adapter() {
        parents = new ArrayList<>();
        children = new HashMap<>();
    }
    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(parents.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(parents.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_parent, parent, false);
        }
        convertView.setContextClickable(true);
        TextView tv_parent = convertView.findViewById(R.id.tv_parentValue);
        tv_parent.setText(parents.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_child, parent, false);
        }

        TextView tv_child = convertView.findViewById(R.id.tv_childValue);
        tv_child.setText(children.get(parents.get(groupPosition)).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
