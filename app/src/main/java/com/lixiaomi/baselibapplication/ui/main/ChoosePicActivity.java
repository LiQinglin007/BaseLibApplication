package com.lixiaomi.baselibapplication.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.ui.dialog.MiPremissionDialog;
import com.lixiaomi.baselib.utils.FileUtil;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/4
 * 内容：
 * 最后修改：
 */
public class ChoosePicActivity extends XMBaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {
    BGASortableNinePhotoLayout mBGASortableNinePhotoLayout;

    @Override
    protected Object setLayout() {
        return R.layout.activity_choosepic;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBGASortableNinePhotoLayout = findViewById(R.id.snpl_moment_add_photos);
        mBGASortableNinePhotoLayout.setMaxItemCount(9);
        // 设置拖拽排序控件的代理
        mBGASortableNinePhotoLayout.setDelegate(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //被同意的
        if (requestCode == PERMISSION_CODE && perms != null && perms.size() != 0) {
            if (perms.contains(permission[0]) && perms.contains(permission[1]) && perms.contains(permission[2])) {
                hasPermission();
            } else {
                MiPremissionDialog.showPermissionDialog(ChoosePicActivity.this, "相机、读写文件", PERMISSION_SETTING_CODE);
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_CODE) {
            MiPremissionDialog.showPermissionDialog(ChoosePicActivity.this, "相机、读写文件", PERMISSION_SETTING_CODE);
        }
    }

    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int PERMISSION_CODE = 0X15;
    int PERMISSION_SETTING_CODE = 0X16;

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(ChoosePicActivity.this, permission)) {
                hasPermission();
            } else {
                //如果被拒绝过了
                if (EasyPermissions.somePermissionDenied(ChoosePicActivity.this, permission)) {
                    MiPremissionDialog.showPermissionDialog(ChoosePicActivity.this, "相机、读写文件", PERMISSION_SETTING_CODE);
                } else {
                    //没有被拒绝过，就去申请权限
                    EasyPermissions.requestPermissions(ChoosePicActivity.this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PERMISSION_CODE, permission);
                }
            }
        } else {
            hasPermission();
        }
    }


    private void hasPermission() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), FileUtil.TAKE_PHOTO_DIR);
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mBGASortableNinePhotoLayout.getMaxItemCount()) // 图片选择张数的最大值
                .selectedPhotos(mBGASortableNinePhotoLayout.getData()) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
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
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        //添加按钮
        checkPermission();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        //删除
        mBGASortableNinePhotoLayout.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mBGASortableNinePhotoLayout.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Toast.makeText(this, "排序发生变化", Toast.LENGTH_SHORT).show();
    }

    int RC_PHOTO_PREVIEW = 0x12;
    int RC_CHOOSE_PHOTO = 0x13;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_SETTING_CODE) {
            checkPermission();
        }
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mBGASortableNinePhotoLayout.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mBGASortableNinePhotoLayout.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }
}
