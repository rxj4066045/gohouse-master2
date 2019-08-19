package com.gohouse.gohouse.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.HomeActivity;
import com.gohouse.gohouse.base.BaseFragment;
import com.gohouse.gohouse.bean.RecBean1;
import com.gohouse.gohouse.bean.RecBean2;
import com.gohouse.gohouse.bean.RecBean3;
import com.gohouse.gohouse.bean.RecTitleBean;
import com.gohouse.gohouse.bean.RecommendBean;
import com.gohouse.gohouse.helper.RecyclerViewScrollHelper;
import com.gohouse.gohouse.ui.customView.ShapeImageView;

import java.util.ArrayList;
import java.util.List;


//推荐
public class RecommendkFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RecommendkAdapter adapter;

    private TextView tv_time, tv_adText;
    private ShapeImageView iv_header, iv_ad;
    private ImageView iv_toTop;

    private boolean isAniing =false;

    public static RecommendkFragment newInstance() {
        RecommendkFragment fragment = new RecommendkFragment();
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
        return R.layout.fragment_recommendk;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

        iv_toTop = findViewById(R.id.rec_toTop);
        iv_toTop.setOnClickListener(this);
        iv_toTop.setVisibility(View.GONE);

        refreshLayout = findViewById(R.id.rec_refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.rec_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecommendkAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(isAniing)
                    return;
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = manager.findFirstVisibleItemPosition();
                if(position >2){
                    //显示回到顶部按钮
                    if(iv_toTop.getVisibility() == View.GONE)
                        showAni();
                }else {
                    //隐藏回到顶部按钮
                    if(iv_toTop.getVisibility() == View.VISIBLE)
                        hideAni();
                }
            }
        });

        adapter.addHeaderView(getHeaderView1());
        adapter.addHeaderView(getHeaderView2());

        List<RecommendBean> beans = new ArrayList<>();
        beans.add(new RecTitleBean("整家产品推荐"));
        beans.add(new RecBean1());
        beans.add(new RecBean1());
        beans.add(new RecBean1());
        beans.add(new RecTitleBean("软装产品推荐"));
        beans.add(new RecBean1());
        beans.add(new RecBean1());
        beans.add(new RecBean2());
        beans.add(new RecTitleBean("用户说"));
        beans.add(new RecBean3());

        adapter.setNewData(beans);

        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            switch (view1.getId()) {
                case R.id.rec_item_allUserSay:
                    ((HomeActivity) getActivity()).showFragment(2);
                    break;
            }
        });
    }



    private View getHeaderView1() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.rec_header1, recyclerView, false);
        tv_time = view.findViewById(R.id.rec_header_time);
        tv_adText = view.findViewById(R.id.rec_header_adText);
        iv_ad = view.findViewById(R.id.rec_header_ad);
        iv_header = view.findViewById(R.id.rec_header_header);
        view.findViewById(R.id.rec_header_book).setOnClickListener(this);
        view.findViewById(R.id.rec_header_zero).setOnClickListener(this);
        view.findViewById(R.id.rec_header_ensure).setOnClickListener(this);
        return view;
    }

    private View getHeaderView2() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.rec_header2, recyclerView, false);
        view.findViewById(R.id.rec_header_construction).setOnClickListener(this);
        view.findViewById(R.id.rec_header_design).setOnClickListener(this);
        view.findViewById(R.id.rec_header_material).setOnClickListener(this);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onRefresh() {
        //此处需要添加刷新所有数据逻辑

        showAlerter("刷新数据");
        new Handler().postDelayed(() -> refreshLayout.setRefreshing(false), 1000);

    }

    @Override
    public void onClick(View view) {
        if(!isFast())
            return;
        switch (view.getId()) {
            case R.id.rec_header_book:
                showAlerter("跳转到预约量房");
                break;
            case R.id.rec_header_zero:
                showAlerter("跳转到零增量");
                break;
            case R.id.rec_header_ensure:
                showAlerter("跳转到资金保障");
                break;
            case R.id.rec_header_construction:
                showAlerter("跳转到施工标准");
                break;
            case R.id.rec_header_design:
                showAlerter("跳转到设计标准");
                break;
            case R.id.rec_header_material:
                showAlerter("跳转到选材标准");
                break;
            case R.id.rec_toTop:
                //回到顶部
                RecyclerViewScrollHelper.scrollToPosition(recyclerView,0);
                break;
        }
    }

    private void showAni() {
        iv_toTop.animate().scaleX(1).scaleY(1).setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAniing = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        iv_toTop.setVisibility(View.VISIBLE);
                        isAniing = true;
                    }

                }).start();


    }

    private void hideAni(){
        iv_toTop.animate().scaleX(0).scaleY(0).setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        iv_toTop.setVisibility(View.GONE);
                        isAniing = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAniing = true;
                    }
                }).start();
    }

    public static class RecommendkAdapter extends BaseMultiItemQuickAdapter<RecommendBean, BaseViewHolder> {

        public RecommendkAdapter() {
            super(null);
            addItemType(RecommendBean.titleType, R.layout.rec_item_title);
            addItemType(RecommendBean.beanType1, R.layout.rec_item_bean1);
            addItemType(RecommendBean.beanType2, R.layout.rec_item_bean2);
            addItemType(RecommendBean.beanType3, R.layout.rec_item_bean3);

        }

        @Override
        protected void convert(BaseViewHolder helper, RecommendBean item) {
            switch (item.getItemType()) {
                case RecommendBean.titleType:
                    initTitle(helper, item);
                    break;
            }
        }
        private void initTitle(BaseViewHolder helper, RecommendBean item) {
            RecTitleBean bean = (RecTitleBean) item;
            helper.setText(R.id.rec_item_title, bean.getTitle());
            helper.setGone(R.id.rec_item_allUserSay, TextUtils.equals(bean.getTitle(), "用户说"));
            helper.addOnClickListener(R.id.rec_item_allUserSay);
        }
    }


}
