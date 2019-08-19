package com.gohouse.gohouse.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.gohouse.gohouse.R;

public class ProductDetailsBehavior extends CoordinatorLayout.Behavior<ConstraintLayout> {

    private boolean isLight = false;
    private boolean isAniing =false;
    private ImageView iv_toTop;

    public ProductDetailsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ConstraintLayout child, @NonNull View dependency) {

        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ConstraintLayout child, @NonNull View dependency) {
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ConstraintLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        iv_toTop = coordinatorLayout.findViewById(R.id.proDetauls_toTop);
        return type == 1 || type == 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ConstraintLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        View view1 = child.findViewById(R.id.proDetauls_statusBar);
        View view2 = child.findViewById(R.id.proDetauls_toolbarBack);

        ImageView iv_back = child.findViewById(R.id.proDetauls_back);
        ImageView iv_attendant = child.findViewById(R.id.proDetauls_attendant);
        ImageView iv_share = child.findViewById(R.id.proDetauls_share);

        float screenHeight = ScreenUtils.getScreenHeight();
        RecyclerView view = (RecyclerView) target;
        float scrollY = view.computeVerticalScrollOffset();
        if (scrollY > screenHeight) {
            scrollY = screenHeight;
            if(!isLight){
                isLight = true;
                iv_back.setImageResource(R.mipmap.back_light);
                iv_attendant.setImageResource(R.mipmap.attendant_light);
                iv_share.setImageResource(R.mipmap.share_light);
            }
            if(!isAniing){
                showAni();
            }

        }else {
            if(isLight){
                isLight = false;
                iv_back.setImageResource(R.mipmap.back_icon);
                iv_attendant.setImageResource(R.mipmap.attendant_icon);
                iv_share.setImageResource(R.mipmap.share_icon);
            }
            if(!isAniing){
                hideAni();
            }
        }

        view1.setAlpha(scrollY / screenHeight);
        view2.setAlpha(scrollY / screenHeight);


    }


    private void showAni() {
        iv_toTop.animate().scaleX(1).scaleY(1).setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAniing = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        iv_toTop.setVisibility(View.VISIBLE);
                        isAniing = true;
                    }

                }).start();


    }

    private void hideAni(){
        iv_toTop.animate().scaleX(0).scaleY(0).setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        iv_toTop.setVisibility(View.GONE);
                        isAniing = false;
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        isAniing = true;
                    }
                }).start();
    }



}
