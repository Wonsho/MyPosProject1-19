package com.wons.myposproject.main_fragments.posfregment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.PosItemMenu_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.itemvalues.Group;

import java.util.ArrayList;
import java.util.Arrays;

enum ItemViewCode {
    BARCODE,
    ITEM_LIST_VIEW;
}


public class PosItemFragment extends Fragment {
    private FragmentPosItemBinding binding;
    public static final String TAG = "PosItemFragment";
    private Fragment fragment;
    private ForBarCodeLayout forBarcodeLayOut;
    private ForBasketLayout forBasketLayout;

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

        //todo 아이템 메뉴 클릭
        binding.lvExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                binding.layoutBarcode.setVisibility(View.GONE);
                binding.layoutItem.setVisibility(View.VISIBLE);
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
                Toast.makeText(getContext(), "외상으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                forBasketLayout.adapter.itemClear();
                MainViewModel.setLiveDataBasketList(new ArrayList<>());
                forBasketLayout.callSetViewFromPosMain();

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
                forBasketLayout.callSetViewFromPosMain();
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
        return binding.getRoot();
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
//
//class ForItemLayout {
//
//}







































