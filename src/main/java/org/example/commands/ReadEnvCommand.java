package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class ReadEnvCommand extends Command {
    private String desc = "Чтение данных из переменной среды";
    private String name = "readEnv";

    boolean needScannerToExecute = false;

    public ReadEnvCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        int expected = 0;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
    }

    @Override
    public void execute() {
        cm.readEnv();
    }
}
