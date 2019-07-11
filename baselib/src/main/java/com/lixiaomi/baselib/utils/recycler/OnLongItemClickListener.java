package com.lixiaomi.baselib.utils.recycler;

import android.view.View;

/**
 * @describe：长按事件<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public interface OnLongItemClickListener {
    /**
     * recyclerview的item长按时间
     * @param view
     * @param position
     */
    void onLongItemClick(View view, int position);
}
