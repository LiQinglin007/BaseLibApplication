package com.lixiaomi.baselibapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.lixiaomi.baselib.base.BasePresenter;
import com.lixiaomi.baselib.eventmessage.MiEventMessage;
import com.lixiaomi.baselib.service.DownloadService;
import com.lixiaomi.baselib.utils.CheckStringEmptyUtils;
import com.lixiaomi.baselib.utils.MiFinalData;
import com.lixiaomi.baselib.utils.T;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseActivity;
import com.tencent.smtt.sdk.TbsReaderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Description: 在应用内打开pdf\excel\world等文档<br>
 * 使用腾讯TBSsdk
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-11-23<br>
 * Time: 8:50<br>
 * UpdateDescription：<br>
 */
public class PDFActivity extends XMBaseActivity implements TbsReaderView.ReaderCallback {

    private TbsReaderView mTbsReaderView;
    private String mFileUrl = "http://home.hbhanzhi.com:7056/test.pdf";
    //    private String mFileUrl = "https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf";
    private String mFileName = "测试文档";
    private android.support.v7.widget.Toolbar mTopToolbar;
    private android.widget.TextView mTopTitleTv;
    long mDownLoadId = -1;

    @Override
    protected Object setLayout() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTopToolbar = findViewById(R.id.top_toolbar);
        mTopTitleTv = findViewById(R.id.top_title_tv);
        mTbsReaderView = new TbsReaderView(this, this);
        LinearLayout rootRl = findViewById(R.id.loadfile_ly);
        rootRl.addView(mTbsReaderView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        mTopToolbar.setNavigationIcon(R.drawable.icon_back);
        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopTitleTv.setText("数据报表");

        if (!CheckStringEmptyUtils.isEmpty(mFileUrl)) {
            showLoading();
            Intent service = new Intent(PDFActivity.this, DownloadService.class);
            service.putExtra(MiFinalData.DOWNLOAD_URL, mFileUrl);
            service.putExtra(MiFinalData.DOWNLOAD_TITLE, mFileName);
            startService(service);
        } else {
            T.shortToast(PDFActivity.this, "当前文件不存在");
            finish();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DownLoadUrl(MiEventMessage eventMessage) {
        if (eventMessage.getMessageType() == MiEventMessage.DOWNLOAD_FINISH) {
            String messageString = eventMessage.getMessageString();
            if (!CheckStringEmptyUtils.isEmpty(messageString)) {
                if (messageString.contains(mFileUrl)) {
                    hineLoading();
                    mDownLoadId = -1;
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", eventMessage.getDownLoadFile().getPath());
                    bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
                    boolean result = mTbsReaderView.preOpen(parseFormat(eventMessage.getDownLoadFile().getName()), false);
                    if (result) {
                        mTbsReaderView.openFile(bundle);
                    }
                }
            }
        } else if (eventMessage.getMessageType() == MiEventMessage.DOWNLOAD_FAIL) {
            String messageString = eventMessage.getMessageString();
            if (!CheckStringEmptyUtils.isEmpty(messageString)) {
                if (messageString.contains(mFileUrl)) {
                    mDownLoadId = -1;
                    hineLoading();
                    T.shortToast(PDFActivity.this, "下载文件失败，请检查网络重新下载！");
                }
            }
        } else if (eventMessage.getMessageType() == MiEventMessage.DOWNLOAD_START) {
            String messageString = eventMessage.getMessageString();
            if (!CheckStringEmptyUtils.isEmpty(messageString)) {
                if (messageString.contains(mFileUrl)) {
                    mDownLoadId = eventMessage.getMessageLong();
                }
            }
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
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
        EventBus.getDefault().unregister(this);
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
        if (mDownLoadId != -1) {
            DownloadService.cancleDownload(mDownLoadId);
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
