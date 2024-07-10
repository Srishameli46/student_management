package com.i2i.sms.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

public final class DateUtils {

    /**
     * <p>
     * Calculate the difference between the given date year and current date year.
     * </p>
     *
     * @param date The date in the format ("yyyy-MM-dd") and must be lesser than current date .
     * @return int
     * The count of number of years between the given date year and the current date year.
     */
    public static int calculatePeriodDifference(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(date,currentDate).getYears();
    }
}
