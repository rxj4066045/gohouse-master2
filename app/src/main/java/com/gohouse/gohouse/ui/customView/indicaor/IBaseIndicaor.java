package com.gohouse.gohouse.ui.customView.indicaor;


import androidx.viewpager.widget.ViewPager;

public interface IBaseIndicaor extends ViewPager.OnPageChangeListener  {
    /** bind ViewPager */
    void setViewPager(ViewPager vp);

    /** for special viewpager,such as LooperViewPager */
    void setViewPager(ViewPager vp, int realCount);

    void setCurrentItem(int item);
}
