package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class MinBySoundtrackNameCommand extends Command {

    private String desc = "Выводит название элемента коллекции с минимальным значением поля soundtrackName";
    private String name = "min_by_soundtrack_name";

    boolean needScannerToExecute = false;

    public MinBySoundtrackNameCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        int expected = 1;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
        cm.minBySoundtrackName();
    }
}
