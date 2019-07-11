package com.lixiaomi.baselibapplication.message;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-12-21<br>
 * Time: 10:13<br>
 * UpdateDescription：<br>
 */
public class TcpClientMessage {

    public final static int SERVER_CLOSE = 0X90;
    public final static int SERVER_OPEN = 0X91;

    private String message;
    private byte[] bytes;
    private int messageType;

    public TcpClientMessage() {
    }

    public TcpClientMessage(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public TcpClientMessage(byte[] bytes, int messageType) {
        this.bytes = bytes;
        this.messageType = messageType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
