package com.wons.myposproject.main_fragments.posfregment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.PosItemMenu_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.enums.SoldCode;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.CheckCode;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallback;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogCallbackForBolt;
import com.wons.myposproject.main_fragments.posfregment.dialog_utils.PosDialogUtils;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Group;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Item;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.SelectedItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Value;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PosItemFragment extends Fragment {
    private FragmentPosItemBinding binding;
    public static final String TAG = "PosItemFragment";
    private Fragment fragment;
    private ForBarCodeLayout forBarcodeLayOut;
    private ForBasketLayout forBasketLayout;
    private ForItemLayout forItemLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);
        PosItemMenu_Adapter adapter = new PosItemMenu_Adapter();
        binding.lvExpandable.setAdapter(adapter);
        adapter.putGroupList(new ArrayList<>(Arrays.asList(Group.values())));
        adapter.notifyDataSetChanged();
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.lvExpandable.expandGroup(i);
        }
        fragment = this;
        forBarcodeLayOut = new ForBarCodeLayout(getContext(), binding);
        forBasketLayout = new ForBasketLayout(getContext(), binding);
        forItemLayout = new ForItemLayout(getContext(), binding);

        //todo 아이템 메뉴 클릭
        binding.lvExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                binding.layoutBarcode.setVisibility(View.GONE);
                binding.layoutItem.setVisibility(View.VISIBLE);
                String child = ((PosItemMenu_Adapter) (binding.lvExpandable.getExpandableListAdapter())).getChild(groupPosition, childPosition);
                Item[] items = Item.values();
                Item item = null;
                for (Item item1 : items) {
                    if (item1.koreanName.equals(child)) {
                        item = item1;
                    } else {
                        Log.e(TAG, "Menu is null");
                    }
                }
                forItemLayout.getSelectedItemNameFromMain(item);
                forItemLayout.setAllViewInit();
                return false;
            }
        });

        //todo 비코드 스캔 버튼
        binding.tvBarcodeLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(fragment).initiateScan();
                binding.layoutBarcode.setVisibility(View.VISIBLE);
                binding.layoutItem.setVisibility(View.GONE);
            }
        });
        //todo 외상 버튼 클릭
        binding.layoutBasket.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forBasketLayout.adapter.getItems().size() == 0) {
                    Toast.makeText(getContext(), "항목을 먼저 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(date);
                AlertDialog alertDialog = new PosDialogUtils().getAlertDialogForSetCreditTag(getContext(), new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                    }

                    @Override
                    public void callBackString(String str) {
                        ArrayList<BasketTypeItem> items = forBasketLayout.adapter.getItems();
                        Log.e("Credit", String.valueOf(items.size()));
                        MainViewModel.insertCreditItem(getContext(), getTime, items, SoldCode.CREDIT, str);
                        Toast.makeText(getContext(), "외상으로 등록되었습니다" + getTime, Toast.LENGTH_SHORT).show();
                        forBasketLayout.adapter.itemClear();
                        MainViewModel.setLiveDataBasketList(new ArrayList<>());
                        forBasketLayout.callSetViewFromPosMain();
                    }
                });
                alertDialog.show();

            }
        });

        //todo 영수증 클릭 버튼
        binding.layoutBasket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "준비중...", Toast.LENGTH_SHORT).show();
            }
        });
        //todo 계산 완료 버튼
        binding.layoutBasket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forBasketLayout.adapter.getItems().size() == 0) {
                    Toast.makeText(getContext(), "항목을 먼저 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "계산이 완료되었습니다", Toast.LENGTH_SHORT).show();
                forBasketLayout.adapter.itemClear();
                MainViewModel.setLiveDataBasketList(new ArrayList<>());
                forBasketLayout.getLiveData();
            }
        });

        //todo 바코드 리스트에서 Basket리스트로 이동
        binding.btnInPosInBarCodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lvInPosInBarcode.getAdapter().getCount() == 0) {
                    Toast.makeText(getContext(), "상품을 추가해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<BasketTypeItem> items = ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).getItems();
                Basket_BarcodeList_Adapter adapter = ((Basket_BarcodeList_Adapter) forBasketLayout.lv.getAdapter());
                adapter.addItem(items);
                forBasketLayout.callSetViewFromPosMain();
                binding.drawer.openDrawer(Gravity.RIGHT);
                Toast.makeText(getContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();
                ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).itemClear();
                ((Basket_BarcodeList_Adapter) binding.lvInPosInBarcode.getAdapter()).notifyDataSetChanged();
            }
        });
        // todo 아이템선택버튼 --> 바스켓 으로 '
        //  항목에 따라 Dialog뷰가 다르게 보여줘야됨
        //  먼저 그 항목의 Dialog코드를 가져와
        //  스위치문으로 맞는 다이로그를 보여줌
        //  그다음 콜백을 해와서 바스켓에 넘김*/
        binding.layoutItem.findViewById(R.id.btn_add_in_pos_to_basket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView) (binding.layoutItem.findViewById(R.id.tv_selectedVerticalValue))).getText().toString().isEmpty() || ((TextView) (binding.layoutItem.findViewById(R.id.tv_selectedHorizontalValue))).getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "항목을 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String itemTitle = binding.tvItemTitle.getText().toString();
                    for (Item item : Item.values()) {
                        if (item.koreanName.equals(itemTitle)) {
                            switch (item.dialogCode) {
                                case QUANTITY: {
                                    showDialogForQuantity();
                                    break;
                                }
                                case QUANTITY_NUT_SW_PW: {
                                    showDialogForBolt(item);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
        return binding.getRoot();
    }

    //todo 외상 저장

    //todo 수량과 셋트 메뉴 묻는 다이로그
    private void showDialogForBolt(Item item) {
        AlertDialog alertDialog = new PosDialogUtils().getDialogForBoltQuantity(getContext(), new PosDialogCallbackForBolt() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("LongLogTag")
            @Override
            public void callBack(ArrayList<CheckCode> checkCode, String quantity) {
                if (checkCode.size() == 0) {
                    String itemQuantity = quantity;
                    String itemName = binding.tvItemTitle.getText().toString().trim();
                    String itemStandard = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedVerticalInUnitPrice)).getText().toString().trim()
                            + " ->\n" + ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedHorizontalInUnitPrice)).getText().toString().trim();
                    String unitPrice = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedUnitPrice)).getText().toString().trim();
                    intentToBasket(new ArrayList<>(Arrays.asList(new BasketTypeItem(itemName, itemStandard, unitPrice, itemQuantity, null))));
                } else {
                    //todo 세트메뉴 추가시
                    String itemQuantity = quantity;
                    String itemName = binding.tvItemTitle.getText().toString().trim();
                    String itemStandard = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedVerticalInUnitPrice)).getText().toString().trim()
                            + " ->\n" + ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedHorizontalInUnitPrice)).getText().toString().trim();
                    String unitPrice = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedUnitPrice)).getText().toString().trim();
                    intentToBasket(new ArrayList<>(Arrays.asList(new BasketTypeItem(itemName, itemStandard, unitPrice, itemQuantity, null))));

                    Log.e("checkCodeSize", String.valueOf(checkCode.size()));
                    String width = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedVerticalInUnitPrice)).getText().toString().trim();
                    for (CheckCode code : checkCode) {
                        Log.e("When BoltItem Need Other Item", "Passed");
                        switch (code) {
                            case PW_2_QUANTITY: {
                                Log.e("PW_2_QUANTITY", "Passed");

                                searchOtherItem(item.pwCode, item.materialCode, width, String.valueOf(Integer.parseInt(quantity) * 2));
                                break;
                            }
                            case SW_2_QUANTITY: {
                                Log.e("SW_2_QUANTITY", "Passed");
                                searchOtherItem(item.swCode, item.materialCode, width, String.valueOf(Integer.parseInt(quantity) * 2));
                                break;
                            }

                            case NUT_2_QUANTITY: {
                                Log.e("NUT_2_QUANTITY", "Passed");

                                searchOtherItem(item.nutCode, item.materialCode, width, String.valueOf(Integer.parseInt(quantity) * 2));
                                break;
                            }
                            case PW_SAME_QUANTITY: {
                                Log.e("PW_SAME_QUANTITY", "Passed");

                                searchOtherItem(item.pwCode, item.materialCode, width, quantity);
                                break;
                            }
                            case SW_SAME_QUANTITY: {
                                Log.e("SW_SAME_QUANTITY", "Passed");

                                searchOtherItem(item.swCode, item.materialCode, width, quantity);
                                break;
                            }

                            case NUT_SAME_QUANTITY: {
                                Log.e("NUT_SAME_QUANTITY", "Passed");

                                searchOtherItem(item.nutCode, item.materialCode, width, quantity);
                                break;
                            }
                        }
                    }
                }
            }
        });
        alertDialog.show();
    }

    //todo 단순 수량만 묻는 다이로그
    private void showDialogForQuantity() {
        AlertDialog alertDialog = new PosDialogUtils().getDialogForQuantity(getContext(), new PosDialogCallbackForBolt() {
            @Override
            public void callBack(ArrayList<CheckCode> checkCode, String quantity) {
                String itemQuantity = quantity;
                String itemName = binding.tvItemTitle.getText().toString().trim();
                String itemStandard = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedVerticalInUnitPrice)).getText().toString().trim()
                        + " ->\n" + ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedHorizontalInUnitPrice)).getText().toString().trim();
                String unitPrice = ((TextView) binding.layoutItem.findViewById(R.id.tv_selectedUnitPrice)).getText().toString().trim();
                intentToBasket(new ArrayList<>(Arrays.asList(new BasketTypeItem(itemName, itemStandard, unitPrice, itemQuantity, null))));
            }
        });
        alertDialog.show();
    }

    //todo 세트메뉴 추가시 데이터 서칭
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchOtherItem(String itemCode, String material, String width, String quantity) {
        Log.e("searchOtherItem1", width);
        ArrayList<Value> itemStandard = MainViewModel.getValueForSetMenu(getContext(), itemCode, "standard");
        ArrayList<Value> setItem = MainViewModel.getValueForSetMenu(getContext(), itemCode, material);
        ArrayList<String> arrItemStandard = itemStandard.get(0).valueList();
        ArrayList<String> arrSetItem = setItem.get(0).valueList();
        String itemUnitPrice = null;
        for (int i = 0; i < arrItemStandard.size(); i++) {
            if (arrItemStandard.get(i).equals(width.replace("/", "_"))) {
                itemUnitPrice = arrSetItem.get(i);
                Log.e("searchOtherItem", itemUnitPrice);
                break;
            }
        }
        BasketTypeItem item = new BasketTypeItem(setItem.get(0).koreanName, material.trim()
                + " ->\n" + width.trim(), itemUnitPrice, quantity, null);
        intentToBasket(new ArrayList<>(Arrays.asList(item)));
    }


    //todo 바스켓으로 보내기
    private void intentToBasket(ArrayList<BasketTypeItem> items) {
        for (BasketTypeItem basketTypeItem : items) {
            Log.e("IntentValue", basketTypeItem.itemName + " " + basketTypeItem.itemStandard + " " + basketTypeItem.unitPrice + " " + basketTypeItem.quantity);
        }
        Basket_BarcodeList_Adapter adapter = ((Basket_BarcodeList_Adapter) forBasketLayout.lv.getAdapter());
        adapter.addItem(items);
        forBasketLayout.callSetViewFromPosMain();
        binding.drawer.openDrawer(Gravity.RIGHT);
        Toast.makeText(getContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("Pos", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Pos", "onActivityResult + if (result.getContents() == null)");
            } else {
                forBarcodeLayOut.searchBarcodeItemInDB(result.getContents());
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}