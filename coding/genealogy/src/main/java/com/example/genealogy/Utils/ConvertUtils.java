package com.example.genealogy.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtils {
    public  static SimpleDateFormat simpleDateFormatMonthDayYear = new SimpleDateFormat("MM/dd/yyyy");
    public static String dateToString(Date date){
        if(date == null) return "?";
        return simpleDateFormatMonthDayYear.format(date);
    }
}
