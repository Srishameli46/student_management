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
  * @param string
  *     String that is to be validated.
  *     Whether it may contain only alphabetical word or alphanumeric word or numerical word.
  * @return true if it is  valid. Else return false.
  */
  public static boolean validString(String str) {
    String regex = "^[a-zA-Z]+$";
    if (str.matches(regex) && (!str.isEmpty())) { 
      return true;
    } else {
      return false; 
    }  
  }
}