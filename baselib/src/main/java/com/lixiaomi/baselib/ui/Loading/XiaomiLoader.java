package com.lixiaomi.baselib.ui.Loading;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.utils.ScreenUtils;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * @describe：用来显示loading的对话框<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/21<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class XiaomiLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static AppCompatDialog mLoadingDialog;
    private static AVLoadingIndicatorView mAvLoadingIndicatorView;
    /**
     * loading的默认样式
     */
    public static final LoaderStyle DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator;

    /**
     * 显示loading的dialog
     *
     * @param context
     * @param loaderStyle 对话框的样式
     */
    public static void showLoading(Context context, int color, Enum<LoaderStyle> loaderStyle) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppCompatDialog(context, R.style.LoadingDialog);
            if (mAvLoadingIndicatorView == null) {
                mAvLoadingIndicatorView = LoaderCreator.create(loaderStyle.name(), context);
            }
            mAvLoadingIndicatorView.setIndicatorColor(color);
            mLoadingDialog.setContentView(mAvLoadingIndicatorView);
        }
        if (mLoadingDialog.isShowing()) {
            return;
        }
        int deviceWidth = ScreenUtils.getScreenWidth(context);
        int deviceHeight = ScreenUtils.getScreenHeight(context);
        final Window dialogWindow = mLoadingDialog.getWindow();
        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.show();
    }


    /**
     * 显示默认样式的loading
     *
     * @param context
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void showLoading(Context context, int color) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        showLoading(context, color, DEFAULT_LOADER);
    }

    /**
     * 停止
     */
    public static void stopLoading() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                if (mAvLoadingIndicatorView != null) {
                    mAvLoadingIndicatorView.hide();
                }
                mLoadingDialog.hide();
            }
        }
    }
}
