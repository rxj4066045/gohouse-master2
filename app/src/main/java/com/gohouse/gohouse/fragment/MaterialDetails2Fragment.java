package com.gohouse.gohouse.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.ui.customView.PullToRefreshView;


public class MaterialDetails2Fragment extends BaseFragment {

    private PullToRefreshView refreshView;

    public static MaterialDetails2Fragment newInstance() {
        MaterialDetails2Fragment fragment = new MaterialDetails2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_material_details2;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        refreshView = findViewById(R.id.materialDetails2_refreshView);
        refreshView.setCanRefreshFooter(false);
        refreshView.setCanRefreshHeader(true);
        refreshView.setOnHeaderRefreshListener(view1 -> {
            refreshView.onHeaderRefreshComplete();
            getFragmentManager().popBackStack();
        });
    }

    @Override
    protected void loadData() {

    }
}
