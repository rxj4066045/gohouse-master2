package com.gohouse.gohouse.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.SPUtils;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        boolean isFirst = SPUtils.getInstance().getBoolean("isFirst",true);

        if(isFirst){
            //跳转到引导页
            SPUtils.getInstance().put("isFirst",false);
            startActivity(GuideActivity.class);
        }else {
            //跳转到首页
            startActivity(HomeActivity.class);
        }

        finish();

    }
}
