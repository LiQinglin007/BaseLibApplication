package com.lixiaomi.baselibapplication.model;


import com.lixiaomi.mvplib.base.BaseModel;
import com.lixiaomi.mvplib.base.BasePresenterCallBack;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface IMyHttpModel2 extends BaseModel {
    /**
     * 登陆
     *
     * @return
     */
    void login(BasePresenterCallBack myPresenterCallBack);

}
