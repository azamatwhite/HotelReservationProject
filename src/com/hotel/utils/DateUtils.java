package com.hotel.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    // 1. Екі датаның арасындағы түндер санын есептеу
    public static long getDaysBetween(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) return 0;
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public static boolean isValidRange(LocalDate checkIn, LocalDate checkOut) {
        LocalDate today = LocalDate.now();
        
        
        if (checkIn.isBefore(today)) return false;
        
        
        if (!checkOut.isAfter(checkIn)) return false;
        
        return true;
    }

   
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr); 
        } catch (Exception e) {
            return null;
        }
    }
}