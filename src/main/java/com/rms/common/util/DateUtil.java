package com.rms.common.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static String patten = "yyyy-MM-dd HH:mm:ss";
    private static String simplePatten = "yyMMddHHmmss";

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(date);
    }

    public static String formatSimple(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(simplePatten);
        return sdf.format(date);
    }

    public static Integer dateToAge(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate birth = localDateTime.toLocalDate();
        return (int) birth.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    public static Date ageToDate(Integer age) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusYears(age);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static void main(String[] args) {
        System.out.println(format(ageToDate(10)));
    }

    //指定日期加上指定时间
    public static Date dateAddTime(Date date, int unit, int amount) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(date);
        Calendar ca = Calendar.getInstance();
        ca.add(unit, amount);// num为增加的天数，可以改变的
        date = ca.getTime();
        return date;
    }

}
