package com.mgp.ddemo.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class DateUtil {

    /**
     * mongo 日期查询isodate
     * @param dateStr
     * @return
     */
    public static Date strDateToISODate(String dateStr){

        //T代表后面跟着时间，Z代表UTC统一时间
        Date date = formatD(dateStr);

        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));

        String isoDate = format.format(date);

        try {

            return format.parse(isoDate);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * mongo 日期查询isodate
     * @param needDate
     * @return
     */
    public static Date dateToISODate(Date needDate){

        //T代表后面跟着时间，Z代表UTC统一时间

        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));

        String isoDate = format.format(needDate);

        try {

            return format.parse(isoDate);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return null;
    }



    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static Date formatD(String dateStr){
        return formatDate(dateStr,DATE_TIME_PATTERN);
    }



    public static Date formatDate(String dateStr ,String format)  {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        Date ret = null ;

        try {

            ret = simpleDateFormat.parse(dateStr) ;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
