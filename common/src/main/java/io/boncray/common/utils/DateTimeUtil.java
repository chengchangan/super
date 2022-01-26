package io.boncray.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author changan
 * @version 1.0
 * @date 2022/1/25 18:24
 */
public class DateTimeUtil {

    public static final DateTimeFormatter MS_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter S_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter M_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter NO_SPLIT_FORMATTER = DateTimeFormatter
            .ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final String EMPTY = "";

    public static final String AMERICA_LOS_ZONE = "America/Los_Angeles";
    public static final String ASIA_SHANG_HAI_ZONE = "Asia/Shanghai";

    /**
     * 时区.
     */
    public static final ZoneOffset DEFAULT_ZONE_OFF_SET = ZoneOffset.ofHours(8);


    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 格式化.
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        if (null == date) {
            return EMPTY;
        }
        return format(date, DATETIME_FORMATTER);
    }

    /**
     * 格式化.
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        if (null == date) {
            return EMPTY;
        }
        return format(toLocalDateTime(date), formatter);
    }

    /**
     * 格式化.
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDate date) {
        if (null == date) {
            return EMPTY;
        }
        return format(date, DATE_FORMATTER);
    }

    /**
     * 格式化.
     */
    public static String format(LocalDate date, DateTimeFormatter formatter) {
        if (null == date) {
            return EMPTY;
        }
        return date.format(formatter);
    }

    /**
     * 格式化.
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime dateTime) {
        if (null == dateTime) {
            return "";
        }
        return format(dateTime, DATETIME_FORMATTER);
    }

    /**
     * 格式化.
     */
    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (null == dateTime) {
            return "";
        }
        ZonedDateTime time = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        return time.format(formatter);
    }

    /**
     * 当前日期.
     *
     * @return yyyy-MM-dd
     */
    public static String nowDate() {
        return format(LocalDate.now());
    }

    /**
     * 当前日期时间.
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String nowTime() {
        return format(LocalDateTime.now());
    }

    /**
     * 文本转为Date.
     *
     * @param text yyyy-MM-dd HH:mm:ss
     */
    public static Date parserDate(String text) {
        return parserDate(!Assert.isEmpty(text) ? text.trim() : text, DATETIME_FORMATTER);
    }

    /**
     * 自定格式文本转为Date.
     */
    public static Date parserDate(String text, DateTimeFormatter formatter) {
        LocalDateTime time = parserLocalDateTime(!Assert.isEmpty(text) ? text.trim() : text, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 文本转为LocalDate.
     *
     * @param text yyyy-MM-dd
     */
    public static LocalDate parserLocalDate(String text) {
        return parserLocalDate(!Assert.isEmpty(text) ? text.trim() : text, DATE_FORMATTER);
    }

    /**
     * 自定格式文本转为LocalDate.
     */
    public static LocalDate parserLocalDate(String text, DateTimeFormatter formatter) {
        return LocalDate.parse(!Assert.isEmpty(text) ? text.trim() : text, formatter);
    }

    /**
     * 文本转为LocalDateTime.
     *
     * @param text yyyy-MM-dd HH:mm:ss
     */
    public static LocalDateTime parserLocalDateTime(String text) {
        return parserLocalDateTime(!Assert.isEmpty(text) ? text.trim() : text, DATETIME_FORMATTER);
    }

    /**
     * 自定格式文本转为LocalDateTime.
     */
    public static LocalDateTime parserLocalDateTime(String text, DateTimeFormatter formatter) {
        return LocalDateTime.parse(!Assert.isEmpty(text) ? text.trim() : text, formatter);
    }

    /**
     * 时间戳转为LocalDateTime.
     */
    public static LocalDateTime parserTimestamp(long timestamp) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }

    /**
     * 时间戳转为 指定时区的DateTime.
     */
    public static LocalDateTime parserTimestamp(long timestamp, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), zoneId);
    }

    /**
     * 时间转为指定 时区的时间
     */
    public static LocalDateTime parserTimeZone(LocalDateTime time, String zoneId) {
        return parserTimestamp(toTimestamp(time), ZoneId.of(zoneId));
    }

    /**
     * 时间戳毫秒转为LocalDateTime.
     */
    public static LocalDateTime parserTimesMillis(long timesMillis) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochMilli(timesMillis), TimeZone.getDefault().toZoneId());
    }

    /**
     * LocalDateTime转为时间戳.
     */
    public static long toTimestamp(LocalDateTime time) {
        return time.toInstant(DEFAULT_ZONE_OFF_SET).getEpochSecond();
    }

    /**
     * LocalDateTime转为毫秒时间戳.
     */
    public static long toTimesMillis(LocalDateTime time) {
        return time.toInstant(DEFAULT_ZONE_OFF_SET).toEpochMilli();
    }

    /**
     * LocalDateTime转为Date.
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转LocalDateTime
     */
    public static LocalDateTime localDateToDateTime(LocalDate localDate) {
        return parserTimesMillis(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
    }

}
