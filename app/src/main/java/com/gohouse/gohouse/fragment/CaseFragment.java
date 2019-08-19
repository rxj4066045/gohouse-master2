package com.gohouse.gohouse.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.SearchActivity;
import com.gohouse.gohouse.adapter.CaseAdapter;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.bean.CaseBean;

//案例
public class CaseFragment extends BaseFragment {

    private RecyclerView rv_case;
    private DrawerLayout dl_menu;
    private ImageView iv_select;
    private ImageView iv_search;
    private RecyclerView rv_case1;

    public static CaseFragment newInstance() {
        CaseFragment fragment = new CaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_case;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        rv_case = findViewById(R.id.rv_case);
        dl_menu = findViewById(R.id.dl_menu);
        iv_select = findViewById(R.id.iv_select);
        iv_search = findViewById(R.id.iv_search);
        rv_case1 = findViewById(R.id.rv_case);
        dl_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //监听筛选菜单的点击事件滑出侧滑菜单
        setSelectListen();
        //监听搜索框的点击事件跳转到搜索页面
        setSearchListen();
        //充填数据
        inflatData();
    }

    private void inflatData() {

        CaseBean caseBean = new CaseBean();
        Context cxt = getActivity();
        CaseAdapter caseAdapter = new CaseAdapter(cxt, caseBean);
        rv_case.setAdapter(caseAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        rv_case.setLayoutManager(gridLayoutManager);

    }

    private void setSearchListen() {
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSelectListen() {
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_menu.openDrawer(Gravity.RIGHT);
            }
        });
    }

    @Override
    protected void loadData() {
        initData();
    }

    private void initData() {

    }
}
