package com.lixiaomi.baselibapplication.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.google.gson.Gson;
import com.lixiaomi.baselib.utils.BaseAppManager;

import com.lixiaomi.baselib.eventmessage.MiEventMessage;
import com.lixiaomi.baselib.net.DownloadListener;
import com.lixiaomi.baselib.net.okhttp.DownloadUtil;
import com.lixiaomi.baselib.ui.Loading.LoaderStyle;
import com.lixiaomi.baselib.ui.chooseDateUtils.MiDayTime;
import com.lixiaomi.baselib.ui.chooseDateUtils.MiDiDiTime;
import com.lixiaomi.baselib.ui.chooseDateUtils.MiMinTime;
import com.lixiaomi.baselib.ui.dialog.MiBottomDialog;
import com.lixiaomi.baselib.ui.dialog.MiDialog;
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiDialogList;
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface;
import com.lixiaomi.baselib.utils.FileUtil;
import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.MiDateUtils;
import com.lixiaomi.baselib.utils.MiRandomUtils;
import com.lixiaomi.baselib.utils.PermissionsUtil;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselib.utils.loadImageUtils.MiLoadImageUtil;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.bean.NoticeBean;
import com.lixiaomi.baselibapplication.bean.UserBean;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseFragment;
import com.lixiaomi.baselibapplication.ui.mDesign.MyDesignMainActivity;
import com.lixiaomi.baselibapplication.utils.NotificationUtil;
import com.lixiaomi.mvplib.base.BasePresenter;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;


/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/4
 * 内容：
 * 最后修改：
 */

public class MineFragment extends XMBaseFragment implements View.OnClickListener {

    public static MineFragment getInstance() {
        return MineFragmentHolder.mineFragment;
    }

    ArrayList<MiListInterface> mBeanArrayList = new ArrayList<>();
    private int REQUEST_CODE_TAKE_PHOTO = 0x77;
    private int REQUEST_CODE_CHOOSE_PHOTO = 0x78;
    private BGAPhotoHelper mPhotoHelper;
    int RC_CHOOSE_PHOTO = 0x17;
    int REQUEST_CODE_CROP = 0x66;

    private final static class MineFragmentHolder {
        final static MineFragment mineFragment = new MineFragment();
    }

    @Override
    protected Object setLayout() {
        return R.layout.fragment_mine;
    }


    AppCompatImageView takePic;
    SwitchCompat takePicSwitch;
    AppCompatTextView chooseTime;
    AppCompatTextView chooseTimeDidi;
    AppCompatTextView chooseTimeMin;
    SwitchCompat chooseTimeSwitch;
    SwitchCompat chooseSingOutSwitch;
    AppCompatTextView chooseSex;
    AppCompatTextView designUi;
    AppCompatTextView bottomList;
    AppCompatTextView bottomUserList;
    AppCompatTextView noticeBtn;
    /**
     * 发布朋友圈
     */
    AppCompatTextView choosePic;
    AppCompatTextView mHttpUtil;
    /**
     * 退出
     */
    AppCompatTextView singOutTv;
    AppCompatTextView switchFragmentTv;
    /**
     * 权限
     */
    AppCompatTextView permissionTv;
    AppCompatTextView downloadTv;


    @Override
    protected void initData() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), FileUtil.TAKE_PHOTO_DIR);
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        takePic = rootView.findViewById(R.id.mine_take_pic);
        takePicSwitch = rootView.findViewById(R.id.mine_take_pic_sw);
        chooseTime = rootView.findViewById(R.id.mine_choosetime);
        chooseTimeDidi = rootView.findViewById(R.id.mine_choose_time_didi);
        chooseTimeMin = rootView.findViewById(R.id.mine_choose_time_min);
        chooseTimeSwitch = rootView.findViewById(R.id.mine_choosetime_sw);
        chooseSex = rootView.findViewById(R.id.mine_choosesex);
        designUi = rootView.findViewById(R.id.mine_design_ui);
        bottomList = rootView.findViewById(R.id.mine_bottom_list);
        bottomUserList = rootView.findViewById(R.id.mine_bottom_userlist);
        noticeBtn = rootView.findViewById(R.id.mine_notice);
        mHttpUtil = rootView.findViewById(R.id.mine_http);
        choosePic = rootView.findViewById(R.id.mine_choose_pic);
        singOutTv = rootView.findViewById(R.id.mine_sing_out);
        chooseSingOutSwitch = rootView.findViewById(R.id.mine_sing_out_sw);
        switchFragmentTv = rootView.findViewById(R.id.mine_switch_fragment);
        permissionTv = rootView.findViewById(R.id.mine_permission);
        downloadTv = rootView.findViewById(R.id.mine_download);


        takePic.setOnClickListener(this);
        chooseTime.setOnClickListener(this);
        chooseTimeDidi.setOnClickListener(this);
        chooseTimeMin.setOnClickListener(this);
        chooseSex.setOnClickListener(this);
        designUi.setOnClickListener(this);
        bottomList.setOnClickListener(this);
        bottomUserList.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        choosePic.setOnClickListener(this);
        singOutTv.setOnClickListener(this);
        mHttpUtil.setOnClickListener(this);
        switchFragmentTv.setOnClickListener(this);
        permissionTv.setOnClickListener(this);
        downloadTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_http:
                showLoading(R.color.warning_color3, LoaderStyle.PacmanIndicator);
                startActivity(new Intent(getActivity(), MyHttpActivity.class));
                break;
            case R.id.mine_take_pic:
                //拍照
                if (takePicSwitch.isChecked()) {
                    new MiBottomDialog(getActivity())
                            .setCancelable(true)
                            .setCanceledOnTouchOutside(true)
                            .addSheetItem(getActivity().getResources().getString(R.string.choose_from_camera_text), MiBottomDialog.SheetItemColor.Blue,
                                    new MiBottomDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            try {
                                                startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                            .addSheetItem(getActivity().getResources().getString(R.string.choose_from_phone_text), MiBottomDialog.SheetItemColor.Blue,
                                    new MiBottomDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_CHOOSE_PHOTO);
                                        }
                                    }).show();
                } else {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    PermissionsUtil.getPermission(MineFragment.getInstance(), permission, "此功能需要读写SD卡、相机权限", new PermissionsUtil.PermissionCallBack() {
                        @Override
                        public void onSuccess() {
                            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), FileUtil.TAKE_PHOTO_DIR);
                            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(getActivity())
                                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                                    .maxChooseCount(1) // 图片选择张数的最大值
                                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                                    .build();
                            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
                break;
            case R.id.mine_choosetime:
                //选择时间
                final boolean chooseTimeSw = chooseTimeSwitch.isChecked();
                Date startDate;
                Date endDate;
                Date selectDate;
                if (chooseTimeSw) {
                    //今天以后
                    startDate = new Date();
                    selectDate = MiDateUtils.getDate(2020, 4, 28);
                    endDate = MiDateUtils.getDate(2038, 5, 30);
                } else {
                    selectDate = MiDateUtils.getDate(2018, 4, 28);
                    startDate = MiDateUtils.getDate(2018, 4, 1);
                    endDate = new Date();
                }
                new MiDayTime(getActivity())
                        .builder()
                        .setStartDate(startDate)
                        .setEndDate(endDate)
                        .setSelectDate(selectDate)
                        .setType(MiDayTime.YEAR)
                        .setTvColor(R.color.warning_color5)
                        .setCallBack(new MiDayTime.TimeDialogCallBack() {
                            @Override
                            public void callback(String year, String month, String day) {
                                chooseTime.setText(chooseTimeSw ? "今天以后:" + year + month + day : "今天以前:" + year + month + day);
                            }
                        })
                        .show();
                break;
            case R.id.mine_choose_time_min:
                Date date = new Date();
                int hours = date.getHours();
                int minutes = date.getMinutes();
                new MiMinTime(getActivity())
                        .builder()
                        .setSelectHour(hours)
                        .setSelectMin(minutes)
                        .setTextColor(R.color.warning_color1)
                        .setTimeDialogCallBack(new MiMinTime.TimeDialogCallBack() {
                            @Override
                            public void callback(String min, String hour) {
                                chooseTimeMin.setText("选择时间(分钟)：    " + hour + "：" + min);
                            }
                        })
                        .show();
                break;
            case R.id.mine_choose_time_didi:
                new MiDiDiTime(getActivity())
                        .builder()
                        .setStartHour(2)
                        .setEndHour(20)
                        .setDayNumber(5)
                        .setTextColor(R.color.warning_color3)
                        .setTimeDialogCallBack(new MiDiDiTime.TimeDialogCallBack() {
                            @Override
                            public void callback(String dateWeek, String date, String time) {
                                chooseTimeDidi.setText("选择预约时间(仿滴滴)：    " + dateWeek + time + "时");
                                LogUtils.logd("选择预约时间(仿滴滴)：    " + date);
                            }
                        })
                        .show();
                break;
            case R.id.mine_choosesex:
                //选择性别
                final ArrayList<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                new MiDialogList(getActivity())
                        .builder()
                        .setTitle("选择性别")
                        .setData(sexList)
                        .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                        .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> position) {
                                chooseSex.setText("选择性别:" + sexList.get(position.get(0)));
                            }
                        })
                        .show();
                break;
            case R.id.mine_design_ui:
                //Design包下的UI
                startActivity(new Intent(getActivity(), MyDesignMainActivity.class));
                break;
            case R.id.mine_bottom_list:
                //中间弹出dialogList
                mBeanArrayList.clear();
                for (int i = 0; i < 20; i++) {
                    mBeanArrayList.add(new NoticeBean(null, "标题巴拉巴拉" + i, "内容巴拉巴拉" + i));
                }
                new MiDialogList(getActivity())
                        .builder()
                        .setData(mBeanArrayList)
                        .setTitle("标题巴拉巴拉")
                        .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                        .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> position) {
                                int size = position.size();
                                for (int i = 0; i < size; i++) {
                                    LogUtils.loge(TAG, new Gson().toJson(mBeanArrayList.get(position.get(i))));
                                }
                                T.shortToast(getActivity(), "您选择了" + size + "个用户，去控制台查看详情吧");

                            }
                        })
                        .show();
                break;
            case R.id.mine_bottom_userlist:
                mBeanArrayList.clear();
                for (int i = 0; i < 20; i++) {
                    mBeanArrayList.add(new UserBean("用户名称巴拉巴拉" + i, "用户性别巴拉巴拉" + i));
                }
                new MiDialogList(getActivity())
                        .builder()
                        .setData(mBeanArrayList)
                        .setTitle("用户名称巴拉巴拉")
                        .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                        .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> position) {
                                T.shortToast(getActivity(), new Gson().toJson(mBeanArrayList.get(position.get(0))));
                            }
                        })
                        .show();
                break;
            case R.id.mine_notice:
                showNotifictionIcon(getActivity());
                break;
            case R.id.mine_choose_pic:
                startActivity(new Intent(getActivity(), ChoosePicActivity.class));
                break;
            case R.id.mine_sing_out:
                if (chooseSingOutSwitch.isChecked()) {
                    new MiDialog(getActivity(), MiDialog.MESSAGE_TYPE)
                            .builder()
                            .setTitle("提示")
                            .setPassword(true)
                            .setMsg("确定要退出么？")
                            .setCannleButton("取消", null)
                            .setOkButton("退出", new MiDialog.DialogCallBack() {
                                @Override
                                public void dialogCallBack(String connect) {
                                    BaseAppManager.getInstance().clear();
                                    getActivity().finish();
                                }
                            })
                            .show();
                } else {
                    new MiDialog(getActivity(), MiDialog.EDIT_TYPE)
                            .builder()
                            .setTitle("提示")
                            .setPassword(true)
                            .setMsg("确定要退出么？")
                            .setCannleButton("取消", null)
                            .setOkButton("退出", new MiDialog.DialogCallBack() {
                                @Override
                                public void dialogCallBack(String connect) {
                                    BaseAppManager.getInstance().clear();
                                    getActivity().finish();
                                }
                            })
                            .show();
                }
                break;
            case R.id.mine_switch_fragment:
                EventBus.getDefault().post(new MiEventMessage(MiEventMessage.SWITCH_FRAGMENT, 0));
                break;
            case R.id.mine_permission:
                getActivity().startActivity(new Intent(getActivity(), PermissionsActivity.class));
                break;
            case R.id.mine_download:
                new DownloadUtil("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", "Q", new DownloadListener() {
                    @Override
                    public void downStart() {
                        LogUtils.loge("开始下载");
                    }

                    @Override
                    public void downProgress(int progress, long speed) {
                        LogUtils.loge("进度下载：" + progress + "speed:" + speed);
                    }

                    @Override
                    public void downSuccess() {
                        LogUtils.loge("下载成功");
                    }

                    @Override
                    public void downFailed(String failedDesc) {
                        LogUtils.loge("下载失败：" + failedDesc);
                    }
                });
                break;
            default:
                break;
        }
    }

    public static void showNotifictionIcon(Context context) {
        String random = MiRandomUtils.getRandom(5, MiRandomUtils.NUMBER);
        Intent mainIntent = new Intent(context, LauncherMessageNumberActivity.class);
        mainIntent.putExtra("notificationId", random);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtil.sendNotificationMyView("报警通知", "有新的报警通知" + "条", "报警通知",
                Integer.parseInt(random), R.mipmap.ic_launcher, pendingIntent, context);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtil.onRequestPermissionsResult(MineFragment.getInstance(), requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionsUtil.PERMISSIONSUTIL_SETTING_CODE) {
            PermissionsUtil.onActivityResult(MineFragment.getInstance(), requestCode, resultCode, data);
        } else if (resultCode == Activity.RESULT_OK) {
            if (takePicSwitch.isChecked()) {
                if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                    try {
                        startActivityForResult(mPhotoHelper.getCropIntent(BGAPhotoHelper.getFilePathFromUri(data.getData()), 200, 200), REQUEST_CODE_CROP);
                    } catch (Exception e) {
                        mPhotoHelper.deleteCropFile();
                        BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                        e.printStackTrace();
                    }
                } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                    try {
                        startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getCameraFilePath(), 200, 200), REQUEST_CODE_CROP);
                    } catch (Exception e) {
                        mPhotoHelper.deleteCameraFile();
                        mPhotoHelper.deleteCropFile();
                        BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                        e.printStackTrace();
                    }
                } else if (requestCode == REQUEST_CODE_CROP) {
                    MiLoadImageUtil.loadImageCircle(getActivity(), mPhotoHelper.getCropFilePath(), takePic);
                }
            } else {//如果是不裁剪
                if (requestCode == RC_CHOOSE_PHOTO) {
                    ArrayList<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                    MiLoadImageUtil.loadImageCircle(getActivity(), selectedPhotos.get(0), takePic);
                }
            }
        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
}
