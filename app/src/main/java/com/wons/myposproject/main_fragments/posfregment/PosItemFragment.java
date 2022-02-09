package com.wons.myposproject.main_fragments.posfregment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.PosItemMenu_Adapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.Basket_Barcode_listView_Code;
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
    private ForBarcodeLayOut forBarcodeLayOut;
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
        fragment =this;
        forBarcodeLayOut = new ForBarcodeLayOut(getContext(), binding);

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

            }
        });
        //todo 영수증 클릭 버튼
        binding.layoutBasket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //todo 계산 완료 버튼
        binding.layoutBasket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

class ForBarcodeLayOut {
    Context context;
    FragmentPosItemBinding binding;
    ListView lv;

    public ForBarcodeLayOut(Context context, FragmentPosItemBinding binding) {
        this.context = context;
        this.binding = binding;
        lv = binding.lvInPosInBarcode;
        setView();
        onClick();
    }

    private void onClick() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog dialog = new PosDialogUtils().getAlertDialogForBasketDelete(context, new PosDialogCallback() {
                    @Override
                    public void callBack(Boolean yOrN) {
                        if(yOrN) {
                            deleteView(position);
                            return;
                        }
                    }

                    @Override
                    public void callBackString(String str) {
                        return;
                    }
                });
                dialog.show();
                return false;
            }
        });
    }



    public void searchBarcodeItemInDB(String barcode) {
        BarCodeItem item = MainViewModel.getBarcodeItem(context, barcode);
        if(item == null) {
            Toast.makeText(context, "해당하는 상품이 없습니다", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dialog(item);
        }
    }

    private void deleteView(int position) {
        Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show();
        ((Basket_BarcodeList_Adapter)lv.getAdapter()).deleteItem(position);
        setView();
    }



    private void dialog(BarCodeItem item) {
        AlertDialog dialog = new PosDialogUtils().getDialogForItemQuantity(context, new PosDialogCallback() {
            @Override
            public void callBack(Boolean yOrN) {
                return;
            }
            @Override
            public void callBackString(String str) {
                if(!str.isEmpty()){
                    ((Basket_BarcodeList_Adapter)lv.getAdapter()).addItem(new BasketTypeItem(item.name, null, item.unitPrice, str.trim()));
                    setView();
                    Toast.makeText(context, "추가 되었습니다",Toast.LENGTH_SHORT).show();
                } else {
                    dialog(item);
                    Toast.makeText(context, "수량을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void setView() {
        if (lv.getAdapter() == null) {
            lv.setAdapter(new Basket_BarcodeList_Adapter());
        }
        ((Basket_BarcodeList_Adapter)lv.getAdapter()).notifyDataSetChanged();
    }


}

class ForItemLayout {

}

class BasketLayout {

}





































