package com.picturepicker.picturepicker.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.picturepicker.picturepicker.PicturePicker;
import com.picturepicker.picturepicker.R;
import com.picturepicker.picturepicker.bean.SelectPackageBean;
import com.picturepicker.picturepicker.interfaces.PictureLoader;

import java.util.List;

public class SelectPackagePopupWindow extends PopupWindow {

    private Context mContext;
    private RecyclerView recyclerView;
    private Adapter adapter;


    public SelectPackagePopupWindow(Context context) {
        super();
        mContext = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(getScreen(context).heightPixels/2);
        setFocusable(true);
        setBackgroundDrawable(ContextCompat.getDrawable(context,android.R.color.transparent));
        setContentView(getRecyclerView());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if(selectPackageLineaer != null)
                    selectPackageLineaer.dismiss();
            }
        });
    }



    public RecyclerView getRecyclerView() {

        if(recyclerView == null){
            recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setBackgroundColor(Color.parseColor("#eaeaea"));
            recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
            adapter = new Adapter();
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter a, View view, int position) {
                    if(selectPackageLineaer != null)
                        selectPackageLineaer.selectPackage(adapter.getData().get(position));
                }
            });
        }
        return recyclerView;
    }

    public void setData(@NonNull List<SelectPackageBean> imagePackages){
        adapter.setNewData(imagePackages);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        if(selectPackageLineaer != null)
            selectPackageLineaer.showing();
    }

    /**
     * 获取屏幕尺寸
     *
     * @return
     */
    private DisplayMetrics getScreen(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density = dm.density;
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
        return dm;

    }

    private SelectPackageLineaer selectPackageLineaer;

    public void setSelectPackageLineaer(SelectPackageLineaer selectPackageLineaer) {
        this.selectPackageLineaer = selectPackageLineaer;
    }

    public interface SelectPackageLineaer{
        void showing();
        void dismiss();
        void selectPackage(SelectPackageBean packageBean);
    }

    public static class Adapter extends BaseQuickAdapter<SelectPackageBean,BaseViewHolder>{

        public Adapter() {
            super(R.layout.select_package_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, SelectPackageBean item) {
            ImageView imageView = helper.getView(R.id.select_package_item_image);
            PictureLoader loader = PicturePicker.prictureLoader;
            if(loader != null)
                loader.loader(mContext,imageView,item.getImagePaht());
            helper.setText(R.id.select_package_item_text,item.getPackageName()+"("+item.count+")");
        }
    }

}
