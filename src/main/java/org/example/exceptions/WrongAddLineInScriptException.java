package org.example.exceptions;

public class WrongAddLineInScriptException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Ошибка ввода в строке файла";
    }
}
