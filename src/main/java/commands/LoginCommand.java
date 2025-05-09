package commands;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

public class LoginCommand  extends Command {
    private String desc = "Вход под паролем и логином";
    private String name = "login";
    private int expected = 1;

    @Override
    public void bodyOfCommand(String argument) throws AccessDeniedException {
        String[] split = argument.split(" ");
        boolean res = db.login(split[0], split[1]);
        if(res) {
            System.out.println("Вход выполнен успешно");
        } else {
            throw new AccessDeniedException("Нет доступа к файлу");
        }
    }

}
