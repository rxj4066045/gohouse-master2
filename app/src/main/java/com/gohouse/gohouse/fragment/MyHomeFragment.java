package com.gohouse.gohouse.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.ImageView;

import com.gohouse.gohouse.R;
import com.gohouse.gohouse.activity.ReminderActivity;
import com.gohouse.gohouse.activity.ServerActivity;
import com.gohouse.gohouse.adapter.CaseWaterfallAdapter;
import com.gohouse.gohouse.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


//我的家
public class MyHomeFragment extends BaseFragment {

    private ImageView iv_reminder;
    private ImageView iv_server;
    private RecyclerView rv_my_home;

    public static MyHomeFragment newInstance() {
        MyHomeFragment fragment = new MyHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_myhome;
    }

    @Override
    protected void initStart(View view, @Nullable Bundle savedInstanceState) {
        iv_reminder = findViewById(R.id.iv_reminder);
        iv_server = findViewById(R.id.iv_server);
        rv_my_home = findViewById(R.id.rv_my_home);

        //监听消息提醒
        setReminder();
        //监听点击联系客服
        setServer();

        setfillData();
    }

    private void setfillData() {
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("aaaa"+i);
        }
        Context cxt = getActivity();
        CaseWaterfallAdapter caseWaterfallAdapter = new CaseWaterfallAdapter(cxt,list);
        rv_my_home.setAdapter(caseWaterfallAdapter);
        rv_my_home.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    private void setServer() {
        iv_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ServerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setReminder() {
        iv_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReminderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
