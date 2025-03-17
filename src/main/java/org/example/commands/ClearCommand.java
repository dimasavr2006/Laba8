package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class ClearCommand extends Command {

    private String desc = "Очистка коллекции";
    private String name = "clear";

    boolean needScannerToExecute = false;

    public ClearCommand() {
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
        cm.clear();
        System.out.println("Коллекция очищена");
    }


}
