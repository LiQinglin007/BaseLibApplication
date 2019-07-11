package com.lixiaomi.baselib.ui.chooseDateUtils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.utils.ScreenUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

/**
 * @describe：选择时分<br>
 * @author：Xiaomi<br>
 * @createTime：2018/5/16<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiMinTime {
    private static Context mContext;
    private static int mSelectHour;
    private static int mSelectMin;
    private static int mTvColor;
    private static TimeDialogCallBack mTimeDialogCallBack;
    private static Dialog mDialog;

    private static AppCompatButton mSubmitTitle;
    private static AppCompatButton mCannleTitle;

    private static WheelView mHourWheel;
    private static WheelView mMinWheel;


    private static ArrayList<String> mHourList = new ArrayList<>();
    private static ArrayList<String> mMinList = new ArrayList<>();

    public MiMinTime(Context context) {
        MiMinTime.mContext = context;
    }

    public MiMinTime builder() {
        mDialog = new Dialog(mContext, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_choose_min, null);

        mSelectHour = 0;
        mSelectMin = 0;
        mTvColor = R.color.default_color;
        mSubmitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        mCannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        mHourWheel = dialogView.findViewById(R.id.dialog_choose_time_hour);
        mMinWheel = dialogView.findViewById(R.id.dialog_choose_time_min);


        int textSize = (ScreenUtils.getScreenHeight(mContext) / 200) * 2;
        mSubmitTitle.setTextSize(textSize);
        mCannleTitle.setTextSize(textSize);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = ScreenUtils.getScreenWidth(mContext);
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(lp);
        mDialog.setContentView(dialogView);
        return this;
    }

    /**
     * 设置选中小时
     *
     * @param selectHour
     * @return
     */
    public MiMinTime setSelectHour(int selectHour) {
        MiMinTime.mSelectHour = selectHour;
        return this;
    }

    /**
     * 设置选中分钟
     *
     * @param selectMin
     * @return
     */
    public MiMinTime setSelectMin(int selectMin) {
        MiMinTime.mSelectMin = selectMin;
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param color
     * @return
     */
    public MiMinTime setTextColor(int color) {
        MiMinTime.mTvColor = color;
        return this;
    }

    /**
     * 设置回调
     *
     * @param timeDialogCallBack
     * @return
     */
    public MiMinTime setTimeDialogCallBack(TimeDialogCallBack timeDialogCallBack) {
        MiMinTime.mTimeDialogCallBack = timeDialogCallBack;
        return this;
    }

    /**
     * 展示
     *
     * @return
     */
    public MiMinTime show() {
        initData();
        mDialog.show();
        return this;
    }

    private void initData() {
        mHourList.clear();
        mMinList.clear();

        for (int i = 0; i < 24; i++) {
            mHourList.add(i + "");
        }
        for (int i = 0; i < 60; i++) {
            mMinList.add(i + "");
        }

        ArrayWheelAdapter arrayWheelAdapterHour = new ArrayWheelAdapter(mContext);
        ArrayWheelAdapter arrayWheelAdapterMin = new ArrayWheelAdapter(mContext);
        mHourWheel.setWheelAdapter(arrayWheelAdapterHour);
        mHourWheel.setWheelSize(5);
        mHourWheel.setSkin(WheelView.Skin.Holo);
        mHourWheel.setWheelData(mHourList);
        mHourWheel.setSelection(mSelectHour);
        //放在setSelection()方法后
        mHourWheel.setVisibility(View.VISIBLE);

        mMinWheel.setWheelAdapter(arrayWheelAdapterMin);
        mMinWheel.setWheelSize(5);
        mMinWheel.setSkin(WheelView.Skin.Holo);
        mMinWheel.setWheelData(mMinList);
        mMinWheel.setSelection(mSelectMin);
        //放在setSelection()方法后
        mMinWheel.setVisibility(View.VISIBLE);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = mContext.getResources().getColor(mTvColor);
        mHourWheel.setStyle(style);
        mMinWheel.setStyle(style);

        mHourWheel.setExtraText("时", mContext.getResources().getColor(mTvColor), 40, 70);
        mMinWheel.setExtraText("分", mContext.getResources().getColor(mTvColor), 40, 70);

        mSubmitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Integer.parseInt((String) mHourWheel.getSelectionItem());
                int min = Integer.parseInt((String) mMinWheel.getSelectionItem());
                String hourSelectionItem = hour < 10 ? "0" + hour : hour + "";
                String minSelectionItem = min < 10 ? "0" + min : min + "";
                if (mTimeDialogCallBack != null) {
                    mTimeDialogCallBack.callback(minSelectionItem, hourSelectionItem);
                }
                mDialog.dismiss();
            }
        });
        mCannleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    public interface TimeDialogCallBack {
        /**
         * 选中时间后的回调
         *
         * @param min
         * @param hour
         */
        void callback(String min, String hour);
    }
}
