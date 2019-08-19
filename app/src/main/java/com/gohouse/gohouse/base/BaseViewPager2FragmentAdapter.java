package com.gohouse.gohouse.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseViewPager2FragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public BaseViewPager2FragmentAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        this(fragmentActivity.getSupportFragmentManager(),fragmentActivity.getLifecycle(),fragments);
    }

    public BaseViewPager2FragmentAdapter(@NonNull Fragment fragment,List<Fragment> fragments) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle(),fragments);
    }

    public BaseViewPager2FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        if(fragments != null)
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
