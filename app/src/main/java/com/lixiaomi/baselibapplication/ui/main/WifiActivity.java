package com.lixiaomi.baselibapplication.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lixiaomi.baselib.ui.dialog.MiDialog;
import com.lixiaomi.baselib.ui.dialog.MiPremissionDialog;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.adapter.WifiListAdapter;
import com.lixiaomi.baselibapplication.bean.ScanResultBean;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.lixiaomi.baselibapplication.utils.WifiSupport;
import com.lixiaomi.mvplib.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @describe：检查wifi列表，自动加入wifi<br>
 * @author：Xiaomi<br>
 * @createTime：2019/2/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class WifiActivity extends XMBaseActivity implements EasyPermissions.PermissionCallbacks {

    private android.support.v7.widget.RecyclerView mWifiRecycler;
    private android.widget.Button mAddWifiBut;
    private android.widget.Button mSeaarchWifiBut;
    private Toolbar mToolbar;
    private TextView mTitle;

    private WifiListAdapter mWifiListAdapter;
    /**
     * 目标wifi名称
     */
    private String mWifiName = "HZJX_0204_0087060a";
//    private String mWifiName = "HZ1104";
    /**
     * 目标wifi密码
     */
    private String mWifiPassword = "HZJX_1Z2Y3X4W";
//    private String mWifiPassword = "hanzhi2015123";

    /**
     * wifi列表
     */
    private List<ScanResultBean> realWifiList = new ArrayList<>();
    /**
     * 是否链接状态
     */
    public static final String WIFI_STATE_CONNECT = "已连接";
    public static final String WIFI_STATE_ON_CONNECTING = "正在连接";
    public static final String WIFI_STATE_UNCONNECT = "未连接";

    /**
     * 获取wifi列表需要的权限
     * 通过GPS芯片接收卫星的定位信息，定位精度达10米以内
     * 通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米
     */
    private final String[] WIFI_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 权限代码
     */
    private final int WIFI_PERMISSIONS_CODE = 0X91;
    /**
     * 去设置页面返回的代码
     */
    private final int WIFI_PERMISSIONS_SET_CODE = 0X92;

    @Override
    protected Object setLayout() {
        return R.layout.activity_wifi;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        WifiSupport.openWifi(WifiActivity.this);
        mToolbar = findViewById(R.id.top_toolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle = findViewById(R.id.top_title_tv);
        mTitle.setText("自动加入wifi");
        mWifiRecycler = findViewById(R.id.wifi_recycler);
        mAddWifiBut = findViewById(R.id.add_wifi_but);
        mSeaarchWifiBut = findViewById(R.id.search_wifi_but);

        mWifiRecycler.setLayoutManager(new LinearLayoutManager(this));
        mWifiListAdapter = new WifiListAdapter(R.layout.item_wifi_list);
        mWifiRecycler.setAdapter(mWifiListAdapter);
        hasPermissions();

        mSeaarchWifiBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPermissions();
            }
        });

        mWifiListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final ScanResultBean scanResultBean = realWifiList.get(position);
                final String wifiName = scanResultBean.getWifiName();
                WifiConfiguration tempConfig = WifiSupport.isExsits(wifiName, WifiActivity.this);

                if (tempConfig != null) {
                    boolean b = WifiSupport.addNetWork(tempConfig, WifiActivity.this);
                    if (b) {
                        T.shortToast(WifiActivity.this, "连接成功");
                    } else {
                        T.shortToast(WifiActivity.this, "连接失败");
                    }
                } else {
                    new MiDialog(WifiActivity.this, MiDialog.EDIT_TYPE)
                            .builder()
                            .setMsg(mWifiPassword)
                            .setCannleButton("取消", null)
                            .setOkButton("确定", new MiDialog.DialogCallBack() {
                                @Override
                                public void dialogCallBack(String connect) {
//                                    String wifiName1 = wifiName.substring(1, wifiName.length() - 1);
                                    T.shortToast(WifiActivity.this, "连接wifi");
                                    LogUtils.loge("wifiName1:" + wifiName + "wifipassword:" + connect);
                                    WifiConfiguration wifiConfiguration = WifiSupport.createWifiConfig(wifiName, connect, WifiSupport.getWifiCipher(scanResultBean.getCapabilities()));
//                                    WifiSupport.connectWifi(WifiActivity.this, wifiName, mWifiPassword, scanResultBean.getEncryptTypeCode());
                                    boolean b = WifiSupport.addNetWork(wifiConfiguration, WifiActivity.this);
//                                    if (b) {
//                                        T.shortToast(WifiActivity.this, "连接成功");
//                                    } else {
//                                        T.shortToast(WifiActivity.this, "连接失败");
//                                    }
                                }
                            })
                            .show();
                }
            }
        });
    }

    /**
     * 检查权限
     */
    private void hasPermissions() {
        //6.0+版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean b = EasyPermissions.hasPermissions(WifiActivity.this, WIFI_PERMISSIONS);
            if (b) {
                getWifiList();
            } else {
                EasyPermissions.requestPermissions(WifiActivity.this, "获取附近wifi列表", WIFI_PERMISSIONS_CODE, WIFI_PERMISSIONS);
            }
        } else {
            getWifiList();
        }
    }

    /**
     * 获取WIFI列表
     * <p>需要权限{@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/> <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     * <p>注意Android6.0上需要主动申请定位权限，并且打开定位开关</p>
     *
     * @param context 上下文
     * @return wifi列表
     */
    public static List<ScanResult> getWifiList(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wm.getScanResults();

        Collections.sort(scanResults, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult scanResult1, ScanResult scanResult2) {
                return scanResult2.level - scanResult1.level;
            }
        });
        return scanResults;
    }

    /**
     * 获取wifi列表
     */
    private void getWifiList() {

        LogUtils.loge("获取wifi列表");
        realWifiList.clear();

//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        List<ScanResult> scanResults = wifiManager.getScanResults();
        List<ScanResult> wifiList = getWifiList(WifiActivity.this);
        LogUtils.loge("wifi数量：" + wifiList.size());
        for (ScanResult scanResult : wifiList) {
            ScanResultBean scanResultBean = new ScanResultBean();
            scanResultBean.setLevel(scanResult.level);
            scanResultBean.setWifiName(scanResult.SSID);
            //只要获取都假设设置成未连接，真正的状态都通过广播来确定
            scanResultBean.setState(WIFI_STATE_UNCONNECT);
            scanResultBean.setCapabilities(scanResult.capabilities);
            scanResultBean.setEncryptTypeStr(WifiSupport.getWifiEncryptTypeStr(scanResult.capabilities));
            scanResultBean.setEncryptTypeCode(WifiSupport.getWifiEncryptType(scanResult.capabilities));
            realWifiList.add(scanResultBean);
        }
        mWifiListAdapter.setNewData(realWifiList);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == WIFI_PERMISSIONS_CODE) {
            hasPermissions();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == WIFI_PERMISSIONS_CODE) {
            MiPremissionDialog.showPermissionDialog(WifiActivity.this, "Gsp", WIFI_PERMISSIONS_SET_CODE);
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == WIFI_PERMISSIONS_SET_CODE) {
            hasPermissions();
        }
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.default_color;
    }

}
