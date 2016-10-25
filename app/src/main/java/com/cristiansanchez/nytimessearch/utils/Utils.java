package com.cristiansanchez.nytimessearch.utils;

import java.util.Calendar;

/**
 * Created by kristianss27 on 10/24/16.
 */
public class Utils {

    public String getFormatDate(Calendar calendar, boolean sumMonth){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        if(sumMonth){
            month++;
        }
        int year = calendar.get(Calendar.YEAR);
        String date = month+"/"+day+"/"+year;
        return date;
    }

    public String getFormatDateUrl(Calendar calendar, boolean sumMonth){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        if(sumMonth){
            month++;
        }
        int year = calendar.get(Calendar.YEAR);

        String date = ""+year+month+day;
        return date;
    }

}
