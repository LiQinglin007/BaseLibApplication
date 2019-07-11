package com.lixiaomi.baselib.eventmessage;

import java.io.File;

/**
 * @describe：EventBusMessageUtils<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/9<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiEventMessage {
    /**
     * 退出App
     */
    public static final int EXIT_APP = 0x512;
    /**
     * 是否正在更新
     */
    public static final int CHECK_APP = 0x513;
    /**
     * 切换fragment
     */
    public static final int SWITCH_FRAGMENT = 0x514;
    /**
     * 调用系统下载，下载完成
     */
    public static final int DOWNLOAD_FINISH = 0x515;
    /**
     * 调用系统下载，下载失败
     */
    public static final int DOWNLOAD_FAIL = 0x516;
    /**
     * 调用系统下载，开始下载
     */
    public static final int DOWNLOAD_START = 0x517;

    /**
     * 消息类型
     */
    private int MessageType;
    /**
     * String类型消息
     */
    private String MessageString;
    /**
     * int 类型消息
     */
    private int MessageInt;
    /**
     * long 类型消息
     */
    private long MessageLong;
    /**
     * boolean类型消息
     */
    private boolean MessageFlag;

    /**
     * 下载好的文件
     */
    private File DownLoadFile;


    /**
     * 构建无参数消息
     *
     * @param messageType
     */
    public MiEventMessage(int messageType) {
        this.MessageType = messageType;
    }

    /**
     * 构建String类型消息
     *
     * @param messageType   消息类型
     * @param messageString message
     */
    public MiEventMessage(int messageType, String messageString) {
        this.MessageType = messageType;
        this.MessageString = messageString;
    }

    /**
     * 构建int类型消息
     *
     * @param messageType 消息类型
     * @param messageInt  message
     */
    public MiEventMessage(int messageType, int messageInt) {
        this.MessageType = messageType;
        this.MessageInt = messageInt;
    }

    /**
     * 构建boolean类型消息
     *
     * @param messageType 消息类型
     * @param messageFlag message
     */
    public MiEventMessage(int messageType, boolean messageFlag) {
        this.MessageType = messageType;
        this.MessageFlag = messageFlag;
    }

    public int getMessageInt() {
        return MessageInt;
    }

    public long getMessageLong() {
        return MessageLong;
    }

    public void setMessageLong(long messageLong) {
        this.MessageLong = messageLong;
    }

    public File getDownLoadFile() {
        return DownLoadFile;
    }

    public void setDownLoadFile(File downLoadFile) {
        this.DownLoadFile = downLoadFile;
    }

    public boolean isMessageFlag() {
        return MessageFlag;
    }

    public void setMessageFlag(boolean messageFlag) {
        this.MessageFlag = messageFlag;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        this.MessageType = messageType;
    }

    public String getMessageString() {
        return MessageString;
    }

    public void setMessageString(String messageString) {
        this.MessageString = messageString;
    }

    public void setMessageInt(int messageInt) {
        this.MessageInt = messageInt;
    }
}
