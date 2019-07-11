package com.lixiaomi.baselib.utils.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * @describe：判断是否滑动到了底部<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class IsBottom {
    /**
     * 监听recyclerview是否滑动到了底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
            return true;
        }
        return false;
    }
}
