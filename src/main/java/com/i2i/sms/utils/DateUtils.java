package com.i2i.sms.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public final class DateUtils {

    private static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * <p>
     * Checks given date in format(yyyy-mm-dd) or not
     * </p>
     *
     * @param date holds the date in string in format of (yyyy-MM-dd).
     * @return Date
     * If the date format is correct, parses date with the given format.
     * If the format is not correct, returns null.
     * This exception is raised when unable to parse another date format.
     * Ex: (yyyy/mm/dd)
     */
    public static Date checkAndFormatDate(String date) {
        try {
            Date parsedDate = sqlDateFormat.parse(date);
            return parsedDate;
        } catch (ParseException e) {
            return null;
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
    public static int calculatePeriodDifference(Date date) {
        Date currentDate = new Date();
        return currentDate.getYear() - date.getYear();
    }
}
