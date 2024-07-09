package com.i2i.sms.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Component
public final class DateUtils {

    /**
     * <p>
     * Checks given date in format(yyyy-mm-dd) or not
     * </p>
     *
     * @param inputDate holds the date in string in format of (yyyy-MM-dd).
     * @return true or false
     * If the date format is correct,return true .
     * If the format is not correct, returns false.
     * This exception is raised when unable to parse another date format.
     * Ex: (yyyy/mm/dd)
     */
    public static boolean isValidPastDate(LocalDate inputDate) {
        try {
            String date = inputDate.toString();
            LocalDate.parse(date);
            return inputDate.isBefore(LocalDate.now());
        } catch(DateTimeParseException e) {
            return false;
        }
    }

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
