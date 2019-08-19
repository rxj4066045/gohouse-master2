package com.gohouse.gohouse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.HomeActivity;
import com.gohouse.gohouse.base.BaseFragment;


public class GuideFragment extends BaseFragment {

    private int page = 0;
    private TextView tv_jumpHome;

    public static GuideFragment newInstance(int page) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt("page",page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt("page",page);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        tv_jumpHome = findViewById(R.id.tv_jumpHome);
        tv_jumpHome.setVisibility(page == 4?View.VISIBLE:View.GONE);
        tv_jumpHome.setOnClickListener(v->{
            startActivity(HomeActivity.class);
        });
    }

    @Override
    protected void loadData() {

    }
}
