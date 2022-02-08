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

        //todo 바코드 아이템 리스트 보여주기 및 아이템 추가기능
        //  외상손님 조회 기능 --> 외상이 계산 되었으면 체크를 하여 외상 데이터에서 지움
    }
}