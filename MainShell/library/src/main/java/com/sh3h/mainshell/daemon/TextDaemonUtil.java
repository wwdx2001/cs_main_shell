/**
 * @author qiweiwei 2013-7-3 下午3:43:15
 */
package com.sh3h.mainshell_chaobiaoji.daemon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TextUtil
 */
public class TextDaemonUtil {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    public static final String FORMAT_FULL_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE_NO_YEAR = "MM-dd HH:mm";
    public static final String FORMAT_SHORT_DATE = "MM-dd";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_ISDROP = "yyyy.MM.dd";
    public static final String FORMAT_DATE_YEAR = "yyyy";
    public static final String FORMAT_DATE_NO_YEAR_SLASH = "MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_SHORT_TIME = "HH:mm";
    public static final String FORMAT_DATE_MONTH = "MM";
    public static final String FORMAT_DATE_DAY = "dd";

    /**
     * 判断字符串是否为空
     *
     * @param text
     *            需要判断的字符串
     * @return 如果为Null或者空字符串则返回True，否则返回False。
     */
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.length() == 0;
    }

    /**
     * 判断字符串是否为空, 允许判断之前做Trim操作。
     *
     * @param text
     *            需要判断的字符串
     * @return 如果为Null或者空字符串则返回True，否则返回False。
     */
    public boolean isNullOrEmpty(String text, boolean allowTrim) {
        String tmpText = text == null ? text : text.trim();
        return isNullOrEmpty(tmpText);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return 字符时间单位
     */
    public static String format(Date date, String format) {
        String formatString = format == null ? FORMAT_DATE_NO_SECOND : format;
        return new SimpleDateFormat(formatString, Locale.getDefault())
                .format(date);
    }

    public static Long format(Date date) {
        return date.getTime();
    }

    public static Date format(String date, String format) {
        if (date == null)
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @return 字符时间单位
     */
    public static String format(long longdate, String format) {
        Date date = new Date(longdate);
        String formatString = format == null ? FORMAT_DATE_NO_SECOND : format;
        return new SimpleDateFormat(formatString, Locale.getDefault())
                .format(date);
    }

    /**
     * 时间格式化
     *
     * @return 字符时间单位
     */
    public static Date format(long longdate) {
        return new Date(longdate);
    }

    /**
     * 截取字符串 如果content为null返回TextUtil.EMPTY 如果length过长则自动截取到结尾
     *
     * @param content
     *            字符串
     * @param startIndex
     *            开始位置
     * @param length
     *            长度
     * @return 截取后的内容
     */
    public static String subString(String content, int startIndex, int length) {
        if (content == null)
            return TextDaemonUtil.EMPTY;

        if (startIndex < 0)
            return TextDaemonUtil.EMPTY;

        int endIndex = startIndex + length;

        if (endIndex > content.length())
            endIndex = content.length();

        return content.substring(startIndex, endIndex);
    }

}
