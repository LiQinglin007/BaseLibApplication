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
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @describe：选择时间，支持区间(年月日)<br>
 * @author：Xiaomi<br>
 * @createTime：2018/4/30<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiDayTime {
    private static WheelView mYearWheel;
    private static WheelView mMonthWheel;
    private static WheelView mDayWheel;
    private static AppCompatButton mSubmitTitle;
    private static AppCompatButton mCannleTitle;

    private static int mStartYear;
    private static int mEndYear;
    private static int mStartMonth;
    private static int mEndMonth;
    private static int mStartDay;
    private static int mEndDay;

    /**
     * 选中的年
     */
    private static int mSelectYear = 0;
    /**
     * 选中的月
     */
    private static int mSelectMonth = 0;
    /**
     * 选中的天
     */
    private static int mSelectDay = 0;

    private static String[] mBigMonths = {"1", "3", "5", "7", "8", "10", "12"};
    private static String[] mLittleMonths = {"4", "6", "9", "11"};
    private static List mBigMonth = Arrays.asList(mBigMonths);
    private static List mLittleMonth = Arrays.asList(mLittleMonths);

    /**
     * 初始化年份数组
     */
    private static ArrayList<String> mYearList = new ArrayList<>();
    private static ArrayList<String> mMonthList = new ArrayList<>();
    private static ArrayList<String> mDayList = new ArrayList<>();
    private static boolean mInit = false;

    public final static int YEAR_MONTH_DAY = 0x22;
    public final static int YEAR_MONTH = 0x23;

    private static int mTvColor = R.color.default_color;
    private static Context mContext;
    private static int mType;
    private static Dialog mDialog;
    private static TimeDialogCallBack mTimeDialogCallBack;


    public MiDayTime(Context context) {
        mContext = context;
    }

    public MiDayTime builder() {
        mStartYear = 1900;
        mStartDay = 01;
        mStartMonth = 01;
        mEndYear = new Date().getYear();
        mEndMonth = new Date().getMonth();
        mEndDay = new Date().getDay();

        mSelectYear = mEndYear;
        mSelectMonth = mEndMonth;
        mSelectDay = mEndDay;
        mType = YEAR_MONTH_DAY;

        mDialog = new Dialog(mContext, R.style.LoadingDialog);
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_choose_time, null);

        mSubmitTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_submit);
        mCannleTitle = (AppCompatButton) dialogView.findViewById(R.id.dialog_choose_time_cancel);
        mYearWheel = dialogView.findViewById(R.id.dialog_choose_time_year);
        mMonthWheel = dialogView.findViewById(R.id.dialog_choose_time_month);
        mDayWheel = dialogView.findViewById(R.id.dialog_choose_time_day);


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

    public MiDayTime setStartDate(Date startDate) {
        MiDayTime.mStartYear = MiDateUtils.getYear(startDate);
        MiDayTime.mStartDay = MiDateUtils.getDay(startDate);
        MiDayTime.mStartMonth = MiDateUtils.getMonth(startDate);
        return this;
    }

    public MiDayTime setEndDate(Date endDate) {
        MiDayTime.mEndYear = MiDateUtils.getYear(endDate);
        MiDayTime.mEndDay = MiDateUtils.getDay(endDate);
        MiDayTime.mEndMonth = MiDateUtils.getMonth(endDate);

        return this;
    }

    public MiDayTime setSelectDate(Date selectDate) {
        MiDayTime.mSelectYear = MiDateUtils.getYear(selectDate);
        MiDayTime.mSelectDay = MiDateUtils.getDay(selectDate);
        MiDayTime.mSelectMonth = MiDateUtils.getMonth(selectDate);
        return this;
    }

    public MiDayTime setType(int type) {
        MiDayTime.mType = type;
        return this;
    }


    public MiDayTime setCallBack(TimeDialogCallBack timeDialogCallBack) {
        MiDayTime.mTimeDialogCallBack = timeDialogCallBack;
        return this;
    }

    public MiDayTime setTvColor(int color) {
        MiDayTime.mTvColor = color;
        return this;
    }

    public MiDayTime show() {
        initData();
        mDialog.show();
        return this;
    }


    private void initData() {
        mYearList.clear();
        mMonthList.clear();
        mDayList.clear();
        for (int i = mStartYear; i < mEndYear + 1; i++) {
            mYearList.add(i + "");
        }
        //初始化月份
        mMonthList.addAll(getMonthList(mStartYear));
        //初始化天
        mDayList.addAll(getDayList(mStartYear, mStartMonth));

        mYearWheel.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mYearWheel.setWheelSize(5);
        mYearWheel.setSkin(WheelView.Skin.Holo);
        mYearWheel.setWheelData(mYearList);
        mYearWheel.setSelection(mSelectYear - mStartYear);
        mYearWheel.setVisibility(View.VISIBLE);

        mMonthWheel.setWheelAdapter(new ArrayWheelAdapter(mContext));
        mMonthWheel.setWheelSize(5);
        mMonthWheel.setSkin(WheelView.Skin.Holo);
        mMonthWheel.setWheelData(mMonthList);
        mMonthWheel.setSelection(mSelectYear == mStartYear ? (mSelectMonth - mStartMonth) : (mSelectMonth - 1));
        mMonthWheel.setVisibility(View.VISIBLE);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = mContext.getResources().getColor(mTvColor);
        mYearWheel.setStyle(style);
        mMonthWheel.setStyle(style);

        mYearWheel.setExtraText("年", mContext.getResources().getColor(mTvColor), 40, 90);
        mMonthWheel.setExtraText("月", mContext.getResources().getColor(mTvColor), 40, 70);


        if (mType == YEAR_MONTH_DAY) {
            mDayWheel.setWheelAdapter(new ArrayWheelAdapter(mContext));
            mDayWheel.setWheelSize(5);
            mDayWheel.setSkin(WheelView.Skin.Holo);
            mDayWheel.setWheelData(mDayList);
            //第一年并且是第一个月:剩余的
            mDayWheel.setSelection((mSelectYear == mStartYear && mSelectMonth == mStartMonth) ? (mSelectDay - mStartDay) : (mSelectDay - 1));
            mDayWheel.setVisibility(View.VISIBLE);
            mDayWheel.setExtraText("日", mContext.getResources().getColor(mTvColor), 40, 70);
            mDayWheel.setStyle(style);
        } else {
            mDayWheel.setVisibility(View.GONE);
        }

        mYearWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int parseInt = Integer.parseInt(mYearList.get(position));
                mMonthList.clear();
                mMonthList.addAll(getMonthList(parseInt));
                mMonthWheel.setWheelData(mMonthList);
            }
        });


        mMonthWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int year = Integer.parseInt((String) mYearWheel.getSelectionItem());
                int parseInt = Integer.parseInt(mMonthList.get(position));
                if (mType == YEAR_MONTH_DAY) {
                    mDayList.clear();
                    mDayList.addAll(getDayList(year, parseInt));
                    mDayWheel.setWheelData(mDayList);
                }
                if (!mInit) {
                    //如果还没有初始化呢，就去显示初始化时间
                    setSelectView(mType);
                }
            }
        });

        mSubmitTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = (String) mYearWheel.getSelectionItem();
                int monthInt = Integer.parseInt((String) mMonthWheel.getSelectionItem());
                String month = monthInt < 10 ? "0" + monthInt : monthInt + "";
                if (mType == YEAR_MONTH_DAY) {
                    int dayInt = Integer.parseInt((String) mDayWheel.getSelectionItem());
                    String day = dayInt < 10 ? "0" + dayInt : dayInt + "";
                    mTimeDialogCallBack.callback(year, month, day);
                } else {
                    mTimeDialogCallBack.callback(year, month, "01");
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


    /**
     * 设置一进来显示那一天
     */
    private static void setSelectView(int type) {
        if (mSelectYear == mStartYear) {
            //第一年
            mMonthWheel.setSelection(mSelectMonth - mStartMonth);
            mMonthWheel.setVisibility(View.VISIBLE);
            if (type == YEAR_MONTH_DAY) {
                if (mSelectMonth == mStartMonth) {
                    //第一个月
                    mDayWheel.setSelection(mSelectDay - mStartDay);
                    mDayWheel.setVisibility(View.VISIBLE);
                } else {
                    mDayWheel.setSelection(mSelectDay - 1);
                    mDayWheel.setVisibility(View.VISIBLE);
                }
            }

        } else {
            mMonthWheel.setSelection(mSelectMonth - 1);
            mMonthWheel.setVisibility(View.VISIBLE);
            if (type == YEAR_MONTH_DAY) {
                mDayWheel.setSelection(mSelectDay - 1);
                mDayWheel.setVisibility(View.VISIBLE);
            }
        }
        mInit = true;
    }

    /**
     * 根据年份返回月份的集合
     *
     * @param thisYear 年份
     * @return 月份集合
     */
    private static ArrayList<String> getMonthList(int thisYear) {
        ArrayList<String> monthList = new ArrayList<>();
        int startNumber = 1;
        int endNumber = 12;
        if (thisYear == mStartYear) {
            //第一年
            startNumber = mStartMonth;
            if (thisYear == mEndYear) {
                //是第一年，又是最后一年
                endNumber = mEndMonth;
            }
        } else if (thisYear == mEndYear) {
            //最后一年
            endNumber = mEndMonth;
        }
        monthList.clear();
        for (int i = startNumber; i < endNumber + 1; i++) {
            monthList.add(i + "");
        }
        return monthList;
    }


    /**
     * 根据年份和月份  返回当前月份的天集合
     *
     * @param thisYear  年份
     * @param thisMonth 月份
     * @return 当前月的天集合
     */
    private static ArrayList<String> getDayList(int thisYear, int thisMonth) {
        ArrayList<String> dayList = new ArrayList<>();
        int startNumber = 1;
        int endNumber = 31;
        if (thisYear == mStartYear) {
            //第一年
            if (thisMonth == mStartMonth) {
                //第一年的第一个月，需要初始化开始天，其他月份都是从1开始
                startNumber = mStartDay;
            }
            if (thisYear == mEndYear && thisMonth == mEndMonth) {
                //就一年，就一个月，第一年的第一个月==最后一年的最后一个月
                if (mBigMonth.contains(thisMonth + "")) {
                    //31天的月份
                    endNumber = mEndDay > 31 ? 31 : mEndDay;
                } else if (mLittleMonth.contains(thisMonth + "")) {
                    //30天的月份
                    endNumber = mEndDay > 30 ? 30 : mEndDay;
                } else {//2月 这里去判断平年或者闰年
                    if ((thisYear % 4 == 0 && thisYear % 100 != 0)
                            || thisYear % 400 == 0) {
                        //闰年
                        endNumber = mEndDay > 29 ? 29 : mEndDay;
                    } else {//平年
                        endNumber = mEndDay > 28 ? 28 : mEndDay;
                    }
                }
            } else {
                endNumber = getEndNumber(thisYear, thisMonth);
            }
        } else if (thisYear == mEndYear) {
            //最后一年，这里就不用管开始了，都是从1号开始，只处理结束时间
            //如果是最后一个月，就到最后一天就好了,//不是最后一个月，要去判断大月和小月，还有平年闰年
            endNumber = thisMonth == mEndMonth ? mEndDay : getEndNumber(thisYear, thisMonth);
        } else {
            //中间年份，也是只处理结束时间
            endNumber = getEndNumber(thisYear, thisMonth);
        }
        dayList.clear();
        for (int i = startNumber; i < endNumber + 1; i++) {
            dayList.add(i + "");
        }
        return dayList;
    }

    /**
     * 根据平年闰年和大小月来判断这个月最后一天是几号
     *
     * @param thisYear 年份
     * @param month    月份
     * @return 最后一天是几号
     */
    private static int getEndNumber(int thisYear, int month) {
        int endNumber = 31;
        if (mBigMonth.contains(month + "")) {
            //31天的月份
            endNumber = 31;
        } else if (mLittleMonth.contains(month + "")) {
            //30天的月份
            endNumber = 30;
        } else {//2月 这里去判断平年或者闰年
            if ((thisYear % 4 == 0 && thisYear % 100 != 0)
                    || thisYear % 400 == 0) {
                //闰年
                endNumber = 29;
            } else {//平年
                endNumber = 28;
            }
        }
        return endNumber;
    }

    public interface TimeDialogCallBack {
        /**
         * 选中时间之后的回调
         *
         * @param year
         * @param month
         * @param day
         */
        void callback(String year, String month, String day);
    }
}

