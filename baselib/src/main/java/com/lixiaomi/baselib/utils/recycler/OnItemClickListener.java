package com.lixiaomi.baselib.utils.recycler;

import android.view.View;

/**
 * @describe：点击事件<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public interface OnItemClickListener {
    /**
     * recyclerview的item点击时间
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);
}
