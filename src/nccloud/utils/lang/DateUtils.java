package nccloud.utils.lang;

import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static long passLong(UFDouble time, TimeEnum type){
        long res=0l;
        long lTime = time.longValue();
        switch (type){
            case YEAR:
                res=lTime*365*24*60*60;
            case MONTH:
            case DAY:
        }
        return res;
    }
    public static long passLong(UFDouble time,int type){
        switch (type){
            case 0: return passLong(time,TimeEnum.YEAR);
            case 1: return passLong(time,TimeEnum.MONTH);
            case 2: return passLong(time,TimeEnum.DAY);
            default:
                return 0l;
        }
    }

    public static UFDate getDateBefore(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -days);
        Date time= c.getTime();
        UFDate u= new UFDate(time);
        return u;
    }
    public static UFDate getDateBefore(int days, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        Date time= c.getTime();
        UFDate u= new UFDate(time);
        return u;
    }

    public static BigDecimal getDateRate(Date date1,Date date2, TimeEnum type){
        switch (type){
            case YEAR:
                return getYear(date1,date2);
            case MONTH:
                return getMonth(date1,date2);
            case DAY:
                return getDay(date1,date2);
        }
        return null;
    }

    public static BigDecimal getDateRate(Date date1,Date date2, Integer type){
        switch (type){
            case 1:
                return getYear(date1,date2);
            case 2:
                return getMonth(date1,date2);
            case 3:
                return getDay(date1,date2);
        }
        return null;
    }

    public static BigDecimal getDateRate(Date date2, Integer type){
        return getDateRate(new Date(),date2,type);
    }
    public static BigDecimal getDateRate(Date date2, TimeEnum type){
        return getDateRate(new Date(),date2,type);
    }

    private static BigDecimal getDay(Date date1, Date date2) {
        BigDecimal subTime = new BigDecimal(date1.getTime() - date2.getTime());
        BigDecimal day = subTime.divide(new BigDecimal(86400000));
        day.setScale(0, RoundingMode.UP);
        return day;
    }

    private static BigDecimal getYear(Date date1, Date date2) {
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        String format1 = format.format(date1);
        String format2 = format.format(date2);
        String[] split1 = format1.split("-");
        String[] split2 = format2.split("-");

        int year = Integer.parseInt(split1[0]) - Integer.parseInt(split2[0]);

        int month1 = Integer.parseInt(split1[1]);
        int month2 = Integer.parseInt(split2[1]);

        if (month1<month2){
            year=year-1;
            month1=month1+12;
        }
        int month=month1-month2;

        int day1 = Integer.parseInt(split1[2]);
        int day2 = Integer.parseInt(split2[2]);
        if (day1<day2){
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH,-1);
            int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            day1=day1+actualMaximum;
            if (month-1<=0){
                year=year-1;
                month=12+month;
            }
            month=month-1;
        }
        int dday=day1-day2;
        calendar.setTime(date1);
        calendar.add(Calendar.YEAR,-1);
        Date timeYear = calendar.getTime();
        BigDecimal dayY = new BigDecimal(date1.getTime()-timeYear.getTime()).divide(new BigDecimal(86400000));
        BigDecimal dayYP = new BigDecimal(dday).divide(dayY,4, RoundingMode.UP).add(new BigDecimal(year)).add( new BigDecimal(month).divide(new BigDecimal(12),4,RoundingMode.UP));
        System.out.println(dayYP);
        return dayYP;
    }

    private static BigDecimal getMonth(Date date1, Date date2) {

        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        String format1 = format.format(date1);
        String format2 = format.format(date2);
        String[] split1 = format1.split("-");
        String[] split2 = format2.split("-");

        int year = Integer.parseInt(split1[0]) - Integer.parseInt(split2[0]);

        int month1 = Integer.parseInt(split1[1]);
        int month2 = Integer.parseInt(split2[1]);

        if (month1<month2){
            year=year-1;
            month1=month1+12;
        }
        int month=month1-month2;

        int day1 = Integer.parseInt(split1[2]);
        int day2 = Integer.parseInt(split2[2]);
        if (day1<day2){
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH,-1);
            int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            day1=day1+actualMaximum;
            if (month-1<=0){
                year=year-1;
                month=12+month;
            }
            month=month-1;
        }
        int dday=day1-day2;

        int subMonth = year * 12 + month;
        calendar.setTime(date1);
        calendar.add(Calendar.MONTH,-1);
        Date time = calendar.getTime();
        BigDecimal dayM = new BigDecimal(date1.getTime()-time.getTime()).divide(new BigDecimal(86400000));
        BigDecimal dayMP = new BigDecimal(dday).divide(dayM,2, RoundingMode.UP).add(new BigDecimal(subMonth));
        System.out.println(dayMP+"月");
        return dayMP;
    }
}
