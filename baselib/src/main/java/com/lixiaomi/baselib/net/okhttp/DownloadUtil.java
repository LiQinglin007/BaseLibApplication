package com.lixiaomi.baselib.net.okhttp;

import android.os.Handler;
import android.os.Message;

import com.lixiaomi.baselib.net.DownloadListener;
import com.lixiaomi.baselib.utils.FileUtil;
import com.lixiaomi.baselib.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @describe：okhttp下载<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class DownloadUtil implements Callback {
    private final int DOWNLOAD_START = 0x1;
    private final int DOWNLOAD_SUCCESS = 0x2;
    private final int DOWNLOAD_RROGRESS = 0x3;
    private final int DOWNLOAD_FAILED = 0x4;
    OkHttpClient mOkHttpClient;
    DownloadListener mDownListener;
    MyHandler mMyHandler = null;
    File mFile = null;

    public DownloadUtil(String downUrl, String fileName, DownloadListener downListener) {
        this.mDownListener = downListener;
        mOkHttpClient = MiOkHttpClient.getOkHttpClient();
        mMyHandler = new MyHandler(this);
        downFile(downUrl, fileName);
    }

    class MyHandler extends Handler {
        WeakReference<DownloadUtil> mReference = null;

        public MyHandler(DownloadUtil mDownloadUtil) {
            mReference = new WeakReference<DownloadUtil>(mDownloadUtil);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_START:
                    mReference.get().mDownListener.downStart();
                    break;
                case DOWNLOAD_SUCCESS:
                    mReference.get().mDownListener.downSuccess();
                    break;
                case DOWNLOAD_RROGRESS:
                    mReference.get().mDownListener.downProgress(msg.arg1, (Long) msg.obj);
                    break;
                case DOWNLOAD_FAILED:
                    mReference.get().mDownListener.downFailed((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }


    private void downFile(String downUrl, String fileName) {
        try {
            mMyHandler.sendEmptyMessage(DOWNLOAD_START);
            String downloadFileDir = FileUtil.DOWNLOAD_FILE_DIR;
            final File fileDir = new File(downloadFileDir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            mFile = new File(downloadFileDir, System.currentTimeMillis() + fileName);
            LogUtils.loge(mFile.getAbsolutePath() + "getAbsolutePath");
            LogUtils.loge(mFile.getName() + "getName");
            Request request = new Request.Builder().url(downUrl).tag(DownloadUtil.class.getSimpleName()).build();
            mOkHttpClient.newCall(request).enqueue(this);
        } catch (Exception e) {
            Message msg = new Message();
            msg.what = DOWNLOAD_FAILED;
            msg.obj = e.toString();
            mMyHandler.sendMessage(msg);
            LogUtils.loge("=================error==" + e.toString());
        }
    }

    /**
     * 取消下载
     */
    public void cacleDown() {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.runningCalls()) {
            String tag = (String) call.request().tag();
            if (DownloadUtil.class.getSimpleName().equals(tag)) {
                call.cancel();
            }
        }
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Message msg = new Message();
        msg.what = DOWNLOAD_FAILED;
        msg.obj = e.toString();
        mMyHandler.sendMessage(msg);
        LogUtils.loge("down:onFailure");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();

            if (mFile.exists()) {
                LogUtils.loge("down:文件存在，删除文件==");
                mFile.delete();
            }
            if (!mFile.exists() && mFile.isFile()) {
                LogUtils.loge("down:下载文件不存在创建文件==");
                boolean isCreat = mFile.createNewFile();
                LogUtils.loge("down:创建文件======" + isCreat);
            }
            fos = new FileOutputStream(mFile);
            long sum = 0;
            long saveSum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                final int progress = (int) (sum * 1.0f / total * 100);
                final long speed = sum - saveSum;
                saveSum = sum;
                LogUtils.loge("down:=================" + progress + "  speed =" + speed);
                updateProgress(progress, speed);
            }
            fos.flush();
            mMyHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
            LogUtils.loge("down:=================success==");
        } catch (Exception e) {
            Message msg = new Message();
            msg.what = DOWNLOAD_FAILED;
            msg.obj = e.toString();
            mMyHandler.sendMessage(msg);
            LogUtils.loge("down:=================error==" + e.toString());
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }


    int lastProgress = 0;

    private void updateProgress(int progress, long speed) {
        if (progress > lastProgress) {
            Message msg = new Message();
            msg.what = DOWNLOAD_RROGRESS;
            msg.arg1 = progress;
            msg.obj = speed;
            mMyHandler.sendMessage(msg);
        }
        lastProgress = progress;
    }

}
