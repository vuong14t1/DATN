package com.example.genealogy.config;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigFormat {
    public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static String getStringFromDate(Date date){
        String result = "?";
        if(date != null){
            result= DATE_TIME_FORMAT.format(date);
        }
        return result;
    }

}
