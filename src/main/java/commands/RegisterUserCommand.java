package commands;

import run.Main;

public class RegisterUserCommand extends Command {
    private String desc = "Регистрация пользователя";
    private String name = "reg";
    private int expected = 3;

    public RegisterUserCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        String[] split = argument.split(" ");
        db.registerUser(split[0], split[1]);
    }
}
