package com.lixiaomi.baselibapplication.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.message.TcpMessage;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.utils.tcp.TcpServer;
import com.lixiaomi.mvplib.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class TcpServerActivity extends XMBaseActivity implements View.OnClickListener {
    private Button btnStartServer, btnCloseServer, btnCleanServerSend, btnCleanServerRcv, btnServerSend;
    private TextView txtRcv, txtSend, txtServerIp;
    private EditText editServerSend, editServerPort;
    private static TcpServer tcpServer = null;
    ExecutorService exec = Executors.newCachedThreadPool();


    private int getHost(String msg) {
        if (msg.equals("")) {
            msg = "8888";
        }
        return Integer.parseInt(msg);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(TcpMessage message) {
        LogUtils.loge("UI收到消息:" + message.getMessage());
        message.getBuff();
        txtRcv.append(message.getBuff().toString());
        txtRcv.append("\n");
    }


    @Override
    protected Object setLayout() {
        return R.layout.tcp_server;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btnStartServer = (Button) findViewById(R.id.btn_tcpServerConn);
        btnCloseServer = (Button) findViewById(R.id.btn_tcpServerClose);
        btnCleanServerRcv = (Button) findViewById(R.id.btn_tcpCleanServerRecv);
        btnCleanServerSend = (Button) findViewById(R.id.btn_tcpCleanServerSend);
        btnServerSend = (Button) findViewById(R.id.btn_tcpServerSend);
        txtRcv = (TextView) findViewById(R.id.txt_ServerRcv);
        txtSend = (TextView) findViewById(R.id.txt_ServerSend);
        txtServerIp = (TextView) findViewById(R.id.txt_Server_Ip);
        editServerSend = (EditText) findViewById(R.id.edit_tcpClientSend);
        editServerPort = (EditText) findViewById(R.id.edit_Server_Port);

        btnStartServer.setOnClickListener(this);
        btnCloseServer.setOnClickListener(this);
        btnCleanServerRcv.setOnClickListener(this);
        btnCleanServerSend.setOnClickListener(this);
        btnServerSend.setOnClickListener(this);

        ini();
    }


    private void ini() {
        btnCloseServer.setEnabled(false);
        btnServerSend.setEnabled(false);
        txtServerIp.setText(getHostIP());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tcpServerConn:
                //启动服务器
                Log.i("A", "onClick: 开始");
                btnStartServer.setEnabled(false);
                btnCloseServer.setEnabled(true);
                btnServerSend.setEnabled(true);
                tcpServer = new TcpServer(getHost(editServerPort.getText().toString()));
                exec.execute(tcpServer);
                break;
            case R.id.btn_tcpServerClose:
                //关闭服务器
                tcpServer.closeSelf();
                btnStartServer.setEnabled(true);
                btnCloseServer.setEnabled(false);
                btnServerSend.setEnabled(false);
                break;
            case R.id.btn_tcpCleanServerRecv:
                //清空接收区
                txtRcv.setText("");
                break;
            case R.id.btn_tcpCleanServerSend:
                //清空发送区
                txtSend.setText("");
                break;
            case R.id.btn_tcpServerSend:
                //发送
                final String sendMessage = editServerSend.getText().toString();
                txtSend.append(sendMessage);
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        int size = tcpServer.SST.size();
                        for (int i = 0; i < size; i++) {
                            tcpServer.SST.get(i).send(sendMessage);
                        }
                    }
                });
                editServerSend.setText("");
                break;
        }
    }


    /**
     * 获取ip地址
     *
     * @return
     */
    public String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("TcpServerActivity", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }


    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

    @Override
    protected void onDestroy() {
        if (tcpServer != null) {
            tcpServer.closeSelf();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
