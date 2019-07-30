package com.lixiaomi.baselib.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lixiaomi.baselib.ui.dialog.MiPremissionDialog;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @describe：申请权限工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/11<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class PermissionsUtil {

    private static PermissionCallBack mPermissionCallBack;
    private static String mPermissionDescribe;
    private static String[] mPermissions;
    /**
     * 申请动态权限码
     */
    public static final int PERMISSIONSUTIL_REQUEST_CODE = 0x5118;
    /**
     * 去设置页面的code
     */
    public static final int PERMISSIONSUTIL_SETTING_CODE = 0x5119;

    /**
     * 检查权限
     *
     * @param fragment
     * @param permissions
     * @param permissionsDescribe 申请权限的描述
     * @param permissionCallBack
     */
    public static void getPermission(@NonNull Fragment fragment, @NonNull String[] permissions, @NonNull String permissionsDescribe, @NonNull PermissionCallBack permissionCallBack) {
        PermissionsUtil.mPermissionCallBack = permissionCallBack;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0才用动态权限
            mPermissionCallBack.onSuccess();
            return;
        }
        mPermissionDescribe = permissionsDescribe;
        mPermissions = permissions;
        //是否有全部权限
        if (EasyPermissions.hasPermissions(fragment.getContext(), permissions)) {
            //说明权限都已经通过，可以做你想做的事情去
            mPermissionCallBack.onSuccess();
        } else {
            //检查权限是不是完全被拒绝
            if (EasyPermissions.somePermissionDenied(fragment, permissions)) {
                //有完全被拒绝的，就弹框去让用户手动打开
                MiPremissionDialog.showPermissionDialog(fragment, mPermissionDescribe, PERMISSIONSUTIL_SETTING_CODE);
            } else {
                EasyPermissions.requestPermissions(fragment, permissionsDescribe, PERMISSIONSUTIL_REQUEST_CODE, permissions);
            }
        }
    }


    /**
     * 请求权限后回调的方法
     *
     * @param fragment
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  申请权限的描述
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //是不是有权限被拒绝
        boolean hasPermissionDismiss = false;
        if (PERMISSIONSUTIL_REQUEST_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                MiPremissionDialog.showPermissionDialog(fragment, mPermissionDescribe, PERMISSIONSUTIL_SETTING_CODE);
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionCallBack.onSuccess();
            }
        }
    }


    public static void onActivityResult(Fragment fragment, int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PermissionsUtil.PERMISSIONSUTIL_SETTING_CODE) {
            getPermission(fragment, mPermissions, mPermissionDescribe, mPermissionCallBack);
        }
    }


    /**
     * 检查权限
     *
     * @param activity
     * @param permissions
     * @param permissionsDescribe 申请权限的描述
     * @param permissionCallBack
     */
    public static void getPermission(@NonNull Activity activity, @NonNull String[] permissions, @NonNull String permissionsDescribe, @NonNull PermissionCallBack permissionCallBack) {
        PermissionsUtil.mPermissionCallBack = permissionCallBack;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0才用动态权限
            mPermissionCallBack.onSuccess();
            return;
        }
        mPermissionDescribe = permissionsDescribe;
        mPermissions = permissions;
        //是否有全部权限
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            //说明权限都已经通过，可以做你想做的事情去
            mPermissionCallBack.onSuccess();
        } else {
            //检查权限是不是完全被拒绝
            if (EasyPermissions.somePermissionDenied(activity, permissions)) {
                //有完全被拒绝的，就弹框去让用户手动打开
                MiPremissionDialog.showPermissionDialog(activity, mPermissionDescribe, PERMISSIONSUTIL_SETTING_CODE);
            } else {
                EasyPermissions.requestPermissions(activity, permissionsDescribe, PERMISSIONSUTIL_REQUEST_CODE, permissions);
            }
        }
    }


    /**
     * 请求权限后回调的方法
     *
     * @param activity
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  申请权限的描述
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //是不是有权限被拒绝
        boolean hasPermissionDismiss = false;
        if (PERMISSIONSUTIL_REQUEST_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                MiPremissionDialog.showPermissionDialog(activity, mPermissionDescribe, PERMISSIONSUTIL_SETTING_CODE);
            } else {
                //全部权限通过，可以进行下一步操作。。。
                mPermissionCallBack.onSuccess();
            }
        }
    }


    public static void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PermissionsUtil.PERMISSIONSUTIL_SETTING_CODE) {
            getPermission(activity, mPermissions, mPermissionDescribe, mPermissionCallBack);
        }
    }

    /**
     * 检查权限的回调
     */
    public interface PermissionCallBack {
        void onSuccess();

        void onFail();
    }
}
