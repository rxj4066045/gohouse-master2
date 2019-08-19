package com.gohouse.gohouse.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.bean.ProductBean;

public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    private RecyclerView recyclerView;

    public ProductAdapter() {
        super(R.layout.product_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public LinearLayoutManager getLinearLayoutManager(){
        if(recyclerView == null)
            return null;
        else
            return (LinearLayoutManager) recyclerView.getLayoutManager();
    }
}
