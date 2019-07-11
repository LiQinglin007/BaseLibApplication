package com.lixiaomi.baselib.utils.recycler;

/**
 * @describe：侧滑点击事件的接口<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public interface OnSideslipClick {
    /**
     * 删除按钮
     *
     * @param position
     */
    void onDeleteClick(int position);

    /**
     * 置顶按钮
     *
     * @param position
     */
    void onTopClick(int position);
}
