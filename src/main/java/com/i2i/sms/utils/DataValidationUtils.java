package com.i2i.sms.utils;

import java.util.regex.Matcher;

/**
 * <p>
 * This class deals with validating the datatype of the given data.
 * </p>
 */
public final class DataValidationUtils {
    
  private DataValidationUtils() {}

 /**
  * <p>
  * This method validates whether the given string contains only alphabetical words or not.
  * </p>
  * @param str
  *     String that is to be validated.
  *     Whether it may contain only alphabetical word or alphanumeric word or numerical word.
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
     * @param zip
     *     String(zip code) that is to be validated.
     *     Whether it may contain only numerical characters with 5 to 6 digits.
     * @return true if it is valid. Else return false.
     */
  public static boolean validPinCode(String zip) {
      String regex = "^\\d{5,6}(?:[-\\s]\\d{4})?$";
      return zip.matches(regex) && (!zip.isEmpty());
  }
}