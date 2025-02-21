package org.example.commands;

import org.example.exceptions.*;

public class SortCommand extends Command {
    private String description = "Выолняет стандартную сортировку коллекции";
    private String nameOfCommand = "sort";

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
