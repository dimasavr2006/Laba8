package commands;

import exceptions.IncorrectArgsNumber;
import managers.CollectionManager;
import managers.DBManager;
import run.Main;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.Scanner;

;

/**
 * @author Dimasavr
 */

public abstract class Command {

    protected static CollectionManager cm = Main.cm;

    protected static DBManager db = Main.db;

    protected static String username = Main.username;

    Scanner sc = Main.sc;

    protected String splite = "----------";

    protected String description;
    protected String nameOfCommand;

    protected int numberOfArgs;

    public Command() {
        this.description = description;
        this.nameOfCommand = nameOfCommand;
        this.numberOfArgs = numberOfArgs;
    }

    ////    @Override
//    public void execute(String args) {}
//
////    @Override
//    public void execute() {}
//
////    @Override
//    public void execute(String args, Scanner sc) {}
//
////    @Override
//    public void execute(Scanner sc) {}

    public void execute(String argument) throws AccessDeniedException, AccessException {
        if ((argument.trim().isEmpty() && numberOfArgs == 0) || (!argument.trim().isEmpty() && numberOfArgs == 1) || (!argument.trim().isEmpty() && numberOfArgs == 3)) {
            bodyOfCommand(argument);
        } else {
            throw new IncorrectArgsNumber(numberOfArgs);
        }

            /*
            if (argument.trim().isEmpty() && numberOfArgs != 0) {
            throw new IncorrectArgsNumber(numberOfArgs);
        } else if (!argument.trim().isEmpty() && numberOfArgs == 0) {
            throw new IncorrectArgsNumber(numberOfArgs);
        } else if (!argument.trim().isEmpty() && numberOfArgs == 1) {
            bodyOfCommand(argument);
        } else if (argument.trim().isEmpty() && numberOfArgs == 2) {
            throw new IncorrectArgsNumber(numberOfArgs);
        } else if (!argument.trim().isEmpty() && numberOfArgs == 3) {}
        bodyOfCommand(argument);
             */
    }
    public void bodyOfCommand(String argument) throws AccessException, AccessDeniedException {}

//    @Override
    public void description() {
        String ret = nameOfCommand + " - " + description;
        System.out.println(ret);
    }

    public String getDescription() {
        return description;
    }

    public String getNameOfCommand() {
        return nameOfCommand;
    }
}
