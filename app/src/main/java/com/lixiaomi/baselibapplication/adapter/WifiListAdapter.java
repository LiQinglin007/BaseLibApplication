package com.lixiaomi.baselibapplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.bean.ScanResultBean;
import com.lixiaomi.baselibapplication.utils.WifiSupport;


/**
 * @describe：自动加入wifi页面,wifiList<br>
 * @author：Xiaomi<br>
 * @createTime：2019/2/28<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class WifiListAdapter extends BaseQuickAdapter<ScanResultBean, BaseViewHolder> {

    public WifiListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScanResultBean item) {
        helper.setText(R.id.item_wifi_name, "wifi名称：" + item.getWifiName()
                + " 信号强度：" + item.getLevel()
                + " 连接状态：" + item.getState()
                + " 加密方式：" + WifiSupport.getCapabilitiesString(item.getCapabilities()));
    }
}
