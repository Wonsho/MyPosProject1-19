package com.wons.myposproject.main_fragments.posfregment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.BasketLayout;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BasKetListAdapter;
import com.wons.myposproject.adapter.MyExpandableAdapter;
import com.wons.myposproject.adapter.ValueAdapter;
import com.wons.myposproject.databinding.FragmentPosItemBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Value;
import com.wons.myposproject.pos_value.BarCodeItem;
import com.wons.myposproject.pos_value.BasketItem;
import com.wons.myposproject.pos_value.BasketSoldCode;

import java.util.ArrayList;
import java.util.Arrays;

enum ActionCode {
    ACTION_CODE_INSERT,
    ACTION_CODE_SELECTED_DELETE,
    ACTION_CODE_ALL_DELETE;
}

enum ItemViewCode {
    BARCODE,
    ITEM_LIST_VIEW;
}

public class PosItemFragment extends Fragment {

    BasketLayoutMovement basketLayoutMovement;
    FragmentPosItemBinding binding;
    private final String TAG = "PosItemFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosItemBinding.inflate(inflater, container, false);

        MenuItemViewMovement.init(getContext(), this, binding);
        BasketLayoutMovement.init(binding, getContext());

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
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


final class MenuItemViewMovement {
    private MenuItemViewMovement(){}
    private MenuItemViewMovement menuItemViewMovement = new MenuItemViewMovement();
    private static Fragment fragment;
    private static Context context;
    private static FragmentPosItemBinding binding;
    static void init(Context context, Fragment fragment,FragmentPosItemBinding binding) {
        MenuItemViewMovement.fragment = fragment;
        MenuItemViewMovement.context = context;
        MenuItemViewMovement.binding = binding;
    }
//    static void barcodeScan(Fragment fragment) {
//        IntentIntegrator.forSupportFragment(fragment).initiateScan();
//    }
}


final class BasketLayoutMovement {

    private static Context context;
    private static BasketLayout basket;
    private static final BasketLayoutMovement basketLayoutMovement = new BasketLayoutMovement();

    private BasketLayoutMovement() {
    }
    static void init(FragmentPosItemBinding binding, Context context) {
        BasketLayoutMovement.basket = binding.layoutBasket;
        BasketLayoutMovement.context = context;
        setOnclick();
    }
    private static void setOnclick() {
       basket.findViewById(R.id.tv_credit_likeBtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //todo 외상버튼
           }
       });
        basket.findViewById(R.id.tv_printReceipt_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 영수증 출력
            }
        });
        basket.findViewById(R.id.tv_sold_likeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 계산 완료
            }
        });
        basket.findViewById(R.id.tv_clearList_likeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 모두 삭제
            }
        });
        ((ListView)basket.findViewById(R.id.lv_basket)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo 리스트를 누를시 그 아이템 삭제
            }
        });
    }
}

//final class MenuListLayoutMovement {
//
//    private MenuListLayoutMovement() {
//    }
//
//    private FragmentPosItemBinding binding;
//    private static final MenuListLayoutMovement menuListLayoutMovement = new MenuListLayoutMovement();
//
//    public static MenuListLayoutMovement getMenuListLayout() {
//        return menuListLayoutMovement;
//    }
//
//    public void setOnclickAndInit(FragmentPosItemBinding binding, Context context) {
//        this.binding = binding;
//        setItemMenuListView();
//    }
//
//    public void setItemMenuListView() {
//        MyExpandableAdapter adapter = new MyExpandableAdapter();
//        ExpandableListView expandableListView = binding.lvExpandable;
//        expandableListView.setAdapter(adapter);
//        ArrayList<Group> groupArrayList = new ArrayList<>(Arrays.asList(Group.values()));
//        adapter.putGroupList(groupArrayList);
//        adapter.notifyDataSetChanged();
//        for (int i = 0; i < adapter.getGroupCount(); i++) {
//            expandableListView.expandGroup(i);
//        }
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                binding.tvItemTitle.setText(((TextView) v.findViewById(R.id.tv_childValue)).getText().toString());
//                return false;
//            }
//        });
//    }
//}

