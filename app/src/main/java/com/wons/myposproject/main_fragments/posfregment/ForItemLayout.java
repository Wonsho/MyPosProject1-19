package com.wons.myposproject.main_fragments.posfregment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.ItemValueAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Item;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.SelectedItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class ForItemLayout{
    Context context;
    FragmentPosItemBinding binding;
    LinearLayout layout;
    ListView verticalList;
    ListView horizontalList;
    TextView tv_selectedFromMenu;
    TextView tv_verticalValue;
    TextView tv_horizontalValue;
    TextView tv_selectedVerticalValueInList;
    TextView tv_selectedHorizontalValueInList;
    TextView tv_selectedVerticalInUnitPrice;
    TextView tv_selectedHorizontalInUnitPrice;
    TextView tv_unitPrice;
    HashMap<String, HashMap<String, String>> selectedItem;
    SelectedItem itemValueClass;

    ForItemLayout(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
        this.layout = binding.layoutItem;
        verticalList = layout.findViewById(R.id.lv_vertical);
        horizontalList = layout.findViewById(R.id.lv_horizontal);
        tv_selectedFromMenu = binding.tvItemTitle;
        tv_verticalValue = layout.findViewById(R.id.tv_vertical);
        tv_horizontalValue = layout.findViewById(R.id.tv_horizontal);
        tv_selectedVerticalValueInList = layout.findViewById(R.id.tv_selectedVerticalValue);
        tv_selectedHorizontalValueInList = layout.findViewById(R.id.tv_selectedHorizontalValue);
        tv_selectedHorizontalInUnitPrice = layout.findViewById(R.id.tv_selectedHorizontalInUnitPrice);
        tv_unitPrice = layout.findViewById(R.id.tv_selectedUnitPrice);
        tv_selectedVerticalInUnitPrice = layout.findViewById(R.id.tv_selectedVerticalInUnitPrice);
        onClick();
    }


    private void onClick() {
        //todo 선택한 물건 바구니에 담기
        layout.findViewById(R.id.btn_add_in_pos_to_basket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //todo 세로값 선택하는 리스트 온클릭
        verticalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String verticalValue = (String) ((ItemValueAdapter) verticalList.getAdapter()).getItem(position);
                setHorizontalListView(verticalValue);
                if (verticalValue.contains("_")) {
                    verticalValue = verticalValue.replace("_", "/");
                }
                tv_selectedHorizontalValueInList.setText("");
                tv_selectedHorizontalInUnitPrice.setText("");
                tv_unitPrice.setText("");
                tv_selectedVerticalValueInList.setText(verticalValue);
                tv_selectedVerticalInUnitPrice.setText(verticalValue);
            }
        });

        //todo 가로값 선택하는 리스트 온클릭

        horizontalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String horizontalValue = (String) ((ItemValueAdapter) horizontalList.getAdapter()).getItem(position);
                String verticalValue = tv_selectedVerticalValueInList.getText().toString();
                if (verticalValue.contains("/")) {
                    verticalValue = verticalValue.replace("/", "_");
                }
                tv_unitPrice.setText(selectedItem.get(verticalValue).get(horizontalValue));
                if (horizontalValue.contains("_")) {
                    horizontalValue = horizontalValue.replace("_", "/");
                }
                tv_selectedHorizontalValueInList.setText(horizontalValue);
                tv_selectedHorizontalInUnitPrice.setText(horizontalValue);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getSelectedItemNameFromMain(Item item) {
        ArrayList<Value> items = MainViewModel.getSelectedValue(context, item.itemCode);
        this.itemValueClass = SelectedItem.getSelectedItem(items);
        this.selectedItem = SelectedItem.item;
        setVerticalListView();
        ((TextView)binding.layoutItem.findViewById(R.id.tv_selectedItemName)).setText(item.koreanName);
        tv_selectedFromMenu.setText(item.koreanName);
        tv_verticalValue.setText(item.verticalValue);
        tv_horizontalValue.setText(item.horizontalValue);
        ((TextView) layout.findViewById(R.id.tv_verticalInfo)).setText(item.verticalValue + " : ");
        ((TextView) layout.findViewById(R.id.tv_horizontalInfo)).setText(item.horizontalValue + " : ");
    }

    private void setVerticalListView() {
        if (verticalList.getAdapter() == null) {
            verticalList.setAdapter(new ItemValueAdapter());
        }
        ((ItemValueAdapter) verticalList.getAdapter()).setValue(SelectedItem.verticalValue);
        ((ItemValueAdapter) verticalList.getAdapter()).notifyDataSetChanged();
    }

    private void setHorizontalListView(String verticalValue) {
        if (horizontalList.getAdapter() == null) {
            horizontalList.setAdapter(new ItemValueAdapter());
        }
        Log.e("setHorizontalListView", verticalValue);
        ((ItemValueAdapter) horizontalList.getAdapter()).setValue(SelectedItem.valueOfKey.get(verticalValue));
        ((ItemValueAdapter) horizontalList.getAdapter()).notifyDataSetChanged();
    }

    public void setAllViewInit() {
        if (horizontalList.getAdapter() == null) {
            horizontalList.setAdapter(new ItemValueAdapter());
        }
        ((ItemValueAdapter) horizontalList.getAdapter()).setValue(new ArrayList<>());
        ((ItemValueAdapter) horizontalList.getAdapter()).notifyDataSetChanged();
        tv_selectedHorizontalValueInList.setText("");
        tv_selectedVerticalValueInList.setText("");
        tv_selectedVerticalInUnitPrice.setText("");
        tv_unitPrice.setText("");
        tv_selectedHorizontalInUnitPrice.setText("");
    }
}
