package com.tnaapp.tnalayout.model;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by dfChicken on 15/10/2015.
 */
public class CustomViewPager extends ViewPager {

    private CustomViewPagerListener mCustomViewPagerListener;

    public void setCustomViewPagerListener(CustomViewPagerListener listener) {
        if (listener != null)
            this.mCustomViewPagerListener = listener;
        else this.mCustomViewPagerListener = new CustomViewPagerListener() {
            @Override
            public void onSwipeUp() {

            }

            @Override
            public void onSwipeDown() {

            }
        };
    }

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float mStartDragY;
    boolean isSwipeable = false;

    //điểu khiển cử chỉ
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isSwipeable = true;
                mStartDragY = y;
                break;
            //điểu khiển vuốt lên, xuống...
            case MotionEvent.ACTION_MOVE:
                //độ dài tối đa để phát hiện vuốt = 120
                if (y - mStartDragY > 150) {
                    if (isSwipeable) {
//                        Log.d("onTouchEvent", "Swipe Down!");
                        mCustomViewPagerListener.onSwipeDown();
                        isSwipeable = false;
                    }
                } else if (mStartDragY - y > 150) {
                    if (isSwipeable) {
//                        Log.d("onTouchEvent", "Swipe Up!");
                        mCustomViewPagerListener.onSwipeUp();
                        isSwipeable = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (y < mStartDragY) {
                } else {
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface CustomViewPagerListener {
        void onSwipeUp();

        void onSwipeDown();
    }

}
