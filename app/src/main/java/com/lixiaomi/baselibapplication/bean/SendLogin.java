package com.lixiaomi.baselibapplication.bean;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/1/26
 * 内容：
 * 最后修改：
 */

public class SendLogin {
    /**
     * 登录名称
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 当前app版本号
     */
    private Integer versionCode;


    public SendLogin(String loginName, String password, Integer versionCode) {
        this.loginName = loginName;
        this.password = password;
        this.versionCode = versionCode;
    }
}
