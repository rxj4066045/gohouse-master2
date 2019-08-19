package com.gohouse.gohouse.ui.dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.base.BaseFullDialogFragment;
import com.gohouse.gohouse.R;

public class MyInitDilaog extends BaseFullDialogFragment {

    public static MyInitDilaog newInstance() {
        MyInitDilaog fragment = new MyInitDilaog();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_init_dilaog;
    }


}
