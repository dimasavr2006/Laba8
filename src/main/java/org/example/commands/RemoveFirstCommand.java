package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class RemoveFirstCommand extends Command {
    private String desc = "Удаляет первый элемент коллекции";
    private String name = "remove_first";

    boolean needScannerToExecute = false;

    public RemoveFirstCommand() {
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
        cm.removeFirst();
    }
}
