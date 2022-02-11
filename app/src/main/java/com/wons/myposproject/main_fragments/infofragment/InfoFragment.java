package com.wons.myposproject.main_fragments.infofragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;
    private Fragment fragment;
    private ForSearedBarcodeItemLayout layoutForBarcodeInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        this.fragment = this;
        layoutForBarcodeInfo = new ForSearedBarcodeItemLayout(getContext(), binding);

        //todo 오른쪽 메뉴 버튼
        binding.btnSearchBarcodeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutSearchedBarcodeItem.setVisibility(View.VISIBLE);
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

    //todo 바코드 상품 조히 및 추가
    //     왼쪽의 DB에 저장된 바코드 상품을 보여주는 리사이클러뷰뷰를 만들고
    //     추가 및 삭제 기능 넣기
    //     중복되는 바코드가 나올시 다일로그를 띄워 원래의 아이템의 이름 및 단가가 가오고 수정을 물어봄
    //      ...............................
    //     저장되어있는 상품입니다 수정하시겠습니까?
    //     이름 : ooo 단가: ooo
    //     이름수정 단가수정 취소
    //     ................................


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
