package com.lixiaomi.baselibapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/17
 * 内容：
 * 最后修改：
 */

public class TestViewPagerAdapter extends FragmentPagerAdapter {


    public TestViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    ArrayList<String> mStringArrayList = new ArrayList<>();

    public  void setList(ArrayList<Fragment> fragments, ArrayList<String> titles) {
        mFragmentArrayList.clear();
        mFragmentArrayList.addAll(fragments);
        mStringArrayList.clear();
        mStringArrayList.addAll(titles);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
//        return Math.min(mFragmentArrayList.size(), mStringArrayList.size());
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mStringArrayList.get(position);
    }
}
