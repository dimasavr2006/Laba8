package org.example.commands;

import org.example.enums.Mood;
import org.example.exceptions.*;


public class CountGreaterThanMoodCommand extends Command {
    private String description = "Выводит количество элементов коллекции настроение которых хуже, чем заданное";
    private String nameOfCommand = "count_greater_than_mood";

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
