package com.xuhe.hadoop.study;

import java.util.Calendar;

public class DateTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf("2017"));
        cal.set(Calendar.MONTH, Integer.valueOf("06") - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Integer daysNumber = cal.get(Calendar.DATE);
        System.out.println(daysNumber);
    }
}
