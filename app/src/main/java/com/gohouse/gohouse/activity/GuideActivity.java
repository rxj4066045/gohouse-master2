package com.gohouse.gohouse.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.base.base.adapter.BaseViewPagerAdapter;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;
import com.gohouse.gohouse.fragment.GuideFragment;
import com.gohouse.gohouse.ui.customView.indicaor.PageViewIndicaor;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;
    private PageViewIndicaor indicaor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = findViewById(R.id.guide_viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GuideFragment.newInstance(0));
        fragments.add(GuideFragment.newInstance(1));
        fragments.add(GuideFragment.newInstance(2));
        fragments.add(GuideFragment.newInstance(3));
        fragments.add(GuideFragment.newInstance(4));

        viewPager.setAdapter(new BaseViewPagerAdapter(getSupportFragmentManager(),fragments));
        indicaor = findViewById(R.id.guide_indicaor);
        indicaor.setViewPager(viewPager);
    }
}
