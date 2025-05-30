package edu.handong.csee.java.studygroup.exceptions;

import java.io.Serial;

/**
 * Custom exception class to handle errors during ZIP file processing.
 * This exception is thrown when an error occurs while reading or processing
 * ZIP files containing CSV data.
 */
public class ZipFileProcessingException extends Exception {

  /**
   * Serial version UID for serialization compatibility.
   */
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the ZipFileProcessingException with a specific error message.
   *
   * @param message A description of the error that occurred.
   */
  public ZipFileProcessingException(String message) {
    super("ZIP Processing Error: " + message);
  }

  /**
   * Constructor for the ZipFileProcessingException with error message and cause.
   *
   * @param message A description of the error that occurred.
   * @param cause The underlying exception that caused this error.
   */
  public ZipFileProcessingException(String message, Throwable cause) {
    super("ZIP Processing Error: " + message, cause);
  }
}