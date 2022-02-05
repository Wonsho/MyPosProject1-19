package com.wons.myposproject.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BasKetListAdapter;
import com.wons.myposproject.adapter.MyExpandableAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Item;
import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.pos_value.BasketItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosItemFragment extends Fragment implements Pos{

    FragmentPosItemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =  FragmentPosItemBinding.inflate(inflater, container, false);
       setItemMenuListView();
       binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               changeView(SHOW_BARCODE);
               BasketItem item = getBasketItemInDB(barcodeScan());
               setBarcodeItemList(item);
           }
       });





       return binding.getRoot();
    }

    @Override
    public void setItemMenuListView() {
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
                changeView(SHOW_ITEM);
                return false;
            }
        });
    }

    @Override
    public void changeView(int actionCode) {
        switch (actionCode) {
            case SHOW_BARCODE: {
                if(binding.layoutBarcode.getVisibility() == View.VISIBLE) break;
                binding.layoutItem.setVisibility(View.GONE);
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                break;
            }
            case SHOW_ITEM: {
                if (binding.layoutItem.getVisibility() == View.VISIBLE) break;
                binding.layoutItem.setVisibility(View.VISIBLE);
                binding.layoutBarcode.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public String barcodeScan() {
        String code = null;
        //todo 바코드 스캐너 생성
        return code;
    }

    @Override
    public void clearBarcodeView() {
        //todo 바코드 아이템 리스트뷰 전부 삭제
    }

    @Override
    public void setBarcodeItemList(BasketItem item) {
        if(item == null) {
            Toast.makeText(getContext(), "해당 값이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        ListView lv = binding.lvInPosInBarcode;
        if(lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        }
        ((BasKetListAdapter)lv.getAdapter()).addItem(item);
        ((BasKetListAdapter)lv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onClickAddButtonInBarCodeList() {

    }

    @Override
    public void clearItemListView() {

    }

    @Override
    public void setVerticalView() {

    }

    @Override
    public void HorizontalView() {

    }

    @Override
    public void onClickAddItemButton() {

    }

    @Override
    public void setBasketView(BasketItem item) {

    }

    @Override
    public void soldToCredit() {

    }

    @Override
    public void printReceipt() {

    }

    @Override
    public void sold() {

    }

    @Override
    public void insertBasket() {

    }

    @Override
    public Value getValue(String code) {
        return null;
    }

    public BasketItem getBasketItemInDB(String code) {

        return null;
    }


//    private void setItemMenuListView() {
//        MyExpandableAdapter adapter = new MyExpandableAdapter();
//        ExpandableListView expandableListView =binding.lvExpandable;
//        expandableListView.setAdapter(adapter);
//        ArrayList<Group> groupArrayList = new ArrayList<>(Arrays.asList(Group.values()));
//        adapter.putGroupList(groupArrayList);
//        adapter.notifyDataSetChanged();
//        for(int i=0; i < adapter.getGroupCount() ; i++) {
//            expandableListView.expandGroup(i);
//        }
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                doChangeView(SHOW_ITEM);
//                return false;
//            }
//        });
//    }

//    private void setVerticalValueList() {
//
//    }
//
//    private void setHorizontalValueList() {
//
//    }

//    private void setBasketListView(BasketItem item) {
//        ListView lv = (ListView) binding.layoutBasket.findViewById(R.id.lv_basket);
//        if(lv.getAdapter() == null) {
//            BasKetListAdapter adapter = new BasKetListAdapter();
//            lv.setAdapter(adapter);
//        }
//        BasKetListAdapter adapter = ((BasKetListAdapter)((ListView)binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter());
//        adapter.addItem(item);
//        adapter.notifyDataSetChanged();
//    }

//    private void doChangeView(int i) {
//        switch (i) {
//            case SHOW_BARCODE: {
//                binding.layoutItem.setVisibility(View.GONE);
//                binding.layoutBarcode.setVisibility(View.VISIBLE);
//                break;
//            }
//            case SHOW_ITEM: {
//                binding.layoutItem.setVisibility(View.VISIBLE);
//                binding.layoutBarcode.setVisibility(View.GONE);
//                break;
//            }
//        }
//    }
}

interface Pos {
    final static int SHOW_BARCODE = 0;
    final static int SHOW_ITEM = 1;
    void setItemMenuListView();
    void changeView(int actionCode);

    //바코드 스캔 버튼을 누를시
    String barcodeScan();
    void clearBarcodeView();
    void setBarcodeItemList(BasketItem item);
    void onClickAddButtonInBarCodeList();


    //아이템 리스트 항목을 누를시
    void clearItemListView();
    void setVerticalView();
    void HorizontalView();
    void onClickAddItemButton();

    //최종 공통
    void setBasketView(BasketItem item); //아이템 넣어주기

    void soldToCredit();
    void printReceipt();
    void sold();

    //아이템 데이터 조회
    void insertBasket();
    Value getValue(String code);
}