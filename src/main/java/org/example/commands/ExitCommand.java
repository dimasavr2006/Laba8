package org.example.commands;

import org.example.exceptions.*;

public class ExitCommand extends Command {
    private String description;
    private String keyword;

    @Override
    public void execute(String args) {
        int expected = 0;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
        System.out.println("Выполняется выход из программы... .");
        cm.exit();
    }

    @Override
    public void execute() {
        System.out.println("Выполняется выход из программы....");
        cm.exit();
    }
}
