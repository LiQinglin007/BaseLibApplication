package com.lixiaomi.baselibapplication.model;
import com.lixiaomi.baselib.base.BaseModel;
import com.lixiaomi.baselib.base.MyPresenterCallBack;

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
    void login(String loginName, String password, MyPresenterCallBack myPresenterCallBack);

}
