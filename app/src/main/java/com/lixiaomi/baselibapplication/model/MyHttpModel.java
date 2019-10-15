package com.lixiaomi.baselibapplication.model;

import com.lixiaomi.mvplib.base.BasePresenterCallBack;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MyHttpModel implements IMyHttpModel {

    @Override
    public void login(String loginName, String password, final BasePresenterCallBack myPresenterCallBack) {
        myPresenterCallBack.success("111111");
    }
}
