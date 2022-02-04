package com.wons.myposproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.wons.myposproject.databinding.ActivityMainBinding;
import com.wons.myposproject.itemvalues.Group;
import com.wons.myposproject.itemvalues.Item;
import com.wons.myposproject.main_fragments.HomeFragment;
import com.wons.myposproject.main_fragments.InfoFragment;
import com.wons.myposproject.main_fragments.PosItemFragment;

public class MainActivity extends AppCompatActivity {
    // TODO: 2022-02-04 데이터베이스 빌드, 뷰모델 생성(날씨 api, 스케쥴) 
    ActivityMainBinding binding;
    MainViewModel viewModel;
    public static final String TAG_DID = "DID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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