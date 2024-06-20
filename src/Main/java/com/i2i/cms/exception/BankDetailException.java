package com.i2i.cms.exception;
import java.lang.Exception;

/**
 * <p>
 * This class represents custom exception for handling in runtime.
 * This exception caught and handle to provide detailed error information.
 * </p>
 */
public class BankDetailException extends RuntimeException {
  public BankDetailException(String message, Throwable t) {
    super(message, t);
  }
}