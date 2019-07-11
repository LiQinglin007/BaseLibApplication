package com.lixiaomi.baselibapplication.ui.mDesign;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.utils.CheckStringEmptyUtils;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselib.utils.recycler.OnItemClickListener;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.TestRecyclerAdapter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.ui.mDesign.Behavior.BehaviorTestActivity;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/11
 * 内容：Design包下的控件使用等，有一些笔记在xml文件里边
 * 最后修改：
 */

public class MyDesignMainActivity extends XMBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    AppBarLayout mAppBarLayout;
    Toolbar mToolbar;
    //    CardView mCardView;
    RecyclerView mRecyclerView;
    FloatingActionButton mFloatingActionButton;

    ArrayList<String> mList = new ArrayList<>();
    TestRecyclerAdapter mTestRecyclerAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_design;
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
        String notificationId = getIntent().getStringExtra("notificationId");
        if (!CheckStringEmptyUtils.isEmpty(notificationId)) {
            T.shortToast(this, "拿到通知id:" + notificationId);
        }

        mList.add("我是一个cardView,点我打开左边的侧滑菜单");
        for (int i = 0; i < 20; i++) {
            mList.add("小米" + i);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDrawerLayout = findViewById(R.id.design_drawer);
        mNavigationView = findViewById(R.id.design_nav);
        mAppBarLayout = findViewById(R.id.design_appbar);
        mToolbar = findViewById(R.id.design_toolbar);
        mRecyclerView = findViewById(R.id.design_recycler);
        mFloatingActionButton = findViewById(R.id.design_floating);

        initNavigation();
        initCardView();
        initTopLay();
        initFloadtingButton();
        mTestRecyclerAdapter = new TestRecyclerAdapter(this);
        mTestRecyclerAdapter.setList(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mTestRecyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 控制FAB点击滑动(这里是滑动到0所以无所谓  但是要是滑动到后面的位置就要这样做)
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(recyclerView, mToPosition);
                }
            }
        });

        mTestRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    private void initFloadtingButton() {
        //设置背景颜色
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.warning_color5)));
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothMoveToPosition(mRecyclerView, 0);
            }
        });
    }

    private void initTopLay() {
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setBackgroundColor(getResources().getColor(R.color.default_color));
        mAppBarLayout.setOrientation(LinearLayout.VERTICAL);
        mAppBarLayout.setBackgroundColor(getResources().getColor(R.color.default_color));

        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.toolbar_but1:
                        T.shortToast(MyDesignMainActivity.this, item.getTitle() + "");
                        break;
                    case R.id.toolbar_but2:
                        startActivity(new Intent(MyDesignMainActivity.this, BehaviorTestActivity.class));
                        break;
                    case R.id.toolbar_but3:
                        startActivity(new Intent(MyDesignMainActivity.this, TabLayoutActivity.class));
                        break;
                    case R.id.toolbar_but4:
                        T.shortToast(MyDesignMainActivity.this, item.getTitle() + "");
                        break;
                    case R.id.toolbar_but5:
                        getApplicationContext().startActivity(new Intent(getApplicationContext(), CoordinatorLayoutActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_meun, menu);
        return true;
    }

    /**
     * 设置一些cardView的属性
     */
    private void initCardView() {

//        app:cardBackgroundColor      设置背景颜色
//        app:cardCornerRadius         设置圆角大小
//        app:cardElevation            设置z轴阴影高度
//        app:cardMaxElevation         设置z轴最大高度值
//        app:contentPadding           内容与边距的间隔
//        app:contentPaddingLeft       内容与左边的间隔
//        app:contentPaddingTop        内容与顶部的间隔
//        app:contentPaddingRight      内容与右边的间隔
//        app:contentPaddingBottom     内容与底部的间隔
//        app:paddingStart             内容与边距的间隔起始
//        app:paddingEnd               内容与边距的间隔终止
//        app:cardUseCompatPadding     设置内边距，在API21及以上版本和之前的版本仍旧具有一样的计算方式
//        app:cardPreventConrerOverlap 在API20及以下版本中添加内边距，这个属性为了防止内容和边角的重叠

//        mCardView.setCardBackgroundColor(getResources().getColor(R.color.color_4e4e4e));
//        mCardView.setMaxCardElevation(100);
//        mCardView.setCardElevation(10);

    }

    /**
     * NavigationView的一些设置
     */
    private void initNavigation() {
        //这里设置菜单的图标显示原来的颜色，要不然会显示一陀灰色
        mNavigationView.setItemIconTintList(null);
        //获取第0个头部view,并设置点击事件
        View headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.shortToast(MyDesignMainActivity.this, "你点击了头布局");
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        //设置菜单的点击事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * NavigationView 菜单的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.design_menu_item1:
                showSnackbar();
                break;
            case R.id.design_menu_item2:
                T.shortToast(MyDesignMainActivity.this, item.getTitle().toString());
                break;
            case R.id.design_menu_item3:
                startActivity(new Intent(MyDesignMainActivity.this, TabLayoutActivity.class));
                break;
            case R.id.design_menu_item4:
                T.shortToast(MyDesignMainActivity.this, item.getTitle().toString());
                break;
            case R.id.design_menu_item5:
                T.shortToast(MyDesignMainActivity.this, item.getTitle().toString());
                break;
            default:
                break;
        }
        //收起来侧滑菜单
        mDrawerLayout.closeDrawer(GravityCompat.START);
        //打开侧滑菜单
//        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 创建一个Snackbar
     */
    private void showSnackbar() {
        Snackbar.make(mNavigationView, "这是snackerbar,你点击的是第一个侧滑按钮", Snackbar.LENGTH_LONG).setAction("点击返回上一页", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();
    }


}
