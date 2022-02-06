package com.wons.myposproject.main_fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosItemFragment extends Fragment implements Pos {

    FragmentPosItemBinding binding;
    private final String TAG = "PosItemFragment";
    private final int FROM_ON_CREATE = 0;
    private final int FROM_OTHER = 1;
    private final int ACTION_CODE_INSERT = 1;
    private final int ACTION_CODE_DELETE_SELECTED = 0;
    private final int ACTION_CODE_DELETE_ALL = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);
        setItemMenuListView();
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                changeView(SHOW_BARCODE);
                binding.tvItemTitle.setText("Item");
                barcodeScan();
                showLog("tvBarcodeLikeBtn + onc");
            }
        });
        if (((MainViewModel.getLiveDataBasketList()).size()) != 0) {
            for (BasketItem item : MainViewModel.getLiveDataBasketList()) {
                setBasketView(item, FROM_ON_CREATE);
            }
        }
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
        binding.layoutBasket.findViewById(R.id.tv_clearList_likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = DialogForBoolean.dialogForYesOrNo("전부 삭제하겠습니까?", null, getActivity(), new DialogCallback() {
                    @Override
                    public void onResult(Object value) {
                        if (value.equals(true)) {
                            Toast.makeText(getActivity(), "모두 삭제되었습ㄴ니다", Toast.LENGTH_SHORT).show();
                            changedBasketData(-1, ACTION_CODE_DELETE_ALL);
                        }
                    }
                }, "예", "아니요");
                alertDialog.show();
            }
        });
        ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = DialogForBoolean.dialogForYesOrNo("삭제하겠습니까?", null, getActivity(), new DialogCallback() {
                    @Override
                    public void onResult(Object value) {
                        if (value.equals(true)) {
                            Toast.makeText(getActivity(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            changedBasketData(position, ACTION_CODE_DELETE_SELECTED);
                        }
                    }
                }, "예", "아니요");
                alertDialog.show();

                return false;
            }
        });
        binding.layoutBasket.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "눌림", Toast.LENGTH_SHORT).show();

            }
        });
        binding.layoutBasket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "눌림", Toast.LENGTH_SHORT).show();

            }
        });
        binding.layoutBasket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "눌림", Toast.LENGTH_SHORT).show();
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
        setBarcodeItemList(null, -1);
        ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).clearItemList();
        ((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void setBarcodeItemList(BarCodeItem item, int quantity) {
        ListView lv = binding.lvInPosInBarcode;
        if (lv.getAdapter() == null) {
            showLog("setBarcodeItemList + if (lv.getAdapter() == null)");
            lv.setAdapter(new BasKetListAdapter());
        }
        if (item == null) {
            showLog("setBarcodeItemList + if (item == null)");
            return;
        }
        BasketItem basketItem = null;
        int sameItemIndexCheck = ((BasKetListAdapter) lv.getAdapter()).checkSameItem(item.name);
        showLog(String.valueOf(sameItemIndexCheck));

        if (sameItemIndexCheck == -1) {
            showLog("if (sameItemIndexCheck != -1)");
            basketItem = new BasketItem(item.name, null, item.unitPrice, String.valueOf(quantity));
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
            setBasketView(item, FROM_OTHER);
        }
        if ((((BasKetListAdapter) binding.lvInPosInBarcode.getAdapter()).getCount()) != 0) {
            Toast.makeText(getContext(), "바구니에 담았습니다", Toast.LENGTH_SHORT).show();
            binding.drawer.openDrawer(Gravity.RIGHT);
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
//            binding.tvItemTitle.setText("Item"); // 완성 되면 주석 해제
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
        binding.drawer.openDrawer(Gravity.RIGHT);
        setBasketView(item, FROM_OTHER);
        clearItemListView();
    }

    @Override
    public void setBasketView(BasketItem item, int from) {
        ListView lv = binding.layoutBasket.findViewById(R.id.lv_basket);
        if (lv.getAdapter() == null) {
            lv.setAdapter(new BasKetListAdapter());
        }
        if (item == null) {
            return;
        }
        ((BasKetListAdapter) lv.getAdapter()).addItem(item);
        ((BasKetListAdapter) lv.getAdapter()).notifyDataSetChanged();
        if (!(from == FROM_OTHER)) {
        } else {
            changeLiveData(item, ACTION_CODE_INSERT);
        }
    }

    public void changedBasketData(int index, int actionCode) {

        if (((BasKetListAdapter) ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter()) == null)
            return;
        switch (actionCode) {
            case ACTION_CODE_DELETE_ALL: {
                ((BasKetListAdapter) ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter()).clearItemList();
                ((BasKetListAdapter) ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter()).notifyDataSetChanged();
                changeLiveData(null, ACTION_CODE_DELETE_ALL);
                break;
            }
            case ACTION_CODE_INSERT: {

                break;
            }
            case ACTION_CODE_DELETE_SELECTED: {
                ((BasKetListAdapter) ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter()).deleteItem(index);
                ((BasKetListAdapter) ((ListView) binding.layoutBasket.findViewById(R.id.lv_basket)).getAdapter()).notifyDataSetChanged();
                changeLiveData(null, ACTION_CODE_DELETE_SELECTED);
                break;
            }
        }
    }

    public void changeLiveData(BasketItem item, int actionCode) {
        switch (actionCode) {
            case ACTION_CODE_INSERT: {
                ArrayList<BasketItem> basketItemArrayList = MainViewModel.getLiveDataBasketList();
                basketItemArrayList.add(item);
                MainViewModel.setLiveDataBasketList(basketItemArrayList);
                break;
            }
            case ACTION_CODE_DELETE_SELECTED: {

                break;
            }

            case ACTION_CODE_DELETE_ALL: {

                break;
            }
        }
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
        Log.e("Pos", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Pos", "onActivityResult + if (result.getContents() == null)");
                setBarcodeItemList(null, -1);
            } else {
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
                BarCodeItem item = getBasketItemInDB(result.getContents());
                if (item == null) {
                    Toast.makeText(getContext(), "해당하는 품목이 없습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog alertDialog = DialogUtils.getOnlyCheckQuantity(getActivity(), new DialogCallback() {
                    @Override
                    public void onResult(Object value) {
                        Log.e("Pos", "onActivityResult + before setBarcodeItemList");
                        setBarcodeItemList(item, Integer.parseInt((String) value));
                    }
                });
                alertDialog.show();
            }
        } else {
            Log.e("Pos", "onActivityResult6");
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

    void setBarcodeItemList(BarCodeItem item, int quantity);

    void onClickAddButtonInBarCodeList();


    //아이템 리스트 항목을 누를시
    void clearItemListView();

    void setVerticalView(Value value);

    void setHorizontalView(Value value);

    void onClickAddItemButton();

    //최종 공통
    void setBasketView(BasketItem item, int from); //아이템 넣어주기

    void soldToCredit();

    void printReceipt();

    void sold();

    //아이템 데이터 조회
    BarCodeItem getBasketItemInDB(String code);

    int makeBasket(String date, String soldCode, String time);

    void insertItemInBasket(int basketCode);

    Value getValue(String code);
}