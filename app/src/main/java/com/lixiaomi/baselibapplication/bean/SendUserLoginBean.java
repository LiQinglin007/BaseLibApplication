package com.lixiaomi.baselibapplication.bean;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/8<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class SendUserLoginBean {
    private String LoginName;
    private String LoginPwd;
    /**
     * 固定1
     */
    private int LoginMode = 1;
    //传空串
    private String ImgCode = "";
    //传空串
    private String ImgId = "";
    private String ClientSN;

    public SendUserLoginBean(String loginName, String loginPwd, String clientSN) {
        LoginName = loginName;
        LoginPwd = loginPwd;
        ClientSN = clientSN;
    }

    @Override
    public String toString() {
        return "SendUserLoginBean{" +
                "LoginName='" + LoginName + '\'' +
                ", LoginPwd='" + LoginPwd + '\'' +
                ", LoginMode=" + LoginMode +
                ", ImgCode='" + ImgCode + '\'' +
                ", ImgId='" + ImgId + '\'' +
                ", ClientSN='" + ClientSN + '\'' +
                '}';
    }
}
