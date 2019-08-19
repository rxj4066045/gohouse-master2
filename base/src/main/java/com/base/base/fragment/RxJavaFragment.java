package com.base.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2018/7/10.
 * 集成rxjava方法，rxjava公共方法可以在这里添加
 */

public abstract class RxJavaFragment extends BasicFragment2 {


    protected CompositeDisposable disposableDis;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposableDis = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(disposableDis != null)
        disposableDis.clear();
    }
}
