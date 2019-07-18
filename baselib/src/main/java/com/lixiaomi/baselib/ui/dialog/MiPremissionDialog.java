package com.lixiaomi.baselib.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;

/**
 * @describe：提醒用户开启权限弹框<br>
 * @author：Xiaomi<br>
 * @createTime：2018/1/17<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MiPremissionDialog {

    /**
     * 提示用户前往设置页面的  mDialog
     *
     * @param mActivity
     * @param message   提示内容，只传进来关键字就好
     * @param code      去设置页面的code
     */
    public static void showPermissionDialog(final Activity mActivity, String message, final int code) {
        new MiDialog(mActivity, MiDialog.MESSAGE_TYPE)
                .builder()
                .setTitle("权限提醒")
                .setMsg(message)
                .setOkButton("去设置", new MiDialog.DialogCallBack() {
                    @Override
                    public void dialogCallBack(String connect) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                        intent.setData(uri);
                        mActivity.startActivityForResult(intent, code);
                    }
                })
                .setCannleButton("取消", null)
                .show();
    }


    /**
     * 提示用户前往设置页面的  mDialog
     *
     * @param mFragment
     * @param message   提示内容，只传进来关键字就好
     * @param code      去设置页面的code
     */
    public static void showPermissionDialog(final Fragment mFragment, String message, final int code) {
        new MiDialog(mFragment.getActivity(), MiDialog.MESSAGE_TYPE)
                .builder()
                .setTitle("权限提醒")
                .setMsg(message)
                .setOkButton("去设置", new MiDialog.DialogCallBack() {
                    @Override
                    public void dialogCallBack(String connect) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mFragment.getContext().getPackageName(), null);
                        intent.setData(uri);
                        mFragment.startActivityForResult(intent, code);
                    }
                })
                .setCannleButton("取消", null)
                .show();
    }
}
