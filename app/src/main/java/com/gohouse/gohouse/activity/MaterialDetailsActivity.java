package com.gohouse.gohouse.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;
import com.gohouse.gohouse.fragment.MaterialDetails1Fragment;
import com.gohouse.gohouse.fragment.MaterialDetails2Fragment;

public class MaterialDetailsActivity extends BaseActivity {

    private MaterialDetails1Fragment fragment1;
    private MaterialDetails2Fragment fragment2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_details);

        fragment1 = MaterialDetails1Fragment.newInstance();
        fragment2 = MaterialDetails2Fragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .add(R.id.materialDetails_fragment,fragment1);
        transaction.commit();

        initView();


    }

    private void initView() {
        findViewById(R.id.materialDetails_cross).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.materialDetails_attendant).setOnClickListener(view -> {
            //跳转到客服
             });
    }

    public void addFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.materialDetails_fragment,fragment2)
                .addToBackStack("");
        transaction.commit();
    }
}
