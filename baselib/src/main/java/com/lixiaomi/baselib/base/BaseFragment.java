package com.lixiaomi.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.ui.Loading.LoaderStyle;
import com.lixiaomi.baselib.ui.Loading.XiaomiLoader;
/**
 * @describe：MVP架构fragment基类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter> extends Fragment   {

    protected final String TAG = this.getClass().getSimpleName();
    protected P mPersenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //这里可以用一个view来放一个布局   也可以用一个布局id来当一个布局
        View rootView = null;
        if (setLayout() instanceof View) {
            if (((View) setLayout()) != null) {
                rootView = (View) setLayout();
                creatView(savedInstanceState, rootView);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        } else if (setLayout() instanceof Integer) {
            if (((Integer) setLayout()) != 0) {
                rootView = inflater.inflate((Integer) setLayout(), container, false);
                creatView(savedInstanceState, rootView);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        }
        return rootView;
    }

    private void creatView(Bundle savedInstanceState, View rootView) {
        initData();
        if (createPersenter() != null) {
            mPersenter = createPersenter();
            mPersenter.attachView((V) this);
        }
        initView(rootView, savedInstanceState);
    }

    /**
     * 设置布局/View
     *
     * @return
     */
    protected abstract Object setLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     *
     * @param rootView
     * @param savedInstanceState
     */
    protected abstract void initView(View rootView, Bundle savedInstanceState);

    /**
     * 创建Persenter
     *
     * @return
     */
    protected abstract P createPersenter();

    /**
     * 切换动画
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        (getActivity()).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        (getActivity()).overridePendingTransition(R.anim.toleft, R.anim.infright);
    }

    /**
     * 显示loading
     */
    protected void showLoading() {
        try {
            XiaomiLoader.showLoading(getActivity(), getResources().getColor(R.color.default_color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示loading
     *
     * @param color       颜色
     * @param loaderStyle 样式
     */
    protected void showLoading(int color, Enum<LoaderStyle> loaderStyle) {
        try {
            XiaomiLoader.showLoading(getActivity(), getResources().getColor(color), loaderStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏loading
     */
    protected void hineLoading() {
        XiaomiLoader.stopLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPersenter != null) {
            mPersenter.detachView();
            mPersenter.detachModel();
            mPersenter = null;
        }
        XiaomiLoader.stopLoading();
    }
}
