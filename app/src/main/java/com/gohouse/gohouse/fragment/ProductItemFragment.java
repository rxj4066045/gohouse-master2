package com.gohouse.gohouse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.View;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.adapter.ProductAdapter;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.bean.ProductBean;
import com.gohouse.gohouse.java.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

//产品 整家:type=0 软装：type=1
public class ProductItemFragment extends BaseFragment {

    private int type = 0;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private ProductAdapter adapter;

    public static ProductItemFragment newInstance(int type) {
        ProductItemFragment fragment = new ProductItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type", type);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_item;
    }


    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        refreshLayout = findViewById(R.id.product_refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                showAlerter("刷新数据");
                refreshLayout.setRefreshing(false);
            }, 1000);
        });

        recyclerView = findViewById(R.id.product_recyclerView);
        recyclerView.setLayoutManager(manager = new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = manager.findFirstVisibleItemPosition();
                if (position > 0) {
                    //变小
                    LiveDataBus.get().with("isBig").setValue(false);
                } else {
                    //变大
                    LiveDataBus.get().with("isBig").setValue(true);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        List<ProductBean> beans = new ArrayList<>();
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        beans.add(new ProductBean());
        adapter.setNewData(beans);
    }

    public ProductAdapter getAdapter() {
        return adapter;
    }

}
