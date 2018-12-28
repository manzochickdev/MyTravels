package com.example.tuananh.mytravels.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    public static String getString(Date date){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String d = simpleDateFormat.format(date);
        return d;
    }

    public static String getString(long time){
        Date date = new Date(time);
        return getString(date);
    }
}
