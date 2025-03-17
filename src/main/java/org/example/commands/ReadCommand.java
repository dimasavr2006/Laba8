package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class ReadCommand extends Command {
    private String desc = "Чтение данных из заданного json файла";
    private String name = "read";

    boolean needScannerToExecute = false;

    public ReadCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    int expected = 1;

    @Override
    public void execute(String args) {
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
        cm.readJson(arguments[0]);
        System.out.println("Коллекция из файла была успешно добавлена");
    }

    @Override
    public void execute() {
        throw new IncorrectArgsNumber(expected);
    }
}
