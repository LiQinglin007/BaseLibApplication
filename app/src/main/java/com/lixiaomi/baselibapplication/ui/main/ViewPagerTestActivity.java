package com.lixiaomi.baselibapplication.ui.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.TestViewPagerAdapter1;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.util.ArrayList;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/5/12<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class ViewPagerTestActivity extends XMBaseActivity {

    private android.support.v4.view.ViewPager mTestViewpager;
    ArrayList<View> mViewList = new ArrayList<>();
    TestViewPagerAdapter1 mTestViewPagerAdapter1;

    @Override
    protected Object setLayout() {
        return R.layout.activity_test_viewpager;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            View view = LayoutInflater.from(ViewPagerTestActivity.this).inflate(R.layout.test_view_pager, null);
            mViewList.add(view);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTestViewpager = findViewById(R.id.test_viewpager);
        mTestViewPagerAdapter1 = new TestViewPagerAdapter1(mViewList);
        mTestViewpager.setAdapter(mTestViewPagerAdapter1);
        mTestViewpager.setCurrentItem(5);
        mTestViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.loge("滑动到第" + i + "页面");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }
}
