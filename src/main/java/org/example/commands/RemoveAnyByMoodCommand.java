package org.example.commands;

import org.example.enums.Mood;
import org.example.exceptions.*;

public class RemoveAnyByMoodCommand extends Command {
    private String description;
    private String keyword;

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
