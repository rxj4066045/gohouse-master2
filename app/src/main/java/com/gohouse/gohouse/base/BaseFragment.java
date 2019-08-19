package com.gohouse.gohouse.base;

import com.base.base.fragment.BasicFragment;
import com.base.helper.SnackBarHelper;

public abstract class BaseFragment extends BasicFragment {
    protected void finishShow(String text){
        SnackBarHelper.finishShow(text);
    }
}
