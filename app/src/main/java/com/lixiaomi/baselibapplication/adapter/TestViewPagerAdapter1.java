package com.lixiaomi.baselibapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/17
 * 内容：
 * 最后修改：
 */

public class TestViewPagerAdapter1 extends PagerAdapter {

    ArrayList<View> mViewList = new ArrayList<>();

    public TestViewPagerAdapter1(ArrayList<View> viewList) {
        this.mViewList.clear();
        this.mViewList.addAll(viewList);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
