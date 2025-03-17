package org.example.exceptions;

public class IllegalScriptLine extends RuntimeException {

    String message;

    public IllegalScriptLine(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
