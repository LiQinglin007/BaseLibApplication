package com.lixiaomi.mvplib.bottom;

/**
 * @describe：底部菜单<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/28<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public final class BottomTabBean {
    /**
     * 文字
     */
    private final String mBottomTv;
    /**
     * 选中时的图片
     */
    private final int mBottomImgChecked;
    /**
     * 未选中时的图片
     */
    private final int mBottomImgUnChecked;

    public BottomTabBean(String bottomTv, int bottomImgChecked, int bottomImgUnChecked) {
        mBottomTv = bottomTv;
        mBottomImgChecked = bottomImgChecked;
        mBottomImgUnChecked = bottomImgUnChecked;
    }


    public String getmBottomTv() {
        return mBottomTv;
    }

    public int getmBottomImgChecked() {
        return mBottomImgChecked;
    }

    public int getmBottomImgUnChecked() {
        return mBottomImgUnChecked;
    }
}
