package com.app.officeautomationapp.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by pc on 2017/11/26.
 */

public class MyScrollview extends ScrollView{

    private int flag = 0;
    private float StartX;
    private float StartY;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 总是调用listview的touch事件处理
        onTouchEvent(ev);
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            StartX = ev.getX();
//            StartY = ev.getY();
//            return false;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            float ScollX = ev.getX() - StartX;
//            float ScollY = ev.getY() - StartY;
//            // 判断是横滑还是竖滑，竖滑的话拦截move事件和up事件（不拦截会由于listview和scrollview同时执行滑动卡顿）
//            if (Math.abs(ScollX) < Math.abs(ScollY)) {
//                flag = 1;
//                return true;
//            }
//            return false;
//        }
//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            if (flag == 1) {
//                return true;
//            }
//            return false;
//        }
        return true;
//        return super.onInterceptTouchEvent(ev);
    }
}
