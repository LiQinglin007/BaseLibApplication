package com.lixiaomi.baselibapplication.persenter;


import com.lixiaomi.baselibapplication.model.MyHttpModel;
import com.lixiaomi.baselibapplication.model.MyHttpModel2;
import com.lixiaomi.baselibapplication.ui.main.IMyHttpActivity;
import com.lixiaomi.mvplib.base.BaseModel;
import com.lixiaomi.mvplib.base.BasePresenter;
import com.lixiaomi.mvplib.base.BasePresenterCallBack;
import com.lixiaomi.mvplib.base.MiPersenterCallBack;

import java.util.ArrayList;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/2<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MyHttpPersenter extends BasePresenter<IMyHttpActivity, BaseModel> implements IMyHttpPersenter {


    @Override
    public void login(String loginName, String password) {
        final IMyHttpActivity view = getView();
        view.startLoading();
        ArrayList<BaseModel> models = getModelList();
        ((MyHttpModel) models.get(0)).login(loginName, password, new MiPersenterCallBack(view) {
            @Override
            public void success(String response) {
                view.setData(response);
            }
        });
    }

    @Override
    public void login2() {
        final IMyHttpActivity view = getView();
        view.startLoading();
        ArrayList<BaseModel> models = getModelList();
        ((MyHttpModel2) models.get(1)).login(new MiPersenterCallBack(view) {
            @Override
            public void success(String response) {
                view.setData(response);
            }
        });
    }

    @Override
    protected ArrayList<BaseModel> createModelList() {
        ArrayList<BaseModel> modelList = new ArrayList<>();
        modelList.add(new MyHttpModel());
        modelList.add(new MyHttpModel2());
        return modelList;
    }
}
