package com.poly.jztr.ecommerce.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateHelper {
    static SimpleDateFormat formater = new SimpleDateFormat();
    public static Instant toDate(String date, String pattern){
        try {
            Date d =  new SimpleDateFormat(pattern).parse(date);
            return d.toInstant();
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static String toStrings(Date date, String pattern){
        formater.applyPattern(pattern);
        return formater.format(date);
    }
    public static Date addDays(Date date, long days){
        date.setTime(date.getTime()+days*24*60*60*1000);
        return date;
    }
}
