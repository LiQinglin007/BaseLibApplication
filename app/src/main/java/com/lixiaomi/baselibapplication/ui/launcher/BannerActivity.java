package com.lixiaomi.baselibapplication.ui.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;

import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.ui.main.MainActivity;
import com.lixiaomi.mvplib.base.BasePresenter;


import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/10
 * 内容：banner
 * 这里只写了加载本地图片的例子，更多样式查看TestDemo项目，
 * 或者到git主页https://github.com/saiwu-bigkoo/Android-ConvenientBanner
 * * 最后修改：
 */

public class BannerActivity extends XMBaseActivity implements View.OnClickListener {
    ConvenientBanner mConvenientBanner;
    AppCompatButton mStartBut;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();//加载本地图片

    @Override
    protected Object setLayout() {
        return R.layout.activity_banner;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }


    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mConvenientBanner = findViewById(R.id.banner_convenient);
        mStartBut = findViewById(R.id.banner_start_but);
        mStartBut.setOnClickListener(this);
        setLocalView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_start_but:
                startActivity(new Intent(BannerActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 本地资源
     */
    private void setLocalView() {
        localImages.clear();
        localImages.add(R.drawable.banner01);
        localImages.add(R.drawable.banner02);
        localImages.add(R.drawable.banner03);
        localImages.add(R.drawable.banner04);
        localImages.add(R.drawable.banner05);
        //本地图片例子
        mConvenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_focus, R.drawable.dot_normal})
                //设置指示器的方向--中间
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //监听翻页事件
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        //0 ：没有滑动   1：正在滑动   2：正在滑动
                        LogUtils.loge(TAG, "滑动状态：" + newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                        当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
                        LogUtils.loge(TAG, "当页面在滑动的时候会调用此方法");
                    }

                    @Override
                    public void onPageSelected(int index) {
                        LogUtils.loge(TAG, "滑动到了第" + index + "个图片");
                        mStartBut.setVisibility(index == (localImages.size() - 1) ? View.VISIBLE : View.GONE);
                    }
                });
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        if (mConvenientBanner != null) {
            mConvenientBanner.stopTurning();
        }
    }

//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////        当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
////
////        position :当前页面，即你点击滑动的页面
////        positionOffset:当前页面偏移的百分比
////        positionOffsetPixels:当前页面偏移的像素位置
//
//    }
//
//
//    @Override
//    public void onPageSelected(int position) {
//        LogUtils.loge(TAG, "滑动到了第" + position + "个图片");
//        mStartBut.setVisibility(position == (localImages.size() - 1) ? View.VISIBLE : View.GONE);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//        //0 ：没有滑动   1：正在滑动   2：正在滑动
//    }

}
