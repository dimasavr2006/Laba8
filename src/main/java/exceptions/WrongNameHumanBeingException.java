package exceptions;

/**
 * @author Dimasavr
 */

public class WrongNameHumanBeingException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Human Being Name=null Exception";
  }
}
