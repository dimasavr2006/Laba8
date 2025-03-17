package org.example.commands;

import org.example.enums.Mood;
import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class RemoveAnyByMoodCommand extends Command {
    private String desc = "Удаляет случайный элемент коллекции, настроение которого равно заданному";
    private String name = "remove_any_by_mood";

    boolean needScannerToExecute = false;

    public RemoveAnyByMoodCommand() {
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
        cm.removeAnyByMood(Mood.valueOf(arguments[1]));
    }
}
