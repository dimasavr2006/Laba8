package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class ExitCommand extends Command {

    private String desc = "Выполняет выход из программы";
    private String name = "exit";

    boolean needScannerToExecute = false;

    public ExitCommand() {
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

    @Override
    public void execute() {
        System.out.println("Выполняется выход из программы....");
        cm.exit();
    }
}
