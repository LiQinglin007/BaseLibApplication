package com.lixiaomi.baselib.utils.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * @describe：判断是否滑动到了顶部<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class IsTop {
    /**
     * 监听recyclerview是否滑动到了顶部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToTop(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        int firstVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //得到当前界面，第一个子视图的position
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }

        if (firstVisibleItemPosition == 0) {
            return true;
        } else {
            return false;
        }
    }
}
