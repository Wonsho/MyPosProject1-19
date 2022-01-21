package com.wons.myposproject.main_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.myposproject.R;
import com.wons.myposproject.databinding.FragmentPosItemBinding;

public class PosItemFragment extends Fragment {

    FragmentPosItemBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding =  FragmentPosItemBinding.inflate(inflater, container, false);

       inflater.inflate(R.layout.in_pos, binding.framelayoutInpositem, true);
       return binding.getRoot();
    }
}