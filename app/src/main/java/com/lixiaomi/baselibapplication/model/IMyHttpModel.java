package com.lixiaomi.baselibapplication.model;

import com.lixiaomi.mvplib.base.BaseModel;
import com.lixiaomi.mvplib.base.BasePresenterCallBack;
import com.lixiaomi.mvplib.base.MiHttpCallBack;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public interface IMyHttpModel extends BaseModel {
    /**
     * 登陆
     *
     * @param loginName
     * @param password
     * @return
     */
    void login(String loginName, String password, BasePresenterCallBack myPresenterCallBack);

}
