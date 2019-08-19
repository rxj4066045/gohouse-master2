package com.gohouse.gohouse.base;

import android.app.Application;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.base.helper.SnackBarHelper;
import com.blankj.utilcode.util.Utils;
import com.gohouse.gohouse.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        SnackBarHelper.init(this);
        initRefreshLayout();
    }

    //上拉刷新下拉加载配置
    private void initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white);//全局设置主题颜色
                ClassicsHeader header = new ClassicsHeader(context);
                header.setAccentColor(ContextCompat.getColor(context, R.color.black));
                header.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                return header;
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setAccentColor(ContextCompat.getColor(context, R.color.black));
                footer.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
                footer.setDrawableSize(20);

                return footer;
            }
        });
    }
}
