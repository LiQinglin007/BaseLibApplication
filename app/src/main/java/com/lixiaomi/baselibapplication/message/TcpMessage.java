package com.lixiaomi.baselibapplication.message;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-12-21<br>
 * Time: 10:13<br>
 * UpdateDescription：<br>
 */
public class TcpMessage {
    private String message;

    private byte buff[];

    public byte[] getBuff() {
        return buff;
    }

    public void setBuff(byte[] buff) {
        this.buff = buff;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
