package com.lixiaomi.baselib.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import com.lixiaomi.baselib.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @describe：上下滚动的广告位(可以做公告之类的效果)<br>
 * @author：Xiaomi<br>
 * @createTime：2017/10/10<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class SwitchViewGroup extends RelativeLayout {
    /**
     * 滚动时间
     */
    private static final int DURATION = 3000;
    /**
     * 显示层
     */
    private LinearLayoutCompat mRootLL;
    /**
     * 显示文本1
     */
    private AppCompatTextView mFirstTv;
    /**
     * 显示文本2
     */
    private AppCompatTextView mSecondTv;

    private Scroller mScroller;
    private int mIndex = -1;
    /**
     * 动画层
     */
    private LinearLayoutCompat mRootAnimLL;
    /**
     * 动画文本1
     */
    private AppCompatTextView mAnimFirstTv;
    /**
     * 动画文本2
     */
    private AppCompatTextView mAnimSecondTv;

    private boolean mIsEnd = true;
    private int mContentHeight;
    private List<String> mDatas;
    private OnClickTabListener mOnClickTabListener;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            scroll2Next();
            postDelayed(this, DURATION);
        }
    };

    public interface OnClickTabListener {
        void onClickTab(int index);
    }

    public SwitchViewGroup(Context context) {
        this(context, null);
    }

    public SwitchViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDatas = new ArrayList<String>();
        mContentHeight = context.getResources().getDimensionPixelSize(R.dimen.vertical_view_group_height);

        mScroller = new Scroller(context, new AccelerateDecelerateInterpolator());

        mRootLL = (LinearLayoutCompat) LayoutInflater.from(context).inflate(R.layout.vertical_view_group_view, null);
        mFirstTv = (AppCompatTextView) mRootLL.findViewById(R.id.first_content_TV);
        mSecondTv = (AppCompatTextView) mRootLL.findViewById(R.id.second_content_TV);

        mRootAnimLL = (LinearLayoutCompat) LayoutInflater.from(context).inflate(R.layout.vertical_view_group_anim_view, null);
        mAnimFirstTv = (AppCompatTextView) mRootAnimLL.findViewById(R.id.anim_first_content_TV);
        mAnimSecondTv = (AppCompatTextView) mRootAnimLL.findViewById(R.id.anim_second_content_TV);

        addView(mRootLL);
        addView(mRootAnimLL);
        mRootLL.setVisibility(VISIBLE);
        mRootAnimLL.setVisibility(INVISIBLE);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickTabListener != null && mDatas != null && mDatas.size() > 0) {
                    mOnClickTabListener.onClickTab(mIndex % mDatas.size());
                }
            }
        });
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mRootAnimLL.scrollTo(0, mScroller.getCurrY());
            invalidate();
        } else {
            if (mScroller.isFinished() && !mIsEnd) {
                mIsEnd = true;
                mRootLL.setVisibility(VISIBLE);
                mRootAnimLL.setVisibility(INVISIBLE);
            }
        }
    }

    public void scroll2Next() {
        mRootAnimLL.scrollTo(0, 0);
        mAnimFirstTv.setText(mFirstTv.getText());
        mAnimSecondTv.setText(mSecondTv.getText());
        mRootLL.setVisibility(INVISIBLE);
        mRootAnimLL.setVisibility(VISIBLE);
        mScroller.startScroll(mRootAnimLL.getScrollX(), mRootAnimLL.getScrollY(), 0, mContentHeight, 1000);
        mIsEnd = false;
        invalidateText();
        invalidate();
    }

    private void invalidateText() {
        mIndex++;
        if (mDatas != null && mDatas.size() > 0) {
            mFirstTv.setText(mDatas.get(mIndex % mDatas.size()));
            mSecondTv.setText(mDatas.get((mIndex + 1) % mDatas.size()));
        }
    }

    public void setmOnClickTabListener(OnClickTabListener mOnClickTabListener) {
        this.mOnClickTabListener = mOnClickTabListener;
    }

    public void stopScroll() {
        removeCallbacks(mRunnable);
    }

    public void startScroll() {
        if (mDatas != null && mDatas.size() > 0) {
            if (mDatas.size() == 1) {
                invalidateText();
            } else {
                stopScroll();
                post(mRunnable);
            }
        }
    }

    public void addData(String data) {
        mDatas.add(data);
    }

    public void addData(List<String> data) {
        mDatas.addAll(data);
    }

    @Override
    protected void onDetachedFromWindow() {
        stopScroll();
        super.onDetachedFromWindow();
    }
}
