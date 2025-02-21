package org.example.commands;

import org.example.exceptions.*;

public class MinBySoundtrackNameCommand extends Command {
    private String description = "Выводит название элемента коллекции с минимальным значением поля soundtrackName";
    private String nameOfCommand = "min_by_soundtrack_name";

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
