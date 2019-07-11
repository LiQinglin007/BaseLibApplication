package com.lixiaomi.baselibapplication.utils.tcp;

import com.google.gson.Gson;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.message.TcpMessage;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class TcpServer implements Runnable {
    private String TAG = "TcpServer";
    private int port = 8888;
    //线程监听标志位
    private boolean isListen = true;
    public ArrayList<ServerSocketThread> SST = new ArrayList<ServerSocketThread>();

    public TcpServer(int port) {
        this.port = port;
    }

    //更改监听标志位
    public void setIsListen(boolean b) {
        isListen = b;
    }

    public void closeSelf() {
        isListen = false;
        for (ServerSocketThread s : SST) {
            s.isRun = false;
        }
        SST.clear();
    }

    private Socket getSocket(ServerSocket serverSocket) {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.loge(TAG, "开始监听...");
            return null;
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(5000);
            while (isListen) {
                LogUtils.loge(TAG, "开始监听...");
                Socket socket = getSocket(serverSocket);
                if (socket != null) {
                    new ServerSocketThread(socket);
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ServerSocketThread extends Thread {
        Socket socket = null;
        private PrintWriter pw;
        private InputStream is = null;
        private OutputStream os = null;
        private String ip = null;
        private boolean isRun = true;

        ServerSocketThread(Socket socket) {
            this.socket = socket;
            ip = socket.getInetAddress().toString();
            LogUtils.loge(TAG, "检测到新的客户端联入,ip:" + ip);
            try {
                socket.setSoTimeout(5000);
                os = socket.getOutputStream();
                is = socket.getInputStream();
                pw = new PrintWriter(os, true);
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String msg) {
            pw.println(msg);
            pw.flush(); //强制送出数据
        }

        @Override
        public void run() {
            byte buff[] = new byte[512];
            String rcvMsg;
            int rcvLen;
            SST.add(this);
            while (isRun && !socket.isClosed() && !socket.isInputShutdown()) {
                try {
                    if ((rcvLen = is.read(buff)) != -1) {
                        byte buff1[] = new byte[rcvLen];
                        System.arraycopy(buff, 0, buff1, 0, rcvLen);
                        rcvMsg = new String(buff, 0, rcvLen, "utf-8");
                        LogUtils.loge("收到消息:" + new Gson().toJson(buff1));
                        TcpMessage tcpMessage = new TcpMessage();
                        tcpMessage.setMessage(rcvMsg);
                        tcpMessage.setBuff(buff1);
                        EventBus.getDefault().post(tcpMessage);
                        if (rcvMsg.equals("QuitServer")) {
                            isRun = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
                SST.clear();
                LogUtils.loge(TAG, "run:断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
