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
import com.lixiaomi.baselib.utils.MiDateUtils;
import com.lixiaomi.baselib.utils.ScreenUtils;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Date;

/**
 * @describe：选择时间，未来三天，模仿滴滴预约<br>
 * @author：Xiaomi<br>
 * @createTime：2018/5/16<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiDiDiTime {
    /**
     * 营业的开始时间
     */
    private static int mStartHour = 0;
    /**
     * 营业的结束时间
     */
    private static int mEendHour = 24;

    private static Dialog mDialog;
    private static AppCompatButton mSubmitTitle;
    private static AppCompatButton mCannleTitle;

    private static WheelView mDateWheel;
    private static WheelView mHourWheel;
    private static WheelView mDayWheel;

    private static Context mContext;
    private static int mTvColor;

    private static TimeDialogCallBack mTimeDialogCallBack;

    public MiDiDiTime(Context context) {
        mContext = context;
    }

    public MiDiDiTime builder() {
        mTvColor = R.color.default_color;
        mDialog = new Dialog(mContext, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_choose_time, null);

        mSubmitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        mCannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        mDateWheel = dialogView.findViewById(R.id.dialog_choose_time_year);
        mHourWheel = dialogView.findViewById(R.id.dialog_choose_time_month);
        mDayWheel = dialogView.findViewById(R.id.dialog_choose_time_day);
        mDayWheel.setVisibility(View.GONE);

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

    public MiDiDiTime setTimeDialogCallBack(TimeDialogCallBack timeDialogCallBack) {
        MiDiDiTime.mTimeDialogCallBack = timeDialogCallBack;
        return this;
    }

    /**
     * 设置结束小时
     *
     * @param endHour
     * @return
     */
    public MiDiDiTime setEndHour(int endHour) {
        MiDiDiTime.mEendHour = endHour;
        return this;
    }


    /**
     * 设置开始小时
     *
     * @param startHour
     * @return
     */
    public MiDiDiTime setStartHour(int startHour) {
        MiDiDiTime.mStartHour = startHour;
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param color
     * @return
     */
    public MiDiDiTime setTextColor(int color) {
        MiDiDiTime.mTvColor = color;
        return this;
    }

    public MiDiDiTime show() {
        initData();
        mDialog.show();
        return this;
    }



    private void initData() {
        /**
         * 营业时间 7：00-22：00
         * 今天晚上22点之前的，能预约今，明，后三天的；
         * 今天晚上22点之后的，能预约明，后，大后三天。
         */
        Date mDate = new Date();
        //现在几点
        int hours = mDate.getHours();
        ArrayList<String> mDateList = new ArrayList<>();
        final ArrayList<String> mTimeList = new ArrayList<>();
        final ArrayList<String> mTimeList1 = new ArrayList<>();
        if (hours < (mEendHour - 1)) {
            int i = hours < mStartHour ? mStartHour : hours + 1;
            for (; i <= mEendHour; i++) {
                mTimeList.add(i + "");
            }
            for (int j = mStartHour; j <= mEendHour; j++) {
                mTimeList1.add(j + "");
            }
            //今天
            mDateList.add(MiDateUtils.getMonthDayWeek(mDate));
            //明天
            mDateList.add(MiDateUtils.getMonthDayWeek(MiDateUtils.getAgoOrNextDay(mDate,1)));
            //后天
            mDateList.add(MiDateUtils.getMonthDayWeek(MiDateUtils.getAgoOrNextDay(mDate,2)));
        } else {
            for (int i = mStartHour; i <= mEendHour; i++) {
                mTimeList1.add(i + "");
                mTimeList.add(i + "");
            }
            //明天
            mDateList.add(MiDateUtils.getMonthDayWeek(MiDateUtils.getAgoOrNextDay(mDate,1)));
            //后天
            mDateList.add(MiDateUtils.getMonthDayWeek(MiDateUtils.getAgoOrNextDay(mDate,2)));
            //大后天
            mDateList.add(MiDateUtils.getMonthDayWeek(MiDateUtils.getAgoOrNextDay(mDate,3)));
        }


        mDateWheel.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(mContext));
        mDateWheel.setWheelSize(5);
        mDateWheel.setSkin(WheelView.Skin.Holo);
        mDateWheel.setWheelData(mDateList);
        mDateWheel.setSelection(0);
        mDateWheel.setVisibility(View.VISIBLE);

        mHourWheel.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(mContext));
        mHourWheel.setWheelSize(5);
        mHourWheel.setSkin(WheelView.Skin.Holo);
        mHourWheel.setWheelData(mTimeList);
        mHourWheel.setSelection(0);
        mHourWheel.setVisibility(View.VISIBLE);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = mContext.getResources().getColor(mTvColor);
        mDateWheel.setStyle(style);
        mHourWheel.setStyle(style);

        mHourWheel.setExtraText("时", mContext.getResources().getColor(mTvColor), 40, 70);

        mDateWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                if (position == 0) {
                    //如果滚动到了第一个，后边的小时就加载第一天的
                    mHourWheel.setWheelData(mTimeList);
                } else {//加载后边的
                    mHourWheel.setWheelData(mTimeList1);
                }
            }
        });


        mSubmitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeSelectionItem = (String) mDateWheel.getSelectionItem();
                int hourSelectionInt = Integer.parseInt((String) mHourWheel.getSelectionItem());
                String hourSelectionItem = hourSelectionInt < 10 ? "0" + hourSelectionInt : hourSelectionInt + "";
                if (mTimeDialogCallBack != null) {
                    mTimeDialogCallBack.callback(timeSelectionItem, hourSelectionItem);
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
         * 选中后的回调
         *
         * @param date
         * @param time
         */
        void callback(String date, String time);
    }
}
