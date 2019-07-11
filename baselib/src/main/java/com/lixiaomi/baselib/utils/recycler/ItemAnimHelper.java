package com.lixiaomi.baselib.utils.recycler;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lixiaomi.baselib.R;

/**
 * @describe：最后一个item的加载动画<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/25<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class ItemAnimHelper {

//    1、private ItemAnimHelper itemAnimhelper = new ItemAnimHelper();
//    2、itemAnimhelper.showItemAnim(holder.linearLayout, position);
    private static int mLastPosition = -1;

    public static void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.recycler_item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }
}
