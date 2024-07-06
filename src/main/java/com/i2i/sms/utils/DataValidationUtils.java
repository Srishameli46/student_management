package com.i2i.sms.utils;

import org.springframework.stereotype.Component;

/**
 * <p>
 * This class deals with validating the datatype of the given data.
 * </p>
 */
@Component
public final class DataValidationUtils {

    private DataValidationUtils() {
    }

    /**
     *<p>
     * This method checks whether the given grade is within the range from 1 to 12.
     *</p>
     *
     * @param grade
     *        The grade which is to be validated.
     *
     * @return True if the grade is within the range, Ex: 12
     *         False if the grade exceeds or lesser than, 0 Ex: 0 0r 13.
     */

    public static boolean isValidGrade(int grade) {
        return (grade < 13 && grade >0);
    }
    /**
     * <p>
     * This method validates whether the given string contains only alphabetical words or not.
     * </p>
     *
     * @param str String that is to be validated.
     *            Whether it may contain only alphabetical word or alphanumeric word or numerical word.
     * @return true if it is  valid. Else return false.
     */
    public static boolean validString(String str) {
        String regex = "^[a-zA-Z\\s]+$";
        return str.matches(regex) && (!str.isEmpty());
    }

    /**
     * <p>
     * This method validates whether the given string contains only US or Indian zip code.
     * </p>
     *
     * @param zip String(zip code) that is to be validated.
     *            Whether it may contain only numerical characters with 5 to 6 digits.
     * @return true if it is valid. Else return false.
     */
    public static boolean validPinCode(String zip) {
        String regex = "^\\d{5,6}(?:[-\\s]\\d{4})?$";
        return zip.matches(regex) && (!zip.isEmpty());
    }
}