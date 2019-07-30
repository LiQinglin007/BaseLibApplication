package com.lixiaomi.baselibapplication.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.message.TcpClientMessage;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.utils.tcp.TcpClient;
import com.lixiaomi.mvplib.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class TcpClientActivity extends XMBaseActivity implements View.OnClickListener {
    private Button btnStartClient, btnCloseClient, btnCleanClientSend, btnCleanClientRcv, btnClientSend;
    private TextView txtRcv, txtSend;
    private EditText editClientSend, editClientPort, editClientIp;
    private static TcpClient tcpClient = null;
    ExecutorService exec = Executors.newCachedThreadPool();

    private int getPort(String msg) {
        if (msg.equals("")) {
            msg = "1234";
        }
        return Integer.parseInt(msg);
    }


    @Override
    protected Object setLayout() {
        return R.layout.tcp_client;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(TcpClientMessage tcpMessage) {
        if (tcpMessage.getMessageType() == TcpClientMessage.SERVER_OPEN) {
            LogUtils.loge("收到消息：" + tcpMessage.getMessage());
            txtRcv.append(tcpMessage.getMessage());
        } else if (tcpMessage.getMessageType() == TcpClientMessage.SERVER_CLOSE) {
            txtRcv.setText("服务器断开");
            Ini();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btnStartClient = (Button) findViewById(R.id.btn_tcpClientConn);
        btnCloseClient = (Button) findViewById(R.id.btn_tcpClientClose);
        btnCleanClientRcv = (Button) findViewById(R.id.btn_tcpCleanClientRecv);
        btnCleanClientSend = (Button) findViewById(R.id.btn_tcpCleanClientSend);
        btnClientSend = (Button) findViewById(R.id.btn_tcpClientSend);
        editClientPort = (EditText) findViewById(R.id.edit_tcpClientPort);
        editClientIp = (EditText) findViewById(R.id.edit_tcpClientIp);
        editClientSend = (EditText) findViewById(R.id.edit_tcpClientSend);
        txtRcv = (TextView) findViewById(R.id.txt_ClientRcv);
        txtSend = (TextView) findViewById(R.id.txt_ClientSend);

        btnStartClient.setOnClickListener(this);
        btnCloseClient.setOnClickListener(this);
        btnCleanClientRcv.setOnClickListener(this);
        btnCleanClientSend.setOnClickListener(this);
        btnClientSend.setOnClickListener(this);

        Ini();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tcpClientConn:
                Log.i(TAG, "onClick: 开始");
                txtRcv.setText("");
                txtSend.setText("");
                editClientSend.setText("");
                btnStartClient.setEnabled(false);
                btnCloseClient.setEnabled(true);
                btnClientSend.setEnabled(true);
                    tcpClient = new TcpClient(editClientIp.getText().toString(), getPort(editClientPort.getText().toString()));
                    exec.execute(tcpClient);
                break;
            case R.id.btn_tcpClientClose:
                if (tcpClient != null) {
                    tcpClient.closeSelf();
                }
                btnStartClient.setEnabled(true);
                btnCloseClient.setEnabled(false);
                btnClientSend.setEnabled(false);
                break;
            case R.id.btn_tcpCleanClientRecv:
                txtRcv.setText("");
                break;
            case R.id.btn_tcpCleanClientSend:
                txtSend.setText("");
                break;
            case R.id.btn_tcpClientSend:
                final String sendMessage = editClientSend.getText().toString();
                if (tcpClient != null) {
                    txtSend.append(sendMessage);

                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            tcpClient.send(sendMessage);
                        }
                    });
                    editClientSend.setText("");
                }
                break;
            default:
                break;
        }
    }

    private void Ini() {
        btnCloseClient.setEnabled(false);
        btnClientSend.setEnabled(false);
        btnStartClient.setEnabled(true);
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
        super.onDestroy();
        if (tcpClient != null) {
            tcpClient.closeSelf();
        }
        EventBus.getDefault().unregister(this);
    }
}
