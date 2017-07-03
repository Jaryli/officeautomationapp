package com.app.officeautomationapp.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by CS-711701-00027 on 2017/5/26.
 */

public class MySwipeRefreshLayout  extends SwipeRefreshLayout {
    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MySwipeRefreshLayout(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int mTouchSlop;
    private float mPrevX;
    private float mPrevY;



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                mPrevY = MotionEvent.obtain(event).getY();
                Log.e("ACTION_DOWN","*********mPrevX*"+mPrevX);
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                final float eventY = event.getY();
                float xDiff = Math.abs(eventX - mPrevX);//横向位移
                float yDiff = Math.abs(eventY - mPrevY);//纵向位移
                Log.e("ACTION_MOVE","*********xDiff*"+xDiff);
                Log.e("ACTION_MOVE","*********yDiff*"+yDiff);
                if (xDiff > mTouchSlop) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }


}
