package com.i2i.sms.exception;
import java.lang.Exception;

/**
 * <p>
 * This class represents custom exception for handling in runtime.
 * This exception caught and handle to provide detailed error information.
 * </p>
 */
public class StudentException extends RuntimeException {
  public StudentException(String message, Throwable t) {
    super(message, t);
  }
}