package org.example.commands;

import org.example.enums.Mood;
import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class CountGreaterThanMoodCommand extends Command {

    private String desc = "Выводит количество элементов коллекции настроение которых хуже, чем заданное";
    private String name = "count_greater_than_mood";

    boolean needScannerToExecute = false;

    public CountGreaterThanMoodCommand() {
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
        cm.countGreaterThanMood(Mood.valueOf(arguments[0]));
    }
}
