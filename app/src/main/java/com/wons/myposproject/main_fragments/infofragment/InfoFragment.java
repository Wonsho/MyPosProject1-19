package com.wons.myposproject.main_fragments.infofragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.MainViewModel;
import com.wons.myposproject.R;
import com.wons.myposproject.adapter.BarcodeListAdapter;
import com.wons.myposproject.adapter.Basket_BarcodeList_Adapter;
import com.wons.myposproject.adapter.ItemValueAdapter;
import com.wons.myposproject.databinding.FragmentInfoBinding;
import com.wons.myposproject.layout_class.Info_credit_layout;
import com.wons.myposproject.main_fragments.posfregment.Basket_Value.BasketTypeItem;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;
    private Fragment fragment;
    private ForSearedBarcodeItemLayout layoutForBarcodeInfo;
    private LayoutForCredit layoutForCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        this.fragment = this;
        layoutForBarcodeInfo = new ForSearedBarcodeItemLayout(getContext(), binding);
        layoutForCredit = new LayoutForCredit(getContext(), binding.layoutSearchedCredit);

        //todo 오른쪽 메뉴 버튼
        binding.btnSearchBarcodeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutSearchedBarcodeItem.setVisibility(View.VISIBLE);
                binding.layoutSearchedCredit.setVisibility(View.GONE);
            }
        });
        binding.btnSearchCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutSearchedBarcodeItem.setVisibility(View.GONE);
                binding.layoutSearchedCredit.setVisibility(View.VISIBLE);
            }
        });

        //todo 바코드 스캔 버튼
        binding.layoutSearchedBarcodeItem.findViewById(R.id.btn_scanBarcode).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(fragment).initiateScan();
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
                layoutForBarcodeInfo.searchInDB(result.getContents());
                Log.e("Pos", "onActivityResult + Scanned" + result.getContents());
            }
        } else {
            Log.e("Pos", "onActivityResult6");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
