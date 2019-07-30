package com.lixiaomi.baselibapplication.ui.mDesign;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.adapter.TestRecyclerAdapter;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseFragment;
import com.lixiaomi.mvplib.base.BasePresenter;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/17
 * 内容：
 * 最后修改：
 */

public class TabLayoutFragment extends XMBaseFragment {
    RecyclerView recyclerView;
    ArrayList<String> mList = new ArrayList<>();
    TestRecyclerAdapter mTestRecyclerAdapter;

    public static TabLayoutFragment getNewInstance() {
        return new TabLayoutFragment();
    }

    @Override
    protected Object setLayout() {
        recyclerView = new RecyclerView(getActivity());
        return recyclerView;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 20; i++) {
            mList.add("小米" + i);
        }
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mTestRecyclerAdapter = new TestRecyclerAdapter(getActivity());
        mTestRecyclerAdapter.setList(mList);
        recyclerView.setAdapter(mTestRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        setInteger(getInteger());
    }

    private int getInteger() {
        return 1;
    }

    private void setInteger(int number) {
        LogUtils.loge("ss");
    }
}
