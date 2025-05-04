package exceptions;

/**
 * @author Dimasavr
 */

public class WrongNameSizeException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong Name Size";
    }
}
