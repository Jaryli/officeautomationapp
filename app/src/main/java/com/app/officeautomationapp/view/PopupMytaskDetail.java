package com.app.officeautomationapp.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.app.officeautomationapp.R;

/**
 * Created by pc on 2018/1/12.
 */

public class PopupMytaskDetail extends PopupWindow {
    private View mView;
    private LinearLayout ll_popmenu_fankui,ll_popmenu_over;

    public  PopupMytaskDetail(Activity paramActivity, View.OnClickListener paramOnClickListener,
                         int paramInt1, int paramInt2){
        mView = LayoutInflater.from(paramActivity).inflate(R.layout.popup_mytask_detail, null);
        ll_popmenu_fankui = (LinearLayout) mView.findViewById(R.id.ll_popmenu_fankui);
        ll_popmenu_over = (LinearLayout) mView.findViewById(R.id.ll_popmenu_over);
        if (paramOnClickListener != null){
            //设置点击监听
            ll_popmenu_fankui.setOnClickListener(paramOnClickListener);
            ll_popmenu_over.setOnClickListener(paramOnClickListener);
            setContentView(mView);
            //设置宽度
            setWidth(paramInt1);
            //设置高度
            setHeight(paramInt2);
            //设置显示隐藏动画
            setAnimationStyle(R.style.AnimTools);
            //设置背景透明
            setBackgroundDrawable(new ColorDrawable(0));
        }
    }


}