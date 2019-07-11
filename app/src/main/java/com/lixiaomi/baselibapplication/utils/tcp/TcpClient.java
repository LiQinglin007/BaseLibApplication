package com.lixiaomi.baselibapplication.utils.tcp;

import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.message.TcpClientMessage;

import org.greenrobot.eventbus.EventBus;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by Jason Zhu on 2017-04-25.
 * Email: cloud_happy@163.com
 */

public class TcpClient implements Runnable {
    private String serverIP = "192.168.4.1";
    private int serverPort = 11100;
    public PrintWriter pw;
    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private boolean isRun = true;
    private Socket socket = null;
    byte buff[] = new byte[4096];
    private String rcvMsg;
    private int rcvLen;

    public TcpClient(String ip, int port) {
        this.serverIP = ip;
        this.serverPort = port;
        contentTime = 0;
    }

    public void closeSelf() {
        isRun = false;
        pw = null;
        is = null;
        dis = null;
        socket = null;
    }

    public boolean send(String msg) {
        if (pw != null) {
            pw.println(msg);
            pw.flush();
            LogUtils.loge("run: 发送成功:" + msg);
        } else {
            if (contentTime > 9) {
                LogUtils.loge("连接失败");
                return false;
            }
            boolean tcpConnect = getTcpConnect();
            if (tcpConnect) {
                LogUtils.loge("连接失败，重试" + (contentTime - 1));
                send(msg);
            }
        }
        return true;
    }

    int contentTime = 0;

    public boolean send(byte[] bytes) {
        if (os != null) {
            try {
                os.write(bytes);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LogUtils.loge("run: 发送成功:"  );
        } else {
            if (contentTime > 9) {
                LogUtils.loge("连接失败");
                return false;
            }
            boolean tcpConnect = getTcpConnect();
            if (tcpConnect) {
                LogUtils.loge("连接失败，重试" + (contentTime - 1));
                send(bytes);
            }
        }
        return true;
    }


    private boolean getTcpConnect() {
        contentTime++;
        try {
            socket = new Socket(serverIP, serverPort);
            socket.setSoTimeout(5000);
            os = socket.getOutputStream();
            pw = new PrintWriter(os, true);
            is = socket.getInputStream();
            dis = new DataInputStream(is);
            LogUtils.loge("run: 连接成功:");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge("run: 连接失败:" + e.toString());
            return false;
        }
    }

    @Override
    public void run() {
        getTcpConnect();
        while (isRun) {
            try {
                if (dis != null) {
                    rcvLen = dis.read(buff);
                    if (rcvLen < 0) {
                        EventBus.getDefault().post(new TcpClientMessage(rcvMsg, TcpClientMessage.SERVER_CLOSE));
                        return;
                    }
                    byte[] buff1=new byte[rcvLen];
                    //src: 源数组
                    //srcPos: 从源数组复制数据的起始位置
                    //dest: 目标数组
                    //destPos: 复制到目标数组的起始位置
                    //length: 复制的长度
                    System.arraycopy(buff, 0, buff1, 0, rcvLen);


                    rcvMsg = new String(buff, 0, rcvLen, "utf-8");
                    LogUtils.loge("run: 收到消息:" + rcvMsg);
                    EventBus.getDefault().post(new TcpClientMessage(rcvMsg, TcpClientMessage.SERVER_OPEN));
                    EventBus.getDefault().post(new TcpClientMessage(buff1, TcpClientMessage.SERVER_OPEN));
                    if (rcvMsg.equals("QuitClient")) {
                        //服务器要求客户端结束
                        isRun = false;
                        LogUtils.loge("连接被断开");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (pw != null) {
                pw.close();
            }
            if (is != null) {
                is.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
