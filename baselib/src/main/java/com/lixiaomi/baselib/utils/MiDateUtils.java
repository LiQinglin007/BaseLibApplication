package com.lixiaomi.baselib.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;
import static java.util.Calendar.WEEK_OF_MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;


/**
 * @describe：时间日期工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/08/21<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiDateUtils {
    public static Date mDate = null;
    public static DateFormat mDateFormat = null;
    public static Calendar mCalendar = null;

    public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public final static String MDHM = "MM-dd HH:mm";
    public final static String YMDHM = "yyyy-MM-dd HH:mm";
    public final static String HMS = "HH:mm:ss";
    public final static String HM = "HH:mm";
    public final static String YMD = "yyyy-MM-dd";
    public final static String YM = "yyyy-MM";
    public final static String MD = "MM-dd";

    /**
     * 日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        mCalendar = getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        mCalendar.setTimeInMillis(millis);
        return mCalendar.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }


    /**
     * 以指定的格式来格式化日期
     *
     * @param date   日期
     * @param format 格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String format) {
        mDateFormat = new SimpleDateFormat(format);
        return mDateFormat.format(date);
    }


    /**
     * 字符串转换成日期.
     *
     * @param dateString 日期字符
     * @param format     格式化.
     * @return
     * @throws ParseException 转换错误
     */
    public static Date stringToDate(String dateString, String format) throws ParseException {
        mDateFormat = new SimpleDateFormat(format);
        return mDateFormat.parse(dateString);
    }


    /**
     * 时间戳转化时间
     *
     * @param dateLong 时间戳
     * @param format   转化规则
     * @return
     * @throws ParseException 转换错误
     */
    public static Date longToDate(Long dateLong, String format) throws ParseException {
        mDateFormat = new SimpleDateFormat(format);
        String strTime = mDateFormat.format(dateLong);
        return mDateFormat.parse(strTime);
    }


    /**
     * 时间戳转化时间
     *
     * @param dateLong 时间戳
     * @param format   转化规则
     * @return
     */
    public static String longToStr(Long dateLong, String format) {
        mDateFormat = new SimpleDateFormat(format);
        return mDateFormat.format(dateLong);
    }


    /**
     * 获取某天是星期几
     *
     * @param date
     * @return 返回  X月X日(周X)
     */
    public static String getMonthDayWeek(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        //获取月份，0表示1月份
        int month = mCalendar.get(MONTH) + 1;
        //获取当前天数
        int day = mCalendar.get(DAY_OF_MONTH);
        int week = mCalendar.get(DAY_OF_WEEK);

        String weekStr = null;
        switch (week) {
            case SUNDAY:
                weekStr = "周日";
                break;
            case MONDAY:
                weekStr = "周一";
                break;
            case TUESDAY:
                weekStr = "周二";
                break;
            case WEDNESDAY:
                weekStr = "周三";
                break;
            case THURSDAY:
                weekStr = "周四";
                break;
            case FRIDAY:
                weekStr = "周五";
                break;
            case SATURDAY:
                weekStr = "周六";
                break;
            default:
                break;
        }
        return month + "月" + day + "日" + "(" + weekStr + ")";
    }



    /**
     * 获取今天周几
     *
     * @return 返回1-7  周一到周日
     */
    public static int getMonthDayWeekNmuber() {
        mCalendar = getInstance();
        int week = mCalendar.get(DAY_OF_WEEK);
        int weekNumber = 1;
        switch (week) {
            case MONDAY:
                weekNumber = 1;
                break;
            case TUESDAY:
                weekNumber = 2;
                break;
            case WEDNESDAY:
                weekNumber = 3;
                break;
            case THURSDAY:
                weekNumber = 4;
                break;
            case FRIDAY:
                weekNumber = 5;
                break;
            case SATURDAY:
                weekNumber = 6;
                break;
            case SUNDAY:
                weekNumber = 7;
                break;
            default:
                break;
        }
        return weekNumber;
    }


    /**
     * 日期转换成口头时间
     *
     * @param date
     * @return
     * @throws ParseException 转换错误
     */
    public static String getTimeInterval(Date date) throws ParseException {
        return getTimeInterval(dateToString(date, MiDateUtils.YMDHMS));
    }


    /**
     * 获得口头时间字符串，如今天，昨天等
     *
     * @param dateString 时间格式为yyyy-MM-dd HH:mm:ss
     * @return 口头时间字符串
     * @throws ParseException 转换错误
     */
    public static String getTimeInterval(String dateString) throws ParseException {
        Date date = stringToDate(dateString, MiDateUtils.YMDHMS);
        mCalendar = getInstance();
        mCalendar.setTime(new Date());
        int nowYear = mCalendar.get(YEAR);
        int nowMonth = mCalendar.get(MONTH);
        int nowWeek = mCalendar.get(WEEK_OF_MONTH);
        int nowDay = mCalendar.get(DAY_OF_WEEK);
        int nowHour = mCalendar.get(HOUR_OF_DAY);
        int nowMinute = mCalendar.get(MINUTE);

        mCalendar = getInstance();
        if (date != null) {
            mCalendar.setTime(date);
        } else {
            mCalendar.setTime(new Date());
        }
        int year = mCalendar.get(YEAR);
        int month = mCalendar.get(MONTH);
        int week = mCalendar.get(WEEK_OF_MONTH);
        int day = mCalendar.get(DAY_OF_WEEK);
        int hour = mCalendar.get(HOUR_OF_DAY);
        int minute = mCalendar.get(MINUTE);
        if (year != nowYear) {
            mDateFormat = new SimpleDateFormat(MiDateUtils.YMD);
            //不同年份
            return mDateFormat.format(date);
        } else {
            if (month != nowMonth) {
                //不同月份
                mDateFormat = new SimpleDateFormat("M月dd日");
                return mDateFormat.format(date);
            } else {
                if (week != nowWeek) {
                    //不同周
                    mDateFormat = new SimpleDateFormat("M月dd日");
                    return mDateFormat.format(date);
                } else if (day != nowDay) {
                    if (day + 1 == nowDay) {
                        return "昨天" + dateToString(date, MiDateUtils.HM);
                    }
                    if (day + 2 == nowDay) {
                        return "前天" + dateToString(date, MiDateUtils.HM);
                    }
                    //不同天
                    mDateFormat = new SimpleDateFormat("M月dd日");
                    return mDateFormat.format(date);
                } else {
                    //同一天
                    int hourGap = nowHour - hour;
                    if (hourGap == 0) {
                        //1小时内
                        if (nowMinute - minute < 1) {
                            return "刚刚";
                        } else {
                            return (nowMinute - minute) + "分钟前";
                        }
                    } else if (hourGap >= 1 && hourGap <= 12) {
                        return hourGap + "小时前";
                    } else {
                        mDateFormat = new SimpleDateFormat("今天 HH:mm");
                        return mDateFormat.format(date);
                    }
                }
            }
        }
    }


    /**
     * 获取今天之前或者之后几天的时间
     *
     * @param date       今天
     * @param dayNumbers 和今天相差几天   今天之前传负数，今天之后传正数
     * @return
     */
    public static Date getAgoOrNextDay(Date date, int dayNumbers) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        mCalendar.add(DAY_OF_MONTH, dayNumbers);
        date = mCalendar.getTime();
        return date;
    }


    /**
     * 拿到指定的天
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year, int month, int day) {
        Date endDate = new Date();
        if (year < 1900) {
            year = 1900;
            month = 1;
            day = 1;
        }
        if (month > 12) {
            month = 12;
            day = 1;
        }
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] monthsBig = {"1", "3", "5", "7", "8", "10", "12"};
        String[] monthsLittle = {"4", "6", "9", "11"};

        List listBig = Arrays.asList(monthsBig);
        List listLittle = Arrays.asList(monthsLittle);
        if (listBig.contains(month + "")) {
            day = day > 31 ? 31 : day;
        } else if (listLittle.contains(month + "")) {
            day = day > 30 ? 30 : day;
        } else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                //闰年
                day = day > 29 ? 29 : day;
            } else {//平年
                day = day > 28 ? 28 : day;
            }
        }
        endDate.setYear(year - 1900);
        endDate.setMonth(month - 1);
        endDate.setDate(day);
        return endDate;
    }

    /**
     * 返回年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(MONTH) + 1;
    }

    /**
     * 返回日份
     *
     * @param date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.get(SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        mCalendar = getInstance();
        mCalendar.setTime(date);
        return mCalendar.getTimeInMillis();
    }

    /**
     * 取得指定月份的第一天
     *
     * @param dateString 字符型日期
     * @return yyyy-MM-dd 格式@throws ParseException 转换错误
     */
    public static String getMonthBegin(String dateString) throws ParseException {
        mDate = stringToDate(dateString, MiDateUtils.YMD);
        return dateToString(mDate, MiDateUtils.YM) + "-01";
    }

    /**
     * 取得指定月份的最后一天
     *
     * @param dateString 字符型日期
     * @return 日期字符串 yyyy-MM-dd格式
     * @throws ParseException 转换错误
     */
    public static String getMonthEnd(String dateString) throws ParseException {
        mDate = stringToDate(getMonthBegin(dateString), MiDateUtils.YMD);
        mCalendar = getInstance();
        mCalendar.setTime(mDate);
        mCalendar.add(MONTH, 1);
        mCalendar.add(DAY_OF_YEAR, -1);
        return dateToString(mCalendar.getTime(), MiDateUtils.YMD);
    }

}
