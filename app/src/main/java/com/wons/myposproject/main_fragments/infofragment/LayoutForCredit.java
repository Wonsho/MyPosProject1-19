package com.wons.myposproject.main_fragments.infofragment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.ItemValueAdapter;
import com.wons.myposproject.layout_class.Info_credit_layout;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class LayoutForCredit {
    Context context;
    Info_credit_layout layout;
    ListView tagListView, creditByTagListView, creditItemList;
    HashMap<String, HashMap<String, ArrayList<BasketTypeItem>>> creditData;
    LayoutForCredit(Context context, Info_credit_layout layout) {
        this.context = context;
        this.layout = layout;
        tagListView = layout.findViewById(R.id.lv_info_creditTag);
        creditByTagListView = layout.findViewById(R.id.lv_info_creditTag_tableNum);
        creditItemList = layout.findViewById(R.id.lv_info_credit_table_item);
        creditData = MainViewModel.getCreditBasketInfo(context);
        setTagList();
        setCreditItem();
        setCreditListByTag();
        onClick();
        initData();
    }


    private void onClick() {
        layout.findViewById(R.id.btn_info_credit_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creditItemList.getAdapter().getCount() != 0 && !(((TextView)layout.findViewById(R.id.tv_date)).getText().toString().isEmpty())) {
                    AlertDialog alertDialog = new InfoDialogUtils().showCheck(context, new InfoDiaLogCallbackForBoolean() {
                        @Override
                        public void callBack(boolean yesOrNo) {
                            if(yesOrNo) {
                                ArrayList<BasketTypeItem> items = ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).getItems();
                                MainViewModel.deleteItemsInDB(items, ((TextView)layout.findViewById(R.id.tv_date)).getText().toString().trim(), context);
                                initData();
                                ((TextView)layout.findViewById(R.id.tv_date)).setText("");
                                ((TextView)layout.findViewById(R.id.tv_tag)).setText("");
                                Toast.makeText(context, "외상기록이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.show();
                } else {
                    Toast.makeText(context, "항목이 없습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)layout.findViewById(R.id.tv_tag)).setText(((TextView)view.findViewById(R.id.tv_value_in_list)).getText().toString().trim());
                getCreditByTagListViewData(((TextView)view.findViewById(R.id.tv_value_in_list)).getText().toString().trim());
                ((TextView)layout.findViewById(R.id.tv_date)).setText("");
                ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).itemClear();
                setCreditItem();
            }
        });

        creditByTagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)layout.findViewById(R.id.tv_date)).setText(((TextView)view.findViewById(R.id.tv_value_in_list)).getText().toString().trim());
                ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).itemClear();
                getCreditItems();
            }
        });
    }


    private void initData() {
        ((ItemValueAdapter)tagListView.getAdapter()).setValue(MainViewModel.getBasketTags(context));
        setTagList();
        ((ItemValueAdapter)creditByTagListView.getAdapter()).setValue(new ArrayList<>());
        ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).itemClear();
        setCreditListByTag();
        setCreditItem();
    }

    private void getCreditByTagListViewData(String tag) {
        ArrayList<String> dateArr = MainViewModel.getDateByTag(context, tag);
        ((ItemValueAdapter)creditByTagListView.getAdapter()).setValue(dateArr);
        setCreditListByTag();
    }

    private void getCreditItems() {
        ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).addItem(creditData.get(((TextView)layout.findViewById(R.id.tv_tag)).getText().toString()).get(((TextView)layout.findViewById(R.id.tv_date)).getText().toString()));
        setCreditItem();
    }
    //todo 선택한 값 추가 하기
    private void setTagList() {
        if(tagListView.getAdapter() == null) {
            tagListView.setAdapter(new ItemValueAdapter());
        }
        ((TextView)layout.findViewById(R.id.tv_price)).setText("0");
        ((ItemValueAdapter)tagListView.getAdapter()).notifyDataSetChanged();
    }

    private void setCreditListByTag() {
        if(creditByTagListView.getAdapter() == null) {
            creditByTagListView.setAdapter(new ItemValueAdapter());
        }
        ((TextView)layout.findViewById(R.id.tv_price)).setText("0");
        ((ItemValueAdapter)creditByTagListView.getAdapter()).notifyDataSetChanged();
    }
    private void setCreditItem() {
        if(creditItemList.getAdapter() == null) {
            creditItemList.setAdapter(new Basket_BarcodeList_Adapter());
        }
        ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).notifyDataSetChanged();
        DecimalFormat format = new DecimalFormat("###,###,###.##");
        double price = 0;
        ArrayList<BasketTypeItem> items = ((Basket_BarcodeList_Adapter)creditItemList.getAdapter()).getItems();
        for(BasketTypeItem item : items) {
            price += Double.parseDouble(item.unitPrice) * Double.parseDouble(item.quantity);
        }
        ((TextView)layout.findViewById(R.id.tv_price)).setText(format.format(price));
    }

}