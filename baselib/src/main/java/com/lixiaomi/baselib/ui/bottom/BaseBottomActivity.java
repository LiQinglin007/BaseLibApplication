package com.lixiaomi.baselib.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.base.BaseActivity;
import com.lixiaomi.baselib.utils.CheckStringEmptyUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @describe：底部菜单的页面<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public abstract class BaseBottomActivity extends BaseActivity implements View.OnClickListener {

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<Fragment> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, Fragment> ITEMS = new LinkedHashMap<>();
    /**
     * 默认选中第0个
     */
    private int mChooseIndex = 0;
    private int mClickedColor;
    private int mUnClickedColor;
    private int mBackGroundColor;
    /**
     * 底部放按钮的布局
     */
    private LinearLayoutCompat mLayoutCompat;
    private OnBottomItemClickListener mOnBottomItemClickListener;
    Fragment openEdFragment = null;

    /**
     * 这里去做一些准备工作
     */
    public abstract void init();

    /**
     * 添加ItemBean
     *
     * @return
     */
    public abstract LinkedHashMap<BottomTabBean, Fragment> setItems();

    /**
     * 设置选中第几个
     *
     * @return
     */
    public abstract int setChooseIndex();

    /**
     * 设置选中的原色
     *
     * @return
     */
    public abstract int setClickedColor();

    /**
     * 设置未选中的原色
     *
     * @return
     */
    public abstract int setUnClickedColor();

    /**
     * 设置背景颜色
     *
     * @return
     */
    public abstract int setBackGroundColor();


    /**
     * 底部按钮的点击接口，<br>
     * 如果没有验证是想切换，就返回true<br>
     * 如果有验证，比如说没有登录的不允许切换，就返回false<br>
     *
     * @return
     */
    public abstract OnBottomItemClickListener setOnBottomItemClickListener();


    @Override
    protected Object setLayout() {
        return R.layout.base_bottom;
    }


    @Override
    protected void initData() {
        mChooseIndex = setChooseIndex();
        mClickedColor = (setClickedColor() == 0 ? R.color.color_51D8BA : setClickedColor());
        mUnClickedColor = (setUnClickedColor() == 0 ? R.color.color_666 : setUnClickedColor());
        mBackGroundColor = setBackGroundColor() == 0 ? R.color.color_white : setBackGroundColor();
        mOnBottomItemClickListener = setOnBottomItemClickListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        init();
        mLayoutCompat = findViewById(R.id.bottom_bar);
        mLayoutCompat.setBackgroundResource(mBackGroundColor);

        final LinkedHashMap<BottomTabBean, Fragment> items = setItems();
        if (items == null) {
            throw new NullPointerException("Fragment list can't be empty !");
        }
        if (items.size() == 0) {
            throw new RuntimeException("Fragment list size  can't be 0 !");
        }
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, Fragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final Fragment value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
        setView();
    }


    private void setView() {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(this).inflate(R.layout.bottom_item_icon_text_layout, mLayoutCompat);
            final LinearLayoutCompat item = (LinearLayoutCompat) mLayoutCompat.getChildAt(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            //设置每个item的点击事件this
            item.setTag(i);
            item.setOnClickListener(this);

            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            if (CheckStringEmptyUtils.isEmpty(bean.getmBottomTv())) {
                itemTitle.setVisibility(View.GONE);
            } else {
                itemTitle.setVisibility(View.VISIBLE);
                itemTitle.setText(bean.getmBottomTv());
                itemTitle.setTextColor(getResources().getColor(i == mChooseIndex ? mClickedColor : mUnClickedColor));
            }
            //判断要不要显示图片
            if (bean.getmBottomImgChecked() == 0 || bean.getmBottomImgUnChecked() == 0) {
                itemIcon.setVisibility(View.GONE);
            } else {
                itemIcon.setVisibility(View.VISIBLE);
                itemIcon.setImageResource(i == mChooseIndex ? bean.getmBottomImgChecked() : bean.getmBottomImgUnChecked());
            }
        }

        initCompletion();
        switchFragment();
    }

    /**
     * 初始化完成  去做一些事情
     */
    protected abstract void initCompletion();


    /**
     * 还原颜色
     */
    private void resetColor() {
        final int count = mLayoutCompat.getChildCount();
        for (int i = 0; i < count; i++) {
            BottomTabBean bottomTabBean = TAB_BEANS.get(i);
            final LinearLayoutCompat item = (LinearLayoutCompat) mLayoutCompat.getChildAt(i);
            final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);

            itemIcon.setImageResource(bottomTabBean.getmBottomImgUnChecked());
            itemTitle.setTextColor(getResources().getColor(mUnClickedColor));
        }
    }

    /**
     * 切换fragment
     *
     * @param chooseIndex
     */
    public void setSwitchIndex(int chooseIndex) {
        mChooseIndex = chooseIndex;
        handoffPage((LinearLayoutCompat) mLayoutCompat.getChildAt(chooseIndex), chooseIndex);
    }

    @Override
    public void onClick(View v) {
        //点击的是第几个
        final int tag = (int) v.getTag();
        if (mOnBottomItemClickListener != null) {
            if (mOnBottomItemClickListener.click(tag)) {
                //看上层让不让切换
                handoffPage(v, tag);
            }
        } else {
            throw new NullPointerException("setOnBottomItemClickListener() can't be empty !");
        }
    }


    private void handoffPage(View v, int tag) {
        //取到相应的Tab
        BottomTabBean bottomTabBean = TAB_BEANS.get(tag);
        //还原所有颜色，图片
        resetColor();
        //取到View
        final LinearLayoutCompat item = (LinearLayoutCompat) v;
        final AppCompatImageView itemIcon = (AppCompatImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        //设置选中的颜色和图片
        itemIcon.setImageResource(bottomTabBean.getmBottomImgChecked());
        itemTitle.setTextColor(getResources().getColor(mClickedColor));
        //改变选中的index,注意先后顺序
        mChooseIndex = tag;
        //切换view
        switchFragment();
    }


    private void switchFragment() {
        Fragment fragment = ITEM_DELEGATES.get(mChooseIndex);
        if (fragment != null) {
            if (openEdFragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.bottom_bar_delegate_container, fragment).commit();
                openEdFragment = fragment;
            } else {
                if (!openEdFragment.equals(fragment)) {
                    if (fragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().hide(openEdFragment).show(fragment).commit();
                        openEdFragment = fragment;
                    } else {
                        getSupportFragmentManager().beginTransaction().hide(openEdFragment).add(R.id.bottom_bar_delegate_container, fragment).commit();
                        openEdFragment = fragment;
                    }
                }
            }
        }
    }


    public interface OnBottomItemClickListener {
        /**
         * 底部按钮的点击接口，<br>
         * 如果没有验证是想切换，就返回true<br>
         * 如果有验证，比如说没有登录的不允许切换，就返回false<br>
         */
        boolean click(int position);
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TAB_BEANS.clear();
        ITEM_DELEGATES.clear();
        ITEMS.clear();
        mChooseIndex = 0;
        mClickedColor = 0;
        mUnClickedColor = 0;
        mBackGroundColor = 0;
        mLayoutCompat = null;
        mOnBottomItemClickListener = null;
        openEdFragment = null;
    }
}
