package com.tnaapp.tnalayout.control;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tnaapp.tnalayout.R;

/**
 * Created by dfChicken on 06/10/2015.
 */
public class SlideUpViewGroup extends ViewGroup {

    private static final String TAG = "SlideUpViewGroup"; //chỉnh sửa lại từ DraggableViewGroup
    private static final int MIN_FLING_VELOCITY = 400; // dips per second
    public View mHeaderView;
    public View mDescView;
    private int mHeaderHeight;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mViewGroupHeight;
    private int mVerticalRange;
    private int mTop;
    private float mDragOffset;
    // boolean thu nhỏ
    public boolean isMinimize = false;
    public boolean isFullScreen = false;
    private ViewDragHelper mViewDragHelper;
    private DraggableViewListener listener;
    private AppCompatActivity mRunnerActivity;
//    MediaController mMediaController;

    public class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public void onViewDragStateChanged(int state) {
//            Log.d("onViewDragStateChanged", String.valueOf(state));
            if (state == 0) {
                if (isMinimize) {
                    requestMinimizeToListener();
                } else {
                    requestMaximizeToListener();
//                    mMediaController.setVisibility(VISIBLE);
                }
            }
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//            Log.i(TAG, "onViewPositionChanged:" + "left:" + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy);
            mTop = top;
//            mMediaController.setVisibility(INVISIBLE);
            mDragOffset = (float) top / mVerticalRange;
            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
//            mVideoView.getHolder().setFixedSize(Math.round(1 - mDragOffset / 2f) , 1 - mDragOffset / 2.22f);

//            Log.d("mTop",String.valueOf(mTop));
            if (mDragOffset == 0.0f) {
                isMinimize = false;
            } else if (mDragOffset == 1.0f) {
                isMinimize = true;
            }
            requestLayout();
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            return newTop;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.4f)) {
                top += mVerticalRange;
            }
            mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
            invalidate();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }
    }

//    public void setMediaController(MediaController mMediaController) {
//        this.mMediaController = mMediaController;
//    }

    public void setDraggableViewListener(DraggableViewListener listener) {
        this.listener = listener;
    }

    public void setRunnerActivity(AppCompatActivity activity) {
        this.mRunnerActivity = activity;
    }

    public SlideUpViewGroup(Context context) {
        super(context);
        init(context);
    }

    public SlideUpViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideUpViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        final float density = context.getResources().getDisplayMetrics().density;
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mViewDragHelper.setMinVelocity(MIN_FLING_VELOCITY * density);
    }

    public void maximize() {
        isMinimize = false;
        smoothSlideTo(0.0f);
        requestMaximizeToListener();
    }

    public void minimize() {
        isMinimize = true;
        smoothSlideTo(1.0f);
        requestMinimizeToListener();
    }

    public void requestFullScreenPlayer() {
        Log.d("DraggableViewGroup", "requestFullScreenPlayer");
        mDescView.setVisibility(GONE);
        mHeaderHeight = mHeaderView.getHeight();
        mViewGroupHeight = this.getHeight();
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        isFullScreen = true;
    }

    public void requestFloatPlayer() {
        Log.d("DraggableViewGroup", "requestFloatPlayer");
        mDescView.setVisibility(VISIBLE);
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, mHeaderHeight));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mViewGroupHeight));
        isFullScreen = false;
    }

    public void forceRequestFullscreenPlayer() {
        Log.d("DraggableViewGroup", "force requestFullScreenPlayer by user");
        mDescView.setVisibility(GONE);
        mHeaderHeight = mHeaderView.getHeight();
        mViewGroupHeight = this.getHeight();
        mRunnerActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        isFullScreen = true;
    }

    public void forceRequestFloatPlayer() {
        Log.d("DraggableViewGroup", "force requestFloatPlayer by user");
        mDescView.setVisibility(VISIBLE);
        mRunnerActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, mHeaderHeight));
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mViewGroupHeight));
        isFullScreen = false;
    }

    private boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mVerticalRange);
        if (mViewDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    private boolean isHeaderTarget(MotionEvent event) {
        int[] mHeaderLocation = new int[2];
        mHeaderView.getLocationOnScreen(mHeaderLocation);
        int upperLimit = mHeaderLocation[1] + mHeaderView.getMeasuredHeight();
        int lowerLimit = mHeaderLocation[1];
        int y = (int) event.getRawY();
        return (y > lowerLimit && y < upperLimit);
    }

    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFInishInflate");
        mHeaderView = findViewById(R.id.viewHeader);
        mDescView = findViewById(R.id.viewDesc);
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0), resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int parentViewHeight = getHeight();
//        int dragViewHeight = mHeaderView.getMeasuredHeight();
        mVerticalRange = getHeight(); //kéo xuống kịch luôn
        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isHeaderTarget(event) && mViewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        } else {
            // thu nhỏ thì vô hiệu vuốt trái, phải -> áp dụng isMinimize trong SwipeDismissTouchListener.DismissCallbacks event
            if (isMinimize) {
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) throws IllegalArgumentException {
        mViewDragHelper.processTouchEvent(event);
        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();
        boolean isHeaderViewUnder = mViewDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final float slop = mViewDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
                    if (mDragOffset != 0 && isMinimize == true) {
                        maximize();
                    }
                }
                break;
            }
        }
        return isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() && screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    private void requestMaximizeToListener() {
        if (listener != null && !isMinimize) {
            listener.onMaximized();
        }
    }

    private void requestMinimizeToListener() {
        if (listener != null && isMinimize) {
            listener.onMinimized();
        }
    }
}