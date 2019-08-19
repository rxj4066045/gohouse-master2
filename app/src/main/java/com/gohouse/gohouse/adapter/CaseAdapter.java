package com.gohouse.gohouse.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.bean.CaseBean;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter {

    /**
     * 头部的用户说
     */
    public static final int HEADER = 0;

    /**
     * 瀑布流
     */
    public static final int WATERFALL = 1;
    private final Context cxt;
    private final CaseBean caseBean;
    private final LayoutInflater layoutInflater;
    private int currentType = HEADER;
    private int mPosition = 1;


    public CaseAdapter(Context cxt, CaseBean caseBean) {
        this.cxt = cxt;
        this.caseBean = caseBean;
        layoutInflater = LayoutInflater.from(cxt);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER){
            return new HeadViewHold(cxt,layoutInflater.inflate(R.layout.case_head,null));
        }else if(viewType == WATERFALL){
            return new WaterFallViewHold(cxt,layoutInflater.inflate(R.layout.waterfall_adapter,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == HEADER){
            Log.e("111111111111", "onBindViewHolder: 11111111111111111111");
            HeadViewHold headViewHold = (HeadViewHold) holder;
            headViewHold.setDate();
        }else if(getItemViewType(position) == WATERFALL){
            Log.e("111111111111", "onBindViewHolder: 2222222222222");
            WaterFallViewHold waterFallViewHold = (WaterFallViewHold) holder;
            waterFallViewHold.setDate();
            Log.e("111111111111", "onBindViewHolder: 444444444444444444");
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class WaterFallViewHold extends RecyclerView.ViewHolder{

        private  Context cxt;
        private  RecyclerView rv_waterfall;

        public WaterFallViewHold(Context cxt, @NonNull View itemView) {
            super(itemView);
            this.cxt = cxt;
            rv_waterfall = itemView.findViewById(R.id.rv_waterfall);
        }
        public void setDate(){
            List<String> list = new ArrayList<>();
            for(int i=0;i<20;i++){
                list.add("aaaa"+i);
            }
            CaseWaterfallAdapter caseWaterfallAdapter = new CaseWaterfallAdapter(cxt,list);
            rv_waterfall.setAdapter(caseWaterfallAdapter);
            rv_waterfall.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
    }


    @Override
    public int getItemViewType(int position) {


        switch (position) {
            case HEADER:
                currentType = HEADER;
                break;
            case WATERFALL:
                currentType = WATERFALL;
                break;
           
        }
        return currentType;

    }

    //头的viewPager
    class HeadViewHold extends RecyclerView.ViewHolder{

        private Context cxt;
        private  ViewPager vp_userSpeak;
        private TextView tv_pgPosition;

        public HeadViewHold(Context cxt, @NonNull View itemView) {
            super(itemView);
            this.cxt = cxt;
            vp_userSpeak = itemView.findViewById(R.id.vp_userSpeak);
            tv_pgPosition = itemView.findViewById(R.id.tv_pgPosition);

        }

        public void setDate(){
            CaseHeadAdapter caseHeadAdapter = new CaseHeadAdapter(cxt,tv_pgPosition);
            vp_userSpeak.setAdapter(caseHeadAdapter);


            tv_pgPosition.setText(mPosition+"");
            vp_userSpeak.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPosition = position;
                    mPosition = mPosition + 1;
                    tv_pgPosition.setText(mPosition+"");
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });



        }

    }
}
