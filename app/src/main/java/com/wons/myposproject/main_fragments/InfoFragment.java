package com.wons.myposproject.main_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    FragmentInfoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater, container, false);

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
}