package com.lixiaomi.baselibapplication.ui.mDesign;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.TestViewPagerAdapter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/17
 * 内容：
 * 最后修改：
 */

public class TabLayoutActivity extends XMBaseActivity {
    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    TestViewPagerAdapter mTestViewPagerAdapter;
    ArrayList<String> mTitles = new ArrayList<>();
    ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected Object setLayout() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            mTitles.add("标题：" + i);
            mFragments.add(TabLayoutFragment.getNewInstance());
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.tablayout_toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.setBackgroundColor(getResources().getColor(R.color.default_color));
        mTabLayout = findViewById(R.id.tablayout_tab);
        mViewPager = findViewById(R.id.tablayout_viewpager);
//
        mTestViewPagerAdapter = new TestViewPagerAdapter(getSupportFragmentManager());
        mTestViewPagerAdapter.setList(mFragments, mTitles);
        mViewPager.setAdapter(mTestViewPagerAdapter);
//
//
//
        //MODE_SCROLLABLE可滑动的展示
        //MODE_FIXED固定展示
        mTabLayout.setBackgroundColor(getResources().getColor(R.color.default_color));
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.warning_color5), getResources().getColor(R.color.color_white));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_white));
        mTabLayout.setSelectedTabIndicatorHeight(5);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
