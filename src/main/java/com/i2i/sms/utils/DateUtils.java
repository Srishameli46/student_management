package com.i2i.sms.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hibernate.type.descriptor.java.JdbcDateJavaType.DATE_FORMAT;

@Component
public final class DateUtils {

    /**
     * <p>
     * Checks given date in format(yyyy-mm-dd) or not
     * </p>
     *
     * @param date holds the date in string in format of (yyyy-MM-dd).
     * @return true or false
     * If the date format is correct,return true .
     * If the format is not correct, returns false.
     * This exception is raised when unable to parse another date format.
     * Ex: (yyyy/mm/dd)
     */
    public static boolean isValidDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            String dateString = sdf.format(date);
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
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
    public static int calculatePeriodDifference(Date date) {
        Date currentDate = new Date();
        return currentDate.getYear() - date.getYear();
    }
}
