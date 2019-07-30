package com.lixiaomi.baselibapplication.ui.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;


import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.PermissionsUtil;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.mvplib.base.BaseActivity;
import com.lixiaomi.mvplib.base.BasePresenter;

/**
 * @describe：申请权限工具类测试<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/18<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class PermissionsActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_permissions;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        findViewById(R.id.call_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });
    }

    String[] permisssions = {Manifest.permission.CALL_PHONE};

    private void getPermission() {
        PermissionsUtil.getPermission(PermissionsActivity.this, permisssions, "此功能需要拨打电话", new PermissionsUtil.PermissionCallBack() {
            @Override
            public void onSuccess() {
                LogUtils.loge("权限验证通过");
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:15284224244");
                intent.setData(data);
                startActivity(intent);
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionsUtil.onActivityResult(PermissionsActivity.this,requestCode, resultCode, data);
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
