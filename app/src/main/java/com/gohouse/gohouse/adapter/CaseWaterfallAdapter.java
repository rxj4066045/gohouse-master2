package com.gohouse.gohouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gohouse.gohouse.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaseWaterfallAdapter extends RecyclerView.Adapter<CaseWaterfallAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;
    //创建集合
    List<Integer> heights = new ArrayList<>();

    public CaseWaterfallAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

        //通过获取随机数存入集合
        for (int i = 0; i < list.size(); i++) {
            int x = new Random().nextInt(800) + 400;
            heights.add(x);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.waterfall_img, null, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //获取控件原本高度，拿随机数集合赋值
        ViewGroup.LayoutParams layoutParams = myViewHolder.img.getLayoutParams();
        layoutParams.height = heights.get(i);
        myViewHolder.img.setLayoutParams(layoutParams);
        myViewHolder.img.setImageResource(R.mipmap.home);
//        if (myViewHolder.img != null) {
//            Glide.with(context).load(list.get(i)).into(myViewHolder.img);
//        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private  ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_waterfall);
        }
    }
}

