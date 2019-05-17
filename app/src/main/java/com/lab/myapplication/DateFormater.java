package com.lab.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {

    static char spliter = '-';

    public static char getSpliter() {
        return spliter;
    }

    public static void setSpliter(char spliter) {
        spliter = spliter;
    }

    public static String getDateNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + spliter + "MM" + spliter + "dd HH:mm:ss");
        return dateFormat.format( new Date() );
    }

    public static int getDateNowSecond() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + spliter + "MM" + spliter + "dd HH:mm:ss");
        Date date = dateFormat.parse(getDateNow());
        int millis = (int) date.getTime() / 1000;
        return millis;
    }

    public static String convertToString(int seconds){
        Date d = new Date(seconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + spliter + "MM" + spliter + "dd HH:mm:ss");
        return dateFormat.format(d);
    }

    public static int convertToSeconds(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy" + spliter + "MM" + spliter + "dd HH:mm:ss");
        Date date = dateFormat.parse(str);
        return  (int) date.getTime() / 1000;
    }

}
