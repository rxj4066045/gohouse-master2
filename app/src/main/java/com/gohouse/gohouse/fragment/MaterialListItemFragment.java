package com.gohouse.gohouse.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.MaterialDetailsActivity;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.bean.MaterialListItemBean;

import java.util.ArrayList;
import java.util.List;


public class MaterialListItemFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match

    private RecyclerView recyclerView;
    private MaterialListItemAdapter adapter;

    public static MaterialListItemFragment newInstance() {
        MaterialListItemFragment fragment = new MaterialListItemFragment();
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
        return R.layout.fragment_material_list_item;
    }


    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.materialListItem_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MaterialListItemAdapter();
        recyclerView.setAdapter(adapter);

        List<MaterialListItemBean> list = new ArrayList<>();
        list.add(new MaterialListItemBean());
        list.add(new MaterialListItemBean());
        list.add(new MaterialListItemBean());
        adapter.setNewData(list);
        adapter.setOnItemClickListener((adapter1, view1, position) -> startActivity(MaterialDetailsActivity.class));


    }

    @Override
    protected void loadData() {
        LogUtils.e("loadData()");
    }

    private static class MaterialListItemAdapter extends BaseQuickAdapter<MaterialListItemBean, BaseViewHolder>{

        public MaterialListItemAdapter() {
            super(R.layout.material_list_item_adapter);
        }

        @Override
        protected void convert(BaseViewHolder helper, MaterialListItemBean item) {

        }
    }

}
