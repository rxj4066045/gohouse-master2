package com.gohouse.gohouse.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.base.base.adapter.BaseViewPagerAdapter;
import com.base.tablayout.SlidingTabLayout;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;
import com.gohouse.gohouse.fragment.MaterialListFragment;

import java.util.ArrayList;
import java.util.List;

public class MaterialListActivity extends BaseActivity {

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        initView();

        initData(new String[]{"客厅","女孩房","客卫","主卫","主卧","厨房","书房","玄关","餐厅","阳台"});
    }

    private void initView() {
        initToolbar();
        initViewPager();
    }

    private void initViewPager() {
        tabLayout = findViewById(R.id.material_list_tablayout);
        viewPager = findViewById(R.id.material_list_viewPager);
    }

    private void initToolbar() {
        findViewById(R.id.material_list_back).setOnClickListener(view -> {
            if(isFast())
                onBackPressed();
        });

        findViewById(R.id.material_list_attendant).setOnClickListener(v ->{
            if(isFast()){
                //跳转到客服
            }
        });
    }

    private void initData(String[] titles) {
        List<MaterialListFragment> fragments = new ArrayList<>();
        for (String title:titles)
            fragments.add(MaterialListFragment.newInstance(title));

        viewPager.setAdapter(new BaseViewPagerAdapter<MaterialListFragment>(getSupportFragmentManager(),fragments));
        tabLayout.setViewPager(viewPager,titles);

    }


}
