package nccloud.utils.lang;

import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    private static final ZoneId zoneId = ZoneId.systemDefault();
    private static final Map<String, DateTimeFormatter> dateFormatterMap = new HashMap<>();

    /**
     * DAY_YEAR = 365
     */
    public static final BigDecimal DAY_YEAR = new BigDecimal(365);
    /**
     * MONTH_YEAR = 12
     */
    public static final BigDecimal MONTH_YEAR = new BigDecimal(12);
    /**
     * DAY_MONTH = 30
     */
    public static final BigDecimal DAY_MONTH = new BigDecimal(30);
    /**
     * HOUR_DAY = 24
     */
    public static final BigDecimal HOUR_DAY = new BigDecimal(24);
    /**
     * MINUTE_HOUR = 60
     */
    public static final BigDecimal MINUTE_HOUR = new BigDecimal(60);
    /**
     * SECOND_MINUTE = 60
     */
    public static final BigDecimal SECOND_MINUTE = new BigDecimal(60);
    /**
     * MILLISECOND_SECOND = 1000
     */
    public static final BigDecimal MILLISECOND_SECOND = new BigDecimal(1000);
    /**
     * SECOND_DAY = 86400
     */
    public static final BigDecimal SECOND_DAY = new BigDecimal(86400);
    /**
     * SECOND_HOUR = 3600
     */
    public static final BigDecimal SECOND_HOUR = new BigDecimal(3600);
    /**
     * MILLISECOND_DAY = 86400000
     */
    public static final BigDecimal MILLISECOND_DAY = new BigDecimal(86400000);
    /**
     * MILLISECOND_HOUR = 3600000
     */
    public static final BigDecimal MILLISECOND_HOUR = new BigDecimal(3600000);





    private static DateTimeFormatter buildDateTimeFormatter(@NotNull String pattern){
        return new DateTimeFormatterBuilder()
                .appendPattern(pattern)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();

    }

    /**
     * 校验日期字符串是否合法
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 如果合法返回True，不合法返回False
     *
     */
    public static boolean isValid(String dateStr,@NotNull String pattern){
        if(StringUtils.isBlank(dateStr)) return false;
        DateTimeFormatter dateTimeFormatter = dateFormatterMap.computeIfAbsent(pattern, DateUtils::buildDateTimeFormatter);
        try {
            LocalDateTime parse = LocalDateTime.parse(dateStr, dateTimeFormatter);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 校验日期字符串是否合法
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @throws DateTimeParseException 如果不合法返回该异常
     */
    public static void validCheck(String dateStr,@NotNull String pattern) throws DateTimeParseException {
        if(StringUtils.isBlank(dateStr)) throw new DateTimeParseException("date is empty", dateStr, 0);
        DateTimeFormatter dateTimeFormatter = dateFormatterMap.computeIfAbsent(pattern, DateUtils::buildDateTimeFormatter);
        LocalDateTime parse = LocalDateTime.parse(dateStr, dateTimeFormatter);
    }


    /**
     * 格式化日期
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(LocalDate date,@NotNull String pattern){
        if (date==null) return null;
        DateTimeFormatter dateTimeFormatter = dateFormatterMap.computeIfAbsent(pattern, DateUtils::buildDateTimeFormatter);
        return date.format(dateTimeFormatter);
    }
    /**
     * 格式化日期
     * @param date 日期时间
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(LocalDateTime date,@NotNull String pattern){
        if (date==null) return null;
        DateTimeFormatter dateTimeFormatter = dateFormatterMap.computeIfAbsent(pattern, DateUtils::buildDateTimeFormatter);
        return date.format(dateTimeFormatter);
    }

    /**
     * 格式化日期,默认时区
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(Date date,@NotNull String pattern){
        if (date==null) return null;
        LocalDateTime localDate = caseToLocalDateTime(date);
        return formatData(localDate, pattern);
    }
    /**
     * 格式化日期,指定时区
     * @param date 日期
     * @param pattern 日期格式
     * @param zoneId 时区
     * @return 日期字符串
     */
    public static String formatData(Date date,@NotNull String pattern,@NotNull ZoneId zoneId){
        if (date==null) return null;
        LocalDateTime localDate = caseToLocalDateTime(date,zoneId);
        return formatData(localDate, pattern);
    }

    /**
     * 格式化日期,默认时区
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(UFDate date,@NotNull String pattern){
        return formatData(caseToLocalDateTime(date), pattern);
    }
    /**
     * 格式化日期,指定时区
     * @param date 日期
     * @param pattern 日期格式
     * @param zoneId 时区
     * @return 日期字符串
     */
    public static String formatData(UFDate date,@NotNull String pattern,@NotNull ZoneId zoneId){
        return formatData(caseToLocalDateTime(date,zoneId), pattern);
    }

    /**
     * 格式化日期,默认时区
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(UFLiteralDate date,@NotNull String pattern){
        return formatData(caseToLocalDate(date), pattern);
    }
    /**
     * 格式化日期,指定时区
     * @param date 日期
     * @param pattern 日期格式
     * @param zoneId 时区
     * @return 日期字符串
     */
    public static String formatData(UFLiteralDate date,@NotNull String pattern,@NotNull ZoneId zoneId){
        return formatData(caseToLocalDate(date,zoneId), pattern);
    }

    /**
     * 格式化日期,默认时区
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatData(UFDateTime date,@NotNull String pattern){
        return formatData(caseToLocalDateTime(date), pattern);
    }
    /**
     * 格式化日期,指定时区
     * @param date 日期
     * @param pattern 日期格式
     * @param zoneId 时区
     * @return 日期字符串
     */
    public static String formatData(UFDateTime date,@NotNull String pattern,@NotNull ZoneId zoneId){
        return formatData(caseToLocalDateTime(date,zoneId), pattern);
    }



    /**
     * Date 转化为 LocalDateTime
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(Date date,ZoneId zoneId){
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date 转化为 LocalDateTime ，时区为本机默认时区
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(Date date){
        return caseToLocalDateTime(date,zoneId);
    }

    /**
     * Date 转化为 LocalDateTime
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(UFDate date,ZoneId zoneId){
        return caseToLocalDateTime(date.toDate(), zoneId);
    }

    /**
     * Date 转化为 LocalDateTime ，时区为本机默认时区
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(UFDate date){
        return caseToLocalDateTime(date.toDate());
    }

    /**
     * Date 转化为 LocalDateTime
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(UFDateTime date,ZoneId zoneId){
        return caseToLocalDateTime(date.getDate(), zoneId);
    }

    /**
     * Date 转化为 LocalDateTime ，时区为本机默认时区
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDateTime caseToLocalDateTime(UFDateTime date){
        return caseToLocalDateTime(date.getDate());
    }


    /**
     * Date 转化为 LocalDate
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate
     */
    public static LocalDate caseToLocalDate(Date date,ZoneId zoneId){
        Instant instant = date.toInstant();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * Date 转化为 LocalDate，时区为本机默认时区
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate caseToLocalDate(Date date){
        return caseToLocalDate(date,zoneId);
    }

    /**
     * Date 转化为 LocalDate，时区为本机默认时区
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate caseToLocalDate(UFLiteralDate date){
        return caseToLocalDate(date.toDate());
    }

    /**
     * Date 转化为 LocalDate
     * @param date 日期
     * @param zoneId 时区
     * @return LocalDate
     */
    public static LocalDate caseToLocalDate(UFLiteralDate date,ZoneId zoneId){
        return caseToLocalDate(date.toDate(), zoneId);
    }

    /**
     * LocalDate 转化为 Date,当天0时0分0秒，时区为本机默认时区
     * @param date 日期
     * @return Date
     */
    public static Date caseToDate(LocalDate date){
        return caseToDate(date, zoneId);
    }

    /**
     * LocalDate 转化为 UFDate,当天0时0分0秒，时区为本机默认时区
     * @param date 日期
     * @return Date
     */
    public static UFDate caseToUFDate(LocalDate date){
        return new UFDate(caseToDate(date));
    }

    /**
     * LocalDate 转化为 UFLiteralDate，时区为本机默认时区
     * @param date 日期
     * @return Date
     */
    public static UFLiteralDate caseToUFLiteralDate(LocalDate date){
        return new UFLiteralDate(caseToDate(date));
    }

    /**
     * LocalDate 转化为 UFDateTime,当天0时0分0秒，时区为本机默认时区
     * @param date 日期
     * @return Date
     */
    public static UFDateTime caseToUFDateTime(LocalDate date){
        return new UFDateTime(caseToDate(date));
    }


    /**
     * LocalDate 转化为 Date,当天0时0分0秒
     * @param date 日期
     * @param zoneId 时区
     * @return Date
     */
    public static Date caseToDate(LocalDate date,ZoneId zoneId){
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDate 转化为 UFDate,当天0时0分0秒
     * @param date 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFDate caseToUFDate(LocalDate date,ZoneId zoneId){
        return new UFDate(caseToDate(date,zoneId));
    }

    /**
     * LocalDate 转化为 UFLiteralDate
     * @param date 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFLiteralDate caseToUFLiteralDate(LocalDate date,ZoneId zoneId){
        return new UFLiteralDate(caseToDate(date,zoneId));
    }

    /**
     * LocalDate 转化为 UFDateTime,当天0时0分0秒
     * @param date 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFDateTime caseToUFDateTime(LocalDate date,ZoneId zoneId){
        return new UFDateTime(caseToDate(date,zoneId));
    }




    /**
     * LocalDateTime 转化为 Date，时区为本机默认时区
     * @param dateTime 日期
     * @return Date
     */
    public static Date caseToDate(LocalDateTime dateTime){
        return caseToDate(dateTime, zoneId);
    }

    /**
     * LocalDateTime 转化为 Date，时区为本机默认时区
     * @param dateTime 日期
     * @return Date
     */
    public static UFDate caseToUFDate(LocalDateTime dateTime){
        return new UFDate(caseToDate(dateTime));
    }

    /**
     * LocalDateTime 转化为 UFDateTime，时区为本机默认时区
     * @param dateTime 日期
     * @return Date
     */
    public static UFDateTime caseToUFDateTime(LocalDateTime dateTime){
        return new UFDateTime(caseToDate(dateTime));
    }

    /**
     * LocalDateTime 转化为 UFLiteralDate，舍去时分秒，时区为本机默认时区
     * @param dateTime 日期
     * @return Date
     */
    public static UFLiteralDate caseToUFLiteralDate(LocalDateTime dateTime){
        return new UFLiteralDate(caseToDate(dateTime).getTime());
    }

    /**
     * LocalDateTime 转化为 Date
     * @param dateTime 日期
     * @param zoneId 时区
     * @return Date
     */
    public static Date caseToDate(LocalDateTime dateTime,ZoneId zoneId){
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 转化为 Date
     * @param dateTime 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFDate caseToUFDate(LocalDateTime dateTime,ZoneId zoneId){
        return new UFDate(caseToDate(dateTime,zoneId));
    }

    /**
     * LocalDateTime 转化为 UFDateTime
     * @param dateTime 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFDateTime caseToUFDateTime(LocalDateTime dateTime,ZoneId zoneId){
        return new UFDateTime(caseToDate(dateTime,zoneId));
    }

    /**
     * LocalDateTime 转化为 UFLiteralDate，舍去时分秒
     * @param dateTime 日期
     * @param zoneId 时区
     * @return Date
     */
    public static UFLiteralDate caseToUFLiteralDate(LocalDateTime dateTime,ZoneId zoneId){
        return new UFLiteralDate(caseToDate(dateTime,zoneId).getTime());
    }



    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位
     * @return 多少秒
     */
    public static long passLong(BigDecimal time, TimeEnum type){
        if (time == null || type == null) return 0;
        BigDecimal res = BigDecimal.ZERO;
        switch (type){
            case YEAR:
                res = time
                        .multiply(DAY_YEAR)
                        .multiply(HOUR_DAY)
                        .multiply(MINUTE_HOUR)
                        .multiply(SECOND_MINUTE);
                break;
            case MONTH:
                res = time
                        .multiply(DAY_MONTH)
                        .multiply(HOUR_DAY)
                        .multiply(MINUTE_HOUR)
                        .multiply(SECOND_MINUTE);
                break;
            case DAY:
                res = time
                        .multiply(HOUR_DAY)
                        .multiply(MINUTE_HOUR)
                        .multiply(SECOND_MINUTE);
                break;
        }
        res = res.setScale(0, RoundingMode.UP);
        return res.longValue();
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位
     * @return 多少秒
     */
    public static long passLong(UFDouble time, TimeEnum type){
        if (time==null) return 0;
        BigDecimal bigDecimal = time.toBigDecimal();
        return passLong(bigDecimal,type);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位
     * @return 多少秒
     */
    public static long passLong(double time, TimeEnum type){
        return passLong(new BigDecimal(time),type);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位
     * @return 多少秒
     */
    public static long passLong(float time, TimeEnum type){
        return passLong(new BigDecimal(time),type);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位
     * @return 多少秒
     */
    public static long passLong(int time, TimeEnum type){
        return passLong(new BigDecimal(time),type);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位 1：年 2：月 3：日
     * @return 多少秒
     */
    public static long passLong(UFDouble time,int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return passLong(time,timeEnum);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位 1：年 2：月 3：日
     * @return 多少秒
     */
    public static long passLong(BigDecimal time,int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return passLong(time,timeEnum);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位 1：年 2：月 3：日
     * @return 多少秒
     */
    public static long passLong(double time,int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return passLong(time,timeEnum);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位 1：年 2：月 3：日
     * @return 多少秒
     */
    public static long passLong(float time,int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return passLong(time,timeEnum);
    }

    /**
     * 数字转多少秒
     * @param time 数字 如1.2年
     * @param type 单位 1：年 2：月 3：日
     * @return 多少秒
     */
    public static long passLong(int time,int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return passLong(time,timeEnum);
    }



    /**
     * 获取今天前几天
     * @param days 天数
     * @return 日期
     */
    public static LocalDate getDateBefore(int days) {
        LocalDate now = LocalDate.now();
        return now.plusDays( -days );
    }

    /**
     * 获取某日前几天
     * @param days 天数
     * @param date 日期
     * @return 日期
     */
    public static Date getDateBefore(Date date, int days) {
        LocalDateTime localDate = caseToLocalDateTime(date);
        return caseToDate(localDate.plusDays( -days ));
    }

    /**
     * 获取某日前几天
     * @param days 天数
     * @param date 日期
     * @return 日期
     */
    public static UFDate getDateBefore(UFDate date, int days) {
        LocalDateTime localDate = caseToLocalDateTime(date);
        return caseToUFDate(localDate.plusDays( -days ));
    }

    /**
     * 获取某日前几天
     * @param days 天数
     * @param date 日期
     * @return 日期
     */
    public static UFDate getDateBefore(UFDateTime date, int days) {
        LocalDateTime localDate = caseToLocalDateTime(date);
        return caseToUFDate(localDate.plusDays( -days ));
    }

    /**
     * 获取某日前几天
     * @param days 天数
     * @param date 日期
     * @return 日期
     */
    public static UFLiteralDate getDateBefore(UFLiteralDate date, int days) {
        LocalDate localDate = caseToLocalDate(date);
        return caseToUFLiteralDate(localDate.plusDays( -days ));
    }

    /**
     * 获取两个日期差了多少
     * @param startDateTime 日期1
     * @param endDateTime 日期2
     * @param type 单位
     * @return 数字
     */
    public static BigDecimal getDateRate(LocalDateTime startDateTime,LocalDateTime endDateTime,TimeEnum type){
        if (type == null || startDateTime==null || endDateTime == null ) return null;
        switch (type){
            case YEAR:
                return getYear(startDateTime,endDateTime);
            case MONTH:
                return getMonth(startDateTime,endDateTime);
            case DAY:
                return getDay(startDateTime,endDateTime);
        }
        return null;
    }

    /**
     * 获取两个日期差了多少
     * @param date1 日期1
     * @param date2 日期2
     * @param zoneId 时区
     * @param type 单位
     * @return 数字
     */
    public static BigDecimal getDateRate(Date date1,Date date2,ZoneId zoneId, TimeEnum type){
        if (type == null || date1==null || date2 == null || zoneId==null) return null;
        LocalDateTime startDateTime = caseToLocalDateTime(date1, zoneId);
        LocalDateTime endDateTime = caseToLocalDateTime(date2, zoneId);
        return getDateRate(startDateTime,endDateTime,type);
    }

    /**
     * 获取两个日期差了多少
     * @param date1 日期1
     * @param date2 日期2
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(Date date1,Date date2,ZoneId zoneId, int type){
        TimeEnum timeEnum = TimeEnum.get(type);
        return getDateRate(date1,date2,zoneId,timeEnum);
    }


    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(LocalDateTime endDateTime, TimeEnum type){
        if (endDateTime==null || type==null ) return null;
        return getDateRate(LocalDateTime.now(),endDateTime,type);
    }

    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(LocalDateTime endDateTime, int type){
        if (endDateTime==null) return null;
        TimeEnum timeEnum = TimeEnum.get(type);
        return getDateRate(endDateTime,timeEnum);
    }

    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(Date endDateTime, ZoneId zoneId, TimeEnum type){
        if (endDateTime==null || type==null || zoneId==null ) return null;
        LocalDateTime localDateTime = caseToLocalDateTime(endDateTime,zoneId);
        return getDateRate(LocalDateTime.now(),localDateTime,type);
    }

    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(Date endDateTime, TimeEnum type){
        if (endDateTime==null || type==null ) return null;
        return getDateRate(endDateTime,zoneId,type);
    }

    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param zoneId 时区
     * @param type 单位
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(Date endDateTime,ZoneId zoneId, int type){
        return getDateRate(endDateTime,zoneId,TimeEnum.get(type));
    }

    /**
     * 获取输入日期距现在差了多少
     * @param endDateTime 日期
     * @param type 单位 1：年 2：月 3：日
     * @return 数字 如1.2
     */
    public static BigDecimal getDateRate(Date endDateTime, int type){
        return getDateRate(endDateTime,zoneId,type);
    }






    /**
     * 两个日期间差了多少天
     * @param startDateTime 日期1
     * @param endDateTime 日期2
     * @return 天数
     */
    public static BigDecimal getDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long until = startDateTime.until(endDateTime, ChronoUnit.SECONDS);
        return new BigDecimal(until).divide(SECOND_DAY,4, RoundingMode.HALF_UP);
    }

    /**
     * 两个日期间差了多少月
     * @param startDateTime 日期1
     * @param endDateTime 日期2
     * @return 天数
     */
    public static BigDecimal getMonth(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long until = startDateTime.until(endDateTime, ChronoUnit.MONTHS);
        LocalDateTime localDateTime = startDateTime.plusMonths(until);
        BigDecimal day = getDay(localDateTime, endDateTime);
        int days = localDateTime.toLocalDate().lengthOfMonth();
        return day.divide(new BigDecimal(days), 4, RoundingMode.HALF_UP).add(new BigDecimal(until));
    }

    /**
     * 两个日期间差了多少年
     * @param startDateTime 日期1
     * @param endDateTime 日期2
     * @return 天数
     */
    public static BigDecimal getYear(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long until = startDateTime.until(endDateTime, ChronoUnit.YEARS);
        LocalDateTime localDateTime = startDateTime.plusYears(until);
        BigDecimal month = getMonth(localDateTime, endDateTime);
        return month.divide(new BigDecimal(12), 4, RoundingMode.HALF_UP).add(new BigDecimal(until));
    }
}
