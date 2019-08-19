package com.gohouse.gohouse.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class MaterialListFragment extends BaseFragment {

    //物料清单
    private String title;
    private RecyclerView recyclerView;
    private MaterialListTabAdapter adapter;
    private int selectPos = 0;
    private ViewPager2 viewPager;
    private LinearLayoutManager manager;

    public static MaterialListFragment newInstance(String title) {
        MaterialListFragment fragment = new MaterialListFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_material_list;
    }


    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initViewPager();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.materialList_viewPager);
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.materialList_recyclerView);
        recyclerView.setLayoutManager(manager = new LinearLayoutManager(getContext()));
        adapter = new MaterialListTabAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view1, position) -> {
//            selectPos(position);
            viewPager.setCurrentItem(position);
        });


    }

    private void selectPos(int position) {
        int oldpos = selectPos;
        selectPos = position;

        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
        if(position <= firstVisibleItemPosition || position >= lastVisibleItemPosition)
            recyclerView.smoothScrollToPosition(position);

        adapter.notifyItemChanged(oldpos);
        adapter.notifyItemChanged(selectPos);

    }

    @Override
    protected void loadData() {

        List<String> tabs = new ArrayList<>();
        for (int i = 0;i < 50 ; i++)
            tabs.add(i+"标题");

        adapter.setNewData(tabs);

        List<MaterialListItemFragment> fragments = new ArrayList<>();
        for (String title:tabs)
            fragments.add(MaterialListItemFragment.newInstance());
        viewPager.setAdapter(new MaterialListViewPager(this,fragments));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectPos(position);
            }
        });

    }


    public static class MaterialListViewPager extends FragmentStateAdapter{
        List<MaterialListItemFragment> fragments = new ArrayList<>();
        public MaterialListViewPager(@NonNull Fragment fragment, List<MaterialListItemFragment> fragments) {
            super(fragment);
            if(fragment != null)
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }

    public class MaterialListTabAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public MaterialListTabAdapter() {
            super(R.layout.material_list_tab_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            int position = helper.getLayoutPosition();
            boolean isSelect = position == selectPos;
            helper.setText(R.id.materialListTabItem_tab,item)
                    .setTextColor(R.id.materialListTabItem_tab, ContextCompat.getColor(mContext,isSelect?R.color.mainGreen:R.color.tabTextColor))
                    .setGone(R.id.materialListTabItem_indicator,isSelect);

        }
    }

}
