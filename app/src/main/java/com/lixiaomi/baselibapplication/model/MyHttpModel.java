package com.lixiaomi.baselibapplication.model;


import com.lixiaomi.baselib.base.MyPresenterCallBack;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MyHttpModel implements IMyHttpModel {

    @Override
    public void login(String loginName, String password, final MyPresenterCallBack myPresenterCallBack) {
        myPresenterCallBack.success(200, "111111111111111111111111111");
    }
}
