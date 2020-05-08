package com.utils;

import java.util.Calendar;

/**
 * @author zzx
 * 2020/5/3 20:50
 */
public class DateUtils {
    public static boolean isBigMonth(int month){
        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month
                == 12;
    }
    public static boolean isSmallMonth(int month){
        return month == 4 || month == 6 || month == 9 || month == 11;
    }
    public static boolean isLeapYear(int year){
        return (year%4==0&&year%100!=0||year%400==0);
    }
    public static int subYear(String time){
        return Integer.parseInt(time.substring(0,time.indexOf("-")));
    }
    public static int subMonth(String time){
        return Integer.parseInt(time.substring(time.indexOf("-")+1,time.lastIndexOf("-")));
    }
    public static int subDay(String time){
        return Integer.parseInt(time.substring(time.lastIndexOf("-")+1));
    }
    public static String expect(int year, int month, int day){
        if(day<29){
            if(month==12){
                month=1;
                year++;
            }else {
                month++;
            }
        }else {
            month++;
            if(DateUtils.isSmallMonth(month)&&day>30){
                day=30;
            }else if(!DateUtils.isBigMonth(month)&&DateUtils.isLeapYear(year)&&day>29) {
                day = 29;
            }
        }
        return year+"-"+month+"-"+day;
    }

    public static String getTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        return  year+"-"+month+"-"+day;

    }
}
