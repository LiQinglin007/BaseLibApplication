package com.lixiaomi.mvplib.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @describe：presenter基类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/1<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public abstract class BasePresenter<T extends BaseView, M> {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * View接口类型的弱引用
     */
    protected Reference<T> mViewRefer;
    /**
     * Model的弱引用
     */
    protected Reference<ArrayList<M>> mModelReferList;

    /**
     * 创建model
     *
     * @return
     */
    protected abstract ArrayList<M> createModelList();

    protected ArrayList<M> getModelList() {
        ArrayList<M> modelList = createModelList();
        if (modelList != null) {
            mModelReferList = new WeakReference<ArrayList<M>>(modelList);
            //在Presenter中获取Model接口的引用
            return mModelReferList.get();
        } else {
            return null;
        }
    }


    /**
     * 判断有没有引用model
     *
     * @return
     */
    public boolean isModelAttached() {
        return mModelReferList != null && mModelReferList.get() != null;
    }


    /**
     * 解除引用
     */
    public void detachModel() {
        if (mModelReferList != null) {
            mModelReferList.clear();
            mModelReferList = null;
        }
    }

    /**
     * 建立连接
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRefer = new WeakReference<T>(view);
    }

    protected T getView() {
        //在Presenter中获取View接口的引用
        return mViewRefer.get();
    }

    /**
     * 判断有没有引用view
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRefer != null && mViewRefer.get() != null;
    }

    /**
     * 解除引用
     */
    public void detachView() {
        if (mViewRefer != null) {
            mViewRefer.clear();
            mViewRefer = null;
        }
    }
}
