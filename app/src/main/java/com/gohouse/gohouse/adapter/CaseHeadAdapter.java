package com.gohouse.gohouse.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;


import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.view.XCRoundRectImageView;

public class CaseHeadAdapter extends PagerAdapter {
    private  TextView tv_pgPosition;
    private  Context cxt;
    public int mPosition = 1;

    public CaseHeadAdapter(Context cxt, TextView tv_pgPosition) {
        this.cxt = cxt;
        tv_pgPosition = tv_pgPosition;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //ImageView imageView = new ImageView(cxt);
        mPosition = position;
        XCRoundRectImageView imageView = new XCRoundRectImageView(cxt);
        Log.e("kkkkkkkkkkkk", "instantiateItem:llllllllll "+position);
        container.addView(imageView);
        imageView.setImageResource(R.drawable.bj2);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
