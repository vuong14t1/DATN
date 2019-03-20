package com.example.genealogy.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtils {
    public  static SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy");
    public  static SimpleDateFormat simpleDateFormatMonthYear = new SimpleDateFormat("MM/yyyy");
    public  static SimpleDateFormat simpleDateFormatMonthDayYear = new SimpleDateFormat("MM/dd/yyyy");
    public static String dateToString(Date date){
        if(date == null) return "?";
        return simpleDateFormatMonthDayYear.format(date);
    }

    public static Date getDate(String value){
        Date result = null;
        if(value.equals("?") ) return null;
        try {
            result = simpleDateFormatMonthDayYear.parse(value);
        } catch (ParseException e) {
            try {
                result = simpleDateFormatMonthYear.parse(value);
            } catch (ParseException e3) {
                try {
                    result = simpleDateFormatYear.parse(value);
                } catch (ParseException ignored) {
                }
            }
        }
        return result;
    }
}
