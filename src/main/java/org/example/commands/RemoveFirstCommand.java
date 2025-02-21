package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

public class RemoveFirstCommand extends Command {
    private String description = "Удаляет первый элемент коллекции";
    private String nameOfCommand = "remove_first";

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
