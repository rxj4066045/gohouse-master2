package com.gohouse.gohouse.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;
import com.gohouse.gohouse.fragment.CaseFragment;
import com.gohouse.gohouse.fragment.MyHomeFragment;
import com.gohouse.gohouse.fragment.ProductFragment;
import com.gohouse.gohouse.fragment.RecommendkFragment;
import com.gohouse.gohouse.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private ImageView[] iv_tabs = new ImageView[4];
    private TextView[] tv_tabs = new TextView[4];

    private int showIndex = -1;
    private List<Fragment> fragments = new ArrayList<>();
    private int[] selectIcon = {R.mipmap.home_tab_rec_select,R.mipmap.home_tab_product_select,R.mipmap.home_tab_case_select,R.mipmap.home_tab_myhome_select};
    private int[] unSelectIcon = {R.mipmap.home_tab_rec_unselect,R.mipmap.home_tab_product_unselect,R.mipmap.home_tab_case_unselect,R.mipmap.home_tab_myhome_unselect};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initFragment();
        getDataforSearch();
    }

    private void getDataforSearch() {
        Intent intent = getIntent();
        int fragmentid = intent.getIntExtra("fragment",-1);
        Log.e("+++++++++++++++", "getDataforSearch: ++++++++++"+fragmentid);
        if(fragmentid == 2){
            showFragment(2);
        }
    }

    private void initView() {
        initTabView();
    }

    private void initTabView() {
        tv_tabs[0] = findViewById(R.id.home_tab_rec_text);
        iv_tabs[0] = findViewById(R.id.home_tab_rec_image);

        tv_tabs[1] = findViewById(R.id.home_tab_product_text);
        iv_tabs[1] = findViewById(R.id.home_tab_product_image);

        tv_tabs[2] = findViewById(R.id.home_tab_case_text);
        iv_tabs[2] = findViewById(R.id.home_tab_case_image);

        tv_tabs[3] = findViewById(R.id.home_tab_my_text);
        iv_tabs[3] = findViewById(R.id.home_tab_my_image);

        findViewById(R.id.home_tab_rec).setOnClickListener(view -> {if(isFast()) showFragment(0);});
        findViewById(R.id.home_tab_product).setOnClickListener(view -> {if(isFast()) showFragment(1);});
        findViewById(R.id.home_tab_case).setOnClickListener(view -> {if(isFast())  showFragment(2);});
        findViewById(R.id.home_tab_my).setOnClickListener(view -> {
            if(!isFast())
                return;

            if(UserUtils.isLogin())
                showFragment(3);
            else {
                //需要完成跳转到登录界面逻辑
            }
        });

    }

    private void initFragment() {
        fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() <= 0) {
            addFragment();
        }
        showFragment(0);
    }

    private void addFragment() {
        fragments = new ArrayList<>();
        fragments.add(RecommendkFragment.newInstance());
        fragments.add(ProductFragment.newInstance());
        fragments.add(CaseFragment.newInstance());
        fragments.add(MyHomeFragment.newInstance());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : fragments)
            transaction.add(R.id.home_context, f);
        transaction.commit();

    }

    public void showFragment(int index) {
        if(showIndex == index)
            return;
        showIndex = index;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                transaction.show(fragments.get(i));
                tv_tabs[i].setTextColor(getColor1(R.color.colorAccent));
                iv_tabs[i].setImageResource(selectIcon[i]);
            } else {
                transaction.hide(fragments.get(i));
                tv_tabs[i].setTextColor(getColor1(R.color.tabTextColor));
                iv_tabs[i].setImageResource(unSelectIcon[i]);
            }
        }
        transaction.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //重启时保存当前显示页
        outState.putInt("showIndex", showIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //重启后显示重启前显示页
            showIndex = savedInstanceState.getInt("showIndex");
            int oldIndex = showIndex;
            showIndex = -1;
            showFragment(oldIndex);
        }
    }
}
