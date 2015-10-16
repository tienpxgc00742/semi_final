package com.tnaapp.tnalayout.model;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by dfChicken on 16/10/2015.
 */
public class CustomWebView extends WebView {
    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private CustomWebViewListener mCustomWebViewListener;

    float mStartDragY;
    boolean isSwipeable = false;

    public void setCustomWebViewListener(CustomWebViewListener listener) {
        this.mCustomWebViewListener = listener;
    }


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
                if (y - mStartDragY > 120) {
                    if (isSwipeable) {
//                        Log.d("onTouchEvent", "Swipe Down!");
                        mCustomWebViewListener.onSwipeDown();
                        isSwipeable = false;
                    }
                } else if (mStartDragY - y > 120) {
                    if (isSwipeable) {
//                        Log.d("onTouchEvent", "Swipe Up!");
                        mCustomWebViewListener.onSwipeUp();
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

    public interface CustomWebViewListener {
        void onSwipeUp();

        void onSwipeDown();
    }


}
