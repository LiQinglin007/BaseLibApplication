package com.lixiaomi.baselibapplication.bean;

/**
 * @describe：wifiBean<br>
 * @author：Xiaomi<br>
 * @createTime：2019/2/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class ScanResultBean {
    private String wifiName;
    private int level;
    /**
     * 加密方式
     */
    private String capabilities;
    /**
     * 已连接  正在连接  未连接 三种状态
     */
    private String state;

    /**
     * 加密方式
     */
    private String encryptTypeStr;

    private int encryptTypeCode;


    public String getEncryptTypeStr() {
        return encryptTypeStr;
    }

    public void setEncryptTypeStr(String encryptTypeStr) {
        this.encryptTypeStr = encryptTypeStr;
    }

    public int getEncryptTypeCode() {
        return encryptTypeCode;
    }

    public void setEncryptTypeCode(int encryptTypeCode) {
        this.encryptTypeCode = encryptTypeCode;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
