package com.lixiaomi.baselibapplication.ui.main;


import com.lixiaomi.mvplib.base.BaseView;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/1
 * 内容：
 * 最后修改：
 */

public interface IMainActivity extends BaseView {
    void setGetData(int code, String response);

    void setPostData(int code, String response);

    void setPostBeanData(int code, String response);

    void setPostHeadData(int code, String response);
}
