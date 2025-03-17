package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class SortCommand extends Command {

    private String desc = "Выолняет стандартную сортировку коллекции";
    private String name = "sort";

    boolean needScannerToExecute = false;

    public SortCommand() {
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
        cm.sort();
    }
}
