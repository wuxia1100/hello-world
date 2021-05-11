package org.cv.sf.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wuxia on 2017/4/1.
 */
public class DateUtil {

    private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";



    /**
     * 将String转日期yyyy-MM-dd
     */
    public static Date parseStringToDate(String date) {
        SimpleDateFormat fm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        try {
            return fm.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将String转日期yyyy-MM-dd
     */
    public static Date parseDateTimeToDate(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        try {
            return fm.parse(fm.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将String转日期yyyy-MM-dd
     */
    public static Date parseStringToDateTime(String date) {
        SimpleDateFormat fm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            return fm.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *将yyyy-MM-dd 日期转String
     * @param date
     * @return
     */
    public static String parseDateToString(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        return fm.format(date);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss日期转String
     * @param date
     * @return
     */
    public static String parseDateTimeToString(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return fm.format(date);
    }
    /**
     * 将yyyy-MM-dd HH:mm:ss日期转String
     * @param date
     * @return
     */
    public static String parseLocalDateTimeToString(LocalDateTime date) {
        return date.toLocalDate().toString()+" "+date.toLocalTime().toString();
    }
    /**
     * Java.util.Date 转date(String), format:自定义
     *
     * @param date   日期对象
     * @param format 格式化字符串
     * @return 格式化后的日期字符串
     */
    public static String getDateStringCustomFormat(Date date, String format) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime dt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dt.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Java.util.Date 转dateTime(String), format:yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime 日期对象
     * @return 格式化后的日期字符串
     */
    public static String getDateTimeString(Date dateTime) {
        return getDateStringCustomFormat(dateTime, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }
    /**
     * 获得几分钟后的日期
     *
     * @param specialDateTime 原时间
     * @param minutes         增加分钟
     */
    public static Date addDateMinutesBySpecialDateTimeString(Date specialDateTime, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(specialDateTime);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     * 获取当前日期的加上addDays天（addDays为负数则为减）
     *
     * @param addDays
     * @return
     */
    public static Date addDays(int addDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, addDays);
        return c.getTime();
    }
    /**
     * 获得几月后的日期
     *
     * @param specialDate 原日期
     * @param addMonth     增加几月
     */
    public static Date addMonthSpecialMonthString(Date specialDate, int addMonth) {
        //Date date = fromDateStringApi(specialDate);
        Calendar c = Calendar.getInstance();
        c.setTime(specialDate);
        c.add(Calendar.MONTH, addMonth);
        return c.getTime();
    }

    /**
     * 获取当前日期的加上addMinutes分钟（minutes为负数则为减）
     *
     * @param minutes
     * @return
     */
    public static Date addMinutes(int minutes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     *
     * @param change :变化的时间量：正数：获得表示当前时间加上变量后的时间，负数：获得表量之前的时间
     * @param field ：变化的时间类型：Calendar.MINUTE,SECOND,DAY,YEAR
     * @return
     */
    public static Date getChangeDate(int change,int field){
        Calendar calendar = Calendar.getInstance();
        calendar.add(field,change);
        return calendar.getTime();
    }

    /**
     * 获取当前时间
     */
    public static Date getCalendarNow(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获得几天后的日期
     *
     * @param specialDateTime 原时间
     * @param days         增加day
     */
    public static Date addDaysBySpecialDate(Date specialDateTime, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(specialDateTime);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * 获取当天日期
     * @return
     */
    public static Date getCurrendDate(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return  fmt.parse(fmt.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String friendlyTime(Date datetime) {
        Date time = datetime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = simpleDateFormat.format(cal.getTime());
        String paramDate = simpleDateFormat.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = ((int) ((cal.getTimeInMillis() - time.getTime()) / 60000)) == 0
                        ? "刚刚" : (int) ((cal.getTimeInMillis() - time.getTime()) / 60000) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days <= 3) {
            ftime = days + "天前";
        } else {
            ftime = simpleDateFormat.format(time);
        }
        return ftime;
    }


}
