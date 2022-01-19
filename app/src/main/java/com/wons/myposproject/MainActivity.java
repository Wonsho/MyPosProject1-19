package com.wons.myposproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.wons.myposproject.databinding.ActivityMainBinding;
import com.wons.myposproject.main_fragments.HomeFragment;
import com.wons.myposproject.main_fragments.InfoFragment;
import com.wons.myposproject.main_fragments.PosItemFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeFragment(String.valueOf(item.getTitle()));
                return true;
            }
        });


    }


    private void changeFragment(String title) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(binding.fragmentContainerView.getId(),setFragment(title)).commit();
    }

    private Fragment setFragment(String title) {

        if (title.equals("홈")) {
            return new HomeFragment();
        } else if (title.equals("품목")) {
            return new PosItemFragment();
        } else if (title.equals("정보")) {
            return new InfoFragment();
        }
        return new HomeFragment();
    }



}