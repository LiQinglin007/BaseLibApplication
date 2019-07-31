package com.lixiaomi.mvplib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.lixiaomi.baselib.ui.Loading.LoaderStyle;
import com.lixiaomi.baselib.ui.Loading.XiaomiLoader;
import com.lixiaomi.baselib.utils.BaseAppManager;

/**
 * @describe：MVP架构基础类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected P mPersenter;
    /**
     * 设置状态栏
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseAppManager.getInstance().addActivity(this);
        //这里可以用一个view来放一个布局   也可以用一个布局id来当一个布局
        if (setLayout() instanceof View) {
            if (((View) setLayout()) != null) {
                setContentView((View) setLayout());
                creatView(savedInstanceState);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        } else if (setLayout() instanceof Integer) {
            if (((Integer) setLayout()) != 0) {
                setContentView((Integer) setLayout());
                creatView(savedInstanceState);
            } else {
                throw new RuntimeException("setLayout type must be view or int !");
            }
        }

        //设置透明(隐藏)状态栏
        if (setStatusBarColor() == 0) {
            //透明状态栏，不写默认透明色
            mImmersionBar = ImmersionBar.with(this).transparentStatusBar();
        } else {
            //fitsSystemWindows属性,必须指定状态栏颜色
            mImmersionBar = ImmersionBar.with(this)
                    .fitsSystemWindows(true)
                    .statusBarColor(setStatusBarColor());
        }
        //所有子类都将继承这些相同的属性
        mImmersionBar.init();
    }

    private void creatView(@Nullable Bundle savedInstanceState) {
        initData();
        if (createPersenter() != null) {
            mPersenter = createPersenter();
            mPersenter.attachView((V) this);
        }
        initView(savedInstanceState);
    }

    /**
     * 设置布局/View
     *
     * @return
     */
    protected abstract Object setLayout();

    /**
     * 初始化Data
     */
    protected abstract void initData();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 创建Persenter
     *
     * @return
     */
    protected abstract P createPersenter();

    /**
     * 设置顶部状态栏颜色
     *
     * @return 0 隐藏状态栏  其他要传R.color资源进来
     */
    protected abstract int setStatusBarColor();

    /**
     * 返回的切换动画
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(com.lixiaomi.baselib.R.anim.toleft, com.lixiaomi.baselib.R.anim.infright);
    }

    /**
     * 处理切换动画
     *
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(com.lixiaomi.baselib.R.anim.in_from_right, com.lixiaomi.baselib.R.anim.out_to_left);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(com.lixiaomi.baselib.R.anim.toleft, com.lixiaomi.baselib.R.anim.infright);
    }

    /**
     * 正在发起请求的数量，用来控制loading
     */
    private int mLoadingNumber = 0;

    /**
     * 显示loading
     */
    protected void showLoading() {
        try {
            mLoadingNumber++;
            XiaomiLoader.showLoading(this, getResources().getColor(com.lixiaomi.baselib.R.color.default_color));
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
            mLoadingNumber++;
            XiaomiLoader.showLoading(this, getResources().getColor(color), loaderStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏loading
     */
    protected void hineLoading() {
        mLoadingNumber--;
        if (mLoadingNumber == 0) {
            XiaomiLoader.stopLoading();
        }
    }

    /**
     * 解除引用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            //必须调用该方法，防止内存泄漏
            mImmersionBar.destroy();
        }
        if (mPersenter != null) {
            mPersenter.detachView();
            mPersenter.detachModel();
            mPersenter = null;
        }
        XiaomiLoader.stopLoading();
        BaseAppManager.getInstance().removeActivity(this);
    }
}
