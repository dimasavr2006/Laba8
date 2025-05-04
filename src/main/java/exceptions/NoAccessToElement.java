package exceptions;

public class NoAccessToElement extends RuntimeException {
    String username;
    public NoAccessToElement(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "У вас нет доступа к данному элементу, ваш логин - " + username;
    }
}
