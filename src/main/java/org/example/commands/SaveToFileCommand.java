package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

public class SaveToFileCommand extends Command {

    private String desc = "Сохраняет текущую коллекцию  в файл пормата json";
    private String name = "save";

    public SaveToFileCommand() {
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

    }
}
