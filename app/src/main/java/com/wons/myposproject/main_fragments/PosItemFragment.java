package com.wons.myposproject.main_fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BasKetListAdapter;
import com.wons.myposproject.adapter.MyExpandableAdapter;
import com.wons.myposproject.adapter.ValueAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Item;
import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;
import com.wons.myposproject.pos_value.BasketSoldCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosItemFragment extends Fragment implements Pos {

    FragmentPosItemBinding binding;
    private final String TAG = "PosItemFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);
        setItemMenuListView();
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(SHOW_BARCODE);
                barcodeScan();
                showLog("tvBarcodeLikeBtn + onc");
            }
        });

       // MainViewModel.insertBarcodeItem(getContext(), "8801121025666", "아몬드 우유", "500");

        binding.btnInPosInBarCodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddButtonInBarCodeList();
            }
        });

        binding.btnAddInPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItemButton();
            }
        });


        return binding.getRoot();
    }


    @Override
    public void setItemMenuListView() {
        MyExpandableAdapter adapter = new MyExpandableAdapter();
        ExpandableListView expandableListView = binding.lvExpandable;
        expandableListView.setAdapter(adapter);
        ArrayList<Group> groupArrayList = new ArrayList<>(Arrays.asList(Group.values()));
        adapter.putGroupList(groupArrayList);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                changeView(SHOW_ITEM);
                binding.tvItemTitle.setText(((TextView) v.findViewById(R.id.tv_childValue)).getText().toString());
                setVerticalView(null); //todo 맞는 데이터 뷰 넣어주기
                return false;
            }
        });
    }

    @Override
    public void changeView(int actionCode) {
        switch (actionCode) {
            case SHOW_BARCODE: {
                if (binding.layoutBarcode.getVisibility() == View.VISIBLE) break;
                binding.layoutItem.setVisibility(View.GONE);
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                clearItemListView();
                break;
            }
            case SHOW_ITEM: {
                if (binding.layoutItem.getVisibility() == View.VISIBLE) break;
                clearBarcodeView();
                binding.layoutItem.setVisibility(View.VISIBLE);
                binding.layoutBarcode.setVisibility(View.GONE);
                break;
            }
        }
    }

    @Override
    public void barcodeScan() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void clearBarcodeView() {
        ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).clearItemList();
        ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void setBarcodeItemList(BarCodeItem item) {
        ListView lv = binding.lvInPosInBarcode;
        if (lv.getAdapter() == null) {
            showLog("setBarcodeItemList + if (lv.getAdapter() == null)");
            lv.setAdapter(new BasKetListAdapter());
        }
        if (item == null) {
            Toast.makeText(getContext(), "해당 값이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        //todo 수량 묻는 다이얼 로그 띄우기

        int quantity = 1;
        BasketItem basketItem = null;
        //todo 스캔하고 수량 입력 and 같은 바코드 찍었을시 중복체크

        int sameItemIndexCheck = ((BasKetListAdapter) lv.getAdapter()).checkSameItem(item.name);
        showLog(String.valueOf(sameItemIndexCheck));

        if (sameItemIndexCheck == -1) {
            showLog("if (sameItemIndexCheck != -1)");
            basketItem = new BasketItem(item.name, null, item.unitPrice, "1");
            ((BasKetListAdapter) lv.getAdapter()).addItem(basketItem);

        } else {
            showLog("else");
            ArrayList<BasketItem> items = ((BasKetListAdapter) lv.getAdapter()).getItems();
            basketItem = items.get((sameItemIndexCheck));
            basketItem.quantity = String.valueOf(Integer.parseInt(basketItem.quantity) + quantity);
            items.set(sameItemIndexCheck, basketItem);
            ((BasKetListAdapter) lv.getAdapter()).setItems(items);
        }
        ((BasKetListAdapter) lv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onClickAddButtonInBarCodeList() {

        for (BasketItem item : ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).getItems()) {
            setBasketView(item);
        }
        if ((((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).getCount()) != 0) {
            Toast.makeText(getContext(), "바구니에 담았습니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "바구니에 담을 아이템이 없습니다", Toast.LENGTH_SHORT).show();
        }
        clearBarcodeView();
    }

    @Override
    public void clearItemListView() {

        //todo VerticalView만 가지고 있기
        setHorizontalView(null);
    }

    @Override
    public void setVerticalView(Value value) {
        ListView lv = binding.lvVertival;
        if (lv.getAdapter() == null) {
            lv.setAdapter(new ValueAdapter());
        }
        if (value == null) {
            ((ValueAdapter) lv.getAdapter()).setValue(new ArrayList<>());
            Toast.makeText(getContext(), "데이터가 없습니다", Toast.LENGTH_SHORT).show();
//            binding.tvItemTitle.setText("Item");
//            binding.layoutItem.setVisibility(View.GONE);
            return;
        }
        ((ValueAdapter) lv.getAdapter()).setValue(value.valueList());
    }

    @Override
    public void setHorizontalView(Value value) {
        ListView lv = binding.lvHorizontal;
        if (lv.getAdapter() == null) {
            lv.setAdapter(new ValueAdapter());
        }
        if (value == null) {
            ((ValueAdapter) lv.getAdapter()).setValue(new ArrayList<>());
            return;
        }
        ((ValueAdapter) lv.getAdapter()).setValue(value.valueList());
    }

    @Override
    public void onClickAddItemButton() {
        BasketItem item = null; // todo 데이터 조회후 BasketItem객체 생성후 전달  (단가, 규격, 품목)
        if (item == null) {
            Toast.makeText(getContext(), "선택을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        setBasketView(item);
        clearItemListView();
    }

    @Override
    public void setBasketView(BasketItem item) {
        ListView lv = binding.layoutBasket.findViewById(R.id.lv_basket);
        if (lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        }
        if (item == null) {
            return;
        }
        ((BasKetListAdapter) lv.getAdapter()).addItem(item);
        ((BasKetListAdapter) lv.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void soldToCredit() {
        //todo 바구니 객체를 만들어서 디비에 넣은후 바구니 데이터의 아이디 값을 가져와서 동일한 코드값으로 db에 저장

        int basketCode = makeBasket("", BasketSoldCode.CREDIT.soldCode, "");
        insertItemInBasket(basketCode);

    }

    @Override
    public void printReceipt() {
        Toast.makeText(getContext(), "아직 구현 안됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sold() {
        int basketCode = makeBasket("", BasketSoldCode.NOT_CREDIT.soldCode, "");
        insertItemInBasket(basketCode);

        //todo 바구니 객체를 만들어서 디비에 넣은후 바구니 데이터의 아이디 값을 가져와서 동일한 코드값으로 db에 저장
    }

    @Override
    public void insertItemInBasket(int basketCode) {
        //todo 바스켓 코드 조회한후 db에 저장
    }

    @Override
    public Value getValue(String code) {
        return null;
    }

    @Override
    public BarCodeItem getBasketItemInDB(String code) {
        return MainViewModel.getBarcodeItem(getContext(), code);
    }

    @Override
    public int makeBasket(String date, String soldCode, String time) {
        int basketCode = 0;
        return basketCode;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("Pos", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                setBarcodeItemList(null);
            } else {
                Toast.makeText(getContext(), "Scanned" + result.getContents(), Toast.LENGTH_SHORT).show();
                BarCodeItem item = getBasketItemInDB(result.getContents());
                setBarcodeItemList(item);
            }
        } else {
            Log.d("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showLog(String msg) {
        Log.e(TAG, msg);
    }
}

interface Pos {
    final static int SHOW_BARCODE = 0;
    final static int SHOW_ITEM = 1;

    void setItemMenuListView();

    void changeView(int actionCode);

    //바코드 스캔 버튼을 누를시
    void barcodeScan();

    void clearBarcodeView();

    void setBarcodeItemList(BarCodeItem item);

    void onClickAddButtonInBarCodeList();


    //아이템 리스트 항목을 누를시
    void clearItemListView();

    void setVerticalView(Value value);

    void setHorizontalView(Value value);

    void onClickAddItemButton();

    //최종 공통
    void setBasketView(BasketItem item); //아이템 넣어주기

    void soldToCredit();

    void printReceipt();

    void sold();

    //아이템 데이터 조회
    BarCodeItem getBasketItemInDB(String code);

    int makeBasket(String date, String soldCode, String time);

    void insertItemInBasket(int basketCode);

    Value getValue(String code);
}