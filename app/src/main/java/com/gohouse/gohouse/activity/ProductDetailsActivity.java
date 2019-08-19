package com.gohouse.gohouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gohouse.gohouse.R;
import com.gohouse.gohouse.base.BaseActivity;
import com.gohouse.gohouse.ui.customView.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionMenu menu;
    private RecyclerView recyclerView;
    private ImageView iv_toTop;
    private ProductDetailsAdapter adapter;
    private View statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setTransparentStatus();
        initView();
    }

    private void initView() {
        initMenu();
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        statusBar = findViewById(R.id.proDetauls_statusBar);
        ViewGroup.LayoutParams params = statusBar.getLayoutParams();
        params.height = BarUtils.getStatusBarHeight();
    }

    private void initRecyclerView() {

        iv_toTop = findViewById(R.id.proDetauls_toTop);
        iv_toTop.setOnClickListener(this);

        recyclerView = findViewById(R.id.proDetauls_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductDetailsAdapter();
        recyclerView.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565679928785&di=bfb34aae74afd0c6ca0ddd5759123cc6&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201607%2F01%2F20160701102054_ZW2KP.jpeg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2283768062,330749390&fm=15&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3519168842,1013005339&fm=26&gp=0.jpg");
        list.add("https://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&word=%E6%89%8B%E6%9C%BA%E5%A3%81%E7%BA%B8&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=0&latest=0&copyright=0&cs=1201349879,2729409802&os=4243325979,2935346773&simid=4212486881,809571998&pn=443&rn=1&di=560&ln=1188&fr=&fmq=1565669827720_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=15&spn=0&pi=0&gsm=186&hs=2&objurl=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fef109ff786a782d47ef88ca4f1ad0b58ad28baec515f9-Pmb8Ic_fw658&rpstart=0&rpnum=0&adpicid=0&force=undefined");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3125451441,2514053450&fm=26&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3125451441,2514053450&fm=26&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3125451441,2514053450&fm=26&gp=0.jpg");
        adapter.setNewData(list);

    }

    private void initMenu() {
        menu = findViewById(R.id.proDetauls_menu);
        findViewById(R.id.proDetauls_menu6).setOnClickListener(this);
        findViewById(R.id.proDetauls_menu5).setOnClickListener(this);
        findViewById(R.id.proDetauls_menu4).setOnClickListener(this);
        findViewById(R.id.proDetauls_menu3).setOnClickListener(this);
        findViewById(R.id.proDetauls_menu2).setOnClickListener(this);
        findViewById(R.id.proDetauls_menu1).setOnClickListener(this);
    }

    private void setTransparentStatus() {
        //设置透明状态栏
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View v) {
        if (!isFast())
            return;

        switch (v.getId()) {
            case R.id.proDetauls_toTop:
                //recyclerView滚动到顶部
                recyclerView.smoothScrollToPosition(0);

                break;
            case R.id.proDetauls_menu6:
                menu.close(true);
                //预约装修
                break;
            case R.id.proDetauls_menu5:
                menu.close(true);
                //装修报价
                break;
            case R.id.proDetauls_menu4:
                menu.close(true);
                //评价
                break;
            case R.id.proDetauls_menu3:
                menu.close(true);
                //案例
                break;
            case R.id.proDetauls_menu2:
                menu.close(true);
                //物料
                startActivity(MaterialListActivity.class);
                break;
            case R.id.proDetauls_menu1:
                menu.close(true);
                //全景
                break;

        }

    }

    private static class ProductDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public ProductDetailsAdapter() {
            super(R.layout.product_details_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView iv = helper.getView(R.id.product_details_item_image);
            Glide.with(mContext).load(item).into(iv);
        }
    }

}
