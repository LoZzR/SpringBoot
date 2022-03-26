package com.spring.boot.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateProcessor {
    public static final String DATE_FORMAT= "yyyy-MM-dd";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static LocalDate toDate(final String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String toString(final LocalDate date){
        return date.format(formatter);
    }
}
