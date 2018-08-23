package kaizone.songmaya.smartns.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 许崇雷 on 2016/6/17.
 * 类型转换
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final ThreadLocalPool<String, SimpleDateFormat> FORMAT_POOL = ThreadLocalPool.withInitial(SimpleDateFormat::new);

    /**
     * 获取操作系统当前时间戳
     *
     * @return
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取操作系统当前时间戳的字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String nowString() {
        return DateUtils.toDateTimeString(now());
    }

    /**
     * 时间转换为 yyyy-MM-dd 字符串
     *
     * @param value
     * @return
     */
    public static String toDateString(Date value) {
        return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD).format(value);
    }

    /**
     * 时间转换为 yyyy-MM-dd HH:mm:ss 字符串
     *
     * @param value
     * @return
     */
    public static String toDateTimeString(Date value) {
        return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD_HH_MM_SS).format(value);
    }

    /**
     * 时间转换为 yyyy-MM-dd HH:mm:ss.SSS 字符串
     *
     * @param value
     * @return
     */
    public static String toDateTimeMsString(Date value) {
        return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD_HH_MM_SS_SSS).format(value);
    }

    /**
     * 时间转换为指定格式的字符串
     *
     * @param value
     * @param format
     * @return
     */
    public static String toString(Date value, String format) {
        if (format == null)
            throw new NullPointerException("format");
        return value == null ? null : FORMAT_POOL.get(format).format(value);
    }


    /**
     * 字符串转换为
     *
     * @param value
     * @return
     */
    public static Date fromDateString(String value) {
        try {
            return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD).parse(value);
        } catch (ParseException e) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

    /**
     * yyyy-MM-dd 格式字符串转换为时间
     *
     * @param value
     * @return
     */
    public static Date fromDateTimeString(String value) {
        try {
            return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD_HH_MM_SS).parse(value);
        } catch (ParseException e) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式字符串转换为时间
     *
     * @param value
     * @return
     */
    public static Date fromDateTimeMsString(String value) {
        try {
            return value == null ? null : FORMAT_POOL.get(YYYY_MM_DD_HH_MM_SS_SSS).parse(value);
        } catch (ParseException e) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS 格式字符串转换为时间
     *
     * @param value
     * @param format
     * @return
     */
    public static Date fromString(String value, String format) {
        if (format == null)
            throw new NullPointerException("format");
        try {
            return value == null ? null : FORMAT_POOL.get(format).parse(value);
        } catch (ParseException e) {
            throw new ClassCastException("can not convert \"" + value + "\" to Date");
        }
    }

}
