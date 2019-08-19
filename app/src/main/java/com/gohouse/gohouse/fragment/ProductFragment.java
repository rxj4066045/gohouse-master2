package com.gohouse.gohouse.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.base.adapter.BaseViewPagerAdapter;
import com.base.tablayout.SlidingTabLayout;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.adapter.ProductAdapter;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.bean.ProductBean;
import com.gohouse.gohouse.java.LiveDataBus;
import com.gohouse.gohouse.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

//产品
public class ProductFragment extends BaseFragment implements ValueAnimator.AnimatorUpdateListener {
    // TODO: Rename parameter arguments, choose names that match

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private ValueAnimator amBig, amSma;
    private ConstraintLayout constraintLayout;
    private TextView tv_title;

    private ProductItemFragment fragment1, fragment2;
    private boolean isBig = true;

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initAnimator();
    }

    private void initView() {
        initConstraintLayout();
        initViewPager();

        LiveDataBus.get().with("isBig", Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean)
                            //变大
                            getBit();
                        else
                            //变小
                            getSma();
                    }
                });

    }

    private void initViewPager() {
        tabLayout = findViewById(R.id.product_tabLyaout);
        viewPager = findViewById(R.id.product_viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragment1 = ProductItemFragment.newInstance(0);
        fragment2 = ProductItemFragment.newInstance(1);
        fragments.add(fragment1);
        fragments.add(fragment2);
        viewPager.setAdapter(new BaseViewPagerAdapter<Fragment>(getChildFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager, new String[]{"整家", "软装"});

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //换页时判断要不要执行动画

                ProductAdapter adapter;
                if (position == 0)
                    adapter = fragment1.getAdapter();
                else
                    adapter = fragment2.getAdapter();

                if (adapter == null)
                    //变大
                    getBit();
                else {
                    List<ProductBean> data = adapter.getData();
                    if (data == null || data.size() <= 0) {
                        //变大
                        getBit();
                    } else {
                        LinearLayoutManager manager = adapter.getLinearLayoutManager();
                        if (manager == null)
                            //变大
                            getBit();
                        else {
                            int position1 = manager.findFirstVisibleItemPosition();
                            if (position1 > 0)
                                //变小
                                getSma();
                            else
                                getBit();
                        }
                    }

                }


            }
        });
    }

    private void getSma() {
        if (isBig) {
            isBig = false;
            amSma.start();
        }
    }

    private void getBit() {
        if (!isBig) {
            isBig = true;
            amBig.start();
        }
    }

    private void initConstraintLayout() {
        constraintLayout = findViewById(R.id.ConstraintLayout);
        tv_title = findViewById(R.id.product_title);
    }

    private void initAnimator() {
        amBig = ObjectAnimator.ofFloat(54, 74);
        amBig.setDuration(300);
        amBig.addUpdateListener(this);

        amSma = ObjectAnimator.ofFloat(74, 54);
        amSma.setDuration(300);
        amSma.addUpdateListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float value = (float) valueAnimator.getAnimatedValue();
        tv_title.setPivotX(0);
        tv_title.setScaleX(value / 74f);
        tv_title.setScaleY(value / 74);
        ViewGroup.LayoutParams params = constraintLayout.getLayoutParams();
        params.height = DisplayUtils.dp2px(getContext(), value);
        constraintLayout.setLayoutParams(params);
        findViewById(R.id.view).setAlpha((74 - value) / (20));
    }
}
