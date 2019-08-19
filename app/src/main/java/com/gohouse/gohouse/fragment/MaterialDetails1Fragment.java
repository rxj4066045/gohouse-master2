package com.gohouse.gohouse.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.MaterialDetailsActivity;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.ui.customView.PullToRefreshView;


public class MaterialDetails1Fragment extends BaseFragment {

    private PullToRefreshView refreshView;
    private MaterialDetailsActivity activity;

    public static MaterialDetails1Fragment newInstance() {
        MaterialDetails1Fragment fragment = new MaterialDetails1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MaterialDetailsActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_material_details1;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        refreshView = findViewById(R.id.materialDetails1_refreshView);
        refreshView.setCanRefreshHeader(false);
        refreshView.setOnFooterRefreshListener(view1 -> {
            //跳转到下一页
            refreshView.onFooterRefreshComplete();
            activity.addFragment();
        });
    }

    @Override
    protected void loadData() {

    }
}
