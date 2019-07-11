package com.lixiaomi.baselibapplication.ui.mDesign;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.TestRecyclerAdapter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/16
 * 内容：一些笔记在xml文件里边
 * 最后修改：
 */

public class CoordinatorLayoutActivity extends XMBaseActivity {
    CoordinatorLayout mCoordinatorLayout;
    AppBarLayout mAppBarLayout;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    FloatingActionButton mFloatingActionButton;
    TestRecyclerAdapter mTestRecyclerAdapter;
    ArrayList<String> mList = new ArrayList<>();

    @Override
    protected Object setLayout() {
        return R.layout.activity_coordinator_layout;
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
        for (int i = 0; i < 20; i++) {
            mList.add("小米：" + i);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        mAppBarLayout = findViewById(R.id.coordinator_app_layout);
        mCollapsingToolbarLayout = findViewById(R.id.coordinator_collapsing);
        mToolbar = findViewById(R.id.coordinator_toolbar);
        mRecyclerView = findViewById(R.id.coordinator_recycler);
        mFloatingActionButton = findViewById(R.id.coordinator_floating);

        mTestRecyclerAdapter = new TestRecyclerAdapter(this);
        mTestRecyclerAdapter.setList(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTestRecyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAppBarLayout.setBackgroundColor(getResources().getColor(R.color.default_color));
        mToolbar.setTitle("标题");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
