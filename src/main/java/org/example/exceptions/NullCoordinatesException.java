package org.example.exceptions;

public class NullCoordinatesException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Coordinates cannot be null";
  }
}
