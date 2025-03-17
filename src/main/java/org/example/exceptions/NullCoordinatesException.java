package org.example.exceptions;

/**
 * @author Dimasavr
 */

public class NullCoordinatesException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Coordinates cannot be null";
  }
}
