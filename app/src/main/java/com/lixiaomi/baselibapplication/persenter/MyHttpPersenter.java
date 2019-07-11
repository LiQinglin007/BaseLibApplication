package com.lixiaomi.baselibapplication.persenter;

import com.lixiaomi.baselib.base.BaseModel;
import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.base.MyPresenterCallBack;
import com.lixiaomi.baselibapplication.model.MyHttpModel;
import com.lixiaomi.baselibapplication.model.MyHttpModel2;
import com.lixiaomi.baselibapplication.ui.main.IMyHttpActivity;

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
        ((MyHttpModel) models.get(0)).login(loginName, password, new MyPresenterCallBack() {
            @Override
            public void success(int code, String response) {
                view.setData(code, response);
                view.stopLoading();
            }

            @Override
            public void failure(Throwable e) {
                view.setData(-100, e.toString());
                view.stopLoading();
            }
        });
    }

    @Override
    public void login2() {
        final IMyHttpActivity view = getView();
        view.startLoading();
        ArrayList<BaseModel> models = getModelList();
        ((MyHttpModel2) models.get(1)).login(new MyPresenterCallBack() {
            @Override
            public void success(int code, String response) {
                view.setData(code, response);
                view.stopLoading();
            }

            @Override
            public void failure(Throwable e) {
                view.setData(-100, e.toString());
                view.stopLoading();
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
